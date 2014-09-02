package com.sylla.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sylla.Corp;
import com.sylla.Niffler;
import com.sylla.Page;
import com.sylla.PagePK;
import com.sylla.Samephore;

/**
 * Verify the pages and corps
 * @author hsun
 * Total Pages & Corps: 
 * 	GSQY = 3189 * 55 + 7 = 175402 (before)
 *  GSQY = 3118 * 55 + 40 = 171530 (now)
 *  GTQY = 5247 * 55 + 51 = 288636 (before)
 *  GTQY = 5290 * 55 + 21 = 290971 (now)
 */
public class Verifier {

	private static final Log _log = LogFactory.getLog(Verifier.class);

	private static final String[] VERIFY_STATUS = {Page.PS_INITIAL, Page.PS_DOWNLOADED};

	private static final Samephore COUNT_SAMEPHORE = new Samephore(0);

	private static boolean stop = false;

	private static boolean running = false;

	private static long counter = 0;

	private static final byte[] RUN_LOCK = {};

	private Verifier() {
	}

	public static void run(long count) {
		synchronized(RUN_LOCK) {
			if (running || count <= 0) return;
			else running = true;
		}
		List<Page> pages = null;
		Page page = null;
		Runnable r;
		Thread t;
		Long idx = 0L;
		try {
			pages = SyllaServiceUtil.getPages(count, VERIFY_STATUS);
		} catch (Throwable tt) {}
		if (pages == null || pages.size() <= 0) return;

		while(!stop) {
			if (counter >= count) COUNT_SAMEPHORE.p();
			try {
				if (pages.size() > 0) page = pages.remove(0);
				else page = SyllaServiceUtil.getNextPage(idx, VERIFY_STATUS);
			} catch(Throwable tt) {}
			if (page == null) {
				if (idx == 0L) break;
				else idx = 0L;
			} else {
				idx = page.getIndex();
				counter++;
				r = new VerifyThread(page);
				t = new Thread(r);
				t.start();
			}
		}	
	}

	private static final class VerifyThread implements Runnable {

		private Page page;

		private VerifyThread(Page page) {
			this.page = page;
		}

		public void run() {
			try {
				List<Corp> corps;
				if(PagePK.PT_GSQY.equals(page.getType())) corps = Niffler.searchCorps(page.getPageId());
				else if(PagePK.PT_GTQY.equals(page.getType())) corps = Niffler.searchGCorps(page.getPageId());
				else throw new Exception("Unknown page type: " + page.getId());
				SyllaServiceUtil.verifyPageAndCorps(page, corps);
				_log.debug("Verify page success: " + page.getPageId());
			} catch(Throwable tt) {
				_log.error("Verify page failed: " + page.getPageId(), tt);
			} finally {
				counter--;
				COUNT_SAMEPHORE.v();
				Informer.v();
			}
		}
	}

}
