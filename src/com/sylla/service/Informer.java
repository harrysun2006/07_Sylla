package com.sylla.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sylla.Corp;
import com.sylla.Info;
import com.sylla.Niffler;
import com.sylla.Samephore;

/**
 * Thread to download corp info from
 * /CorpSel/Corp_info.aspx and /CorpSel/Corp_infoG.aspx pages
 * @author hsun
 *
 */
public class Informer {

	private static final Log _log = LogFactory.getLog(Informer.class);

	private static final String[] CORP_STATUS = {Corp.CS_INITIAL};

	private static final Samephore COUNT_SAMEPHORE = new Samephore(0);

	private static final Samephore EMPTY_SAMEPHORE = new Samephore(0);

	private static boolean stop = false;

	private static boolean running = false;

	private static long counter = 0;

	private static final byte[] RUN_LOCK = {};

	private Informer() {
	}

	public static void v() {
		EMPTY_SAMEPHORE.v();
	}

	public static void run(long count) {
		synchronized(RUN_LOCK) {
			if (running || count <= 0) return;
			else running = true;
		}
		List<Corp> corps = null;
		Corp corp = null;
		Runnable r;
		Thread t;
		Long idx = 0L;
		try {
			// corps = SyllaServiceUtil.getCorps(count, CORP_STATUS);
			corps = SyllaServiceUtil.getVoidCorps(count);
		} catch (Throwable tt) {}
		while(!stop) {
			if (counter >= count) COUNT_SAMEPHORE.p();
			try {
				if (corps.size() > 0) corp = corps.remove(0);
				else {
					// corp = SyllaServiceUtil.getNextCorp(idx, CORP_STATUS);
					corp = SyllaServiceUtil.getVoidCorp(idx);
				}
			} catch (Throwable tt) {}
			if (corp == null) {
				if (idx == 0L) EMPTY_SAMEPHORE.p();
				else idx = 0L;
			} else {
				idx = corp.getIndex();
				counter++;
				r = new InfoThread(corp);
				t = new Thread(r);
				t.start();
			}
		}
	}

	private static final class InfoThread implements Runnable {

		private Corp corp;

		private InfoThread(Corp corp) {
			this.corp = corp;
		}

		public void run() {
			try {
				List<Info> infos;
				if (Corp.CT_GSQY.equals(corp.getType())) infos = Niffler.info(corp);
				else if (Corp.CT_GTQY.equals(corp.getType())) infos = Niffler.infoG(corp);
				else throw new Exception("Unknown corp type: " + corp.getId());
				// SyllaServiceUtil.saveCorpAndInfos(corp, infos);
				SyllaServiceUtil.verifyCorpAndInfos(corp, infos);
				_log.debug("Download corp success: " + corp.getId());
			} catch(Throwable tt) {
				_log.error("Download corp failed: " + corp.getId(), tt);
			} finally {
				counter--;
				COUNT_SAMEPHORE.v();
				Imager.v();
			}
		}
	}
}
