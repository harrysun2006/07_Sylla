package com.sylla;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import com.sylla.service.Collator;
import com.sylla.service.SyllaServiceUtil;
import com.sylla.util.CommonUtil;
import com.sylla.util.PropsUtil;
import com.sylla.util.SpringUtil;

public class Niffler {

	private static final Log log = LogFactory.getLog(Niffler.class);

	public static final String HOST = "218.4.189.213";

	public static final String ACCEPT_ALL_HEADER = "*/*";

	// public static final String ACCEPT_MOST_HEADER = "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, */*";

	public static final String LOGIN_URL = "http://218.4.189.213/login.aspx";

	public static final String VICODE_URL = "http://218.4.189.213/VicCode.aspx";

	public static final String INDEX_URL = "http://218.4.189.213/index.htm";

	public static final String BASEINFO_URL = "http://218.4.189.213/BaseInfo/Book.aspx";

	private static final String __EVENT_TARGET = "";

	private static final String __EVENT_ARGUMENT = "";

	private static final String __VIEW_STATE = "/wEPDwUKLTg5MzAwODMzNmQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFC0ltZ0J0bkxvZ2lu9qONp4vpfpo+m0hpiHy1LmxxWK4=";

	private static final String __EVENT_VALIDATION = "/wEWBQKe8Mh4Avi6n6EGArjOqqsKAri//VkCnMC7yAcXvR1YLFDV21zLgqC+1KbuIThTPw==";

	private static final String CARD_NUMBER = "001000000548";

	private static final String CARD_PASSWORD = "263651967016";

	private static final String HARDWARE_CODE = "BFEBFBFF000006FDx86 Family 6 Model 15 Stepping 13CPU0Intel(R) Pentium(R) Dual  CPU  E2180  @ 2.00GHz1363trueMicro-StartrueMS-7255 V1.2To be filled by O.E.M.1.2底板undefinedundefinedBase Board";

	private static final String LOGIN_IMGBTNX = "56";

	private static final String LOGIN_IMGBTNY = "16";

	public static final Pattern BOOK_PATTERN = Pattern.compile("<span id=\"LblLogins\">(\\d+)</span>.*<span id=\"LblSearch\">(\\d+)</span>", Pattern.DOTALL);

	public static final String LEFT_URL = "http://218.4.189.213/CorpLeft.htm";

	public static final String CORP_SEARCH_URL = "http://218.4.189.213/CorpSel/CorpSearchs.aspx?CrruPage={0,number,###}&corp_zch=&corp_qymc=&corp_jydz=&corp_frdb=&corp_zczbminvalue=&corp_zczbmaxvalue=&corp_jyfw=&Drp_corp_type=&Drp_copy_hy=&corp_gq=&corp_jyqxminvalue=&corp_jyqxmaxvalue=";

	// public static final String CORP_SEARCH_REF = MessageFormat.format(CORP_SEARCH_URL, 1);

	public static final String CORP_SEARCHG_URL = "http://218.4.189.213/CorpSel/CorpSearchsG.aspx?CrruPage={0,number,###}&corp_zch=&corp_qymc=&corp_jydz=&corp_frdb=&corp_zczbminvalue=&corp_zczbmaxvalue=&corp_jyfw=&Drp_copy_hy=&corp_gq=&corp_jyqxminvalue=&corp_jyqxmaxvalue=";

	public static final String CORP_INFO_URL = "http://218.4.189.213/CorpSel/Corp_info.aspx?corp_id={0,number,###}";

	public static final String CORP_INFOG_URL = "http://218.4.189.213/CorpSel/Corp_infoG.aspx?corp_id={0,number,###}";

	public static final String IMAGE_URL = "http://218.4.189.213/CorpSel/InfoShowImg.aspx?StrCode={0}";

	public static final Pattern CORP_INFO_PATTERN = Pattern.compile("<a href=\"Corp_info\\.aspx\\?corp_id=(\\d+)\">(.*)</a>&nbsp;&nbsp;([^<\\s*]*)");

	public static final Pattern CORP_INFOG_PATTERN = Pattern.compile("<a href=\"Corp_infoG\\.aspx\\?corp_id=(\\d+)\">(.*)</a>&nbsp;&nbsp;([^<\\s*]*)");

	public static final Pattern CORP_TOTAL_PAGE_PATTERN = Pattern.compile("<span id=\"LblPageCount\".*>(\\d+)</span>");

	public static final Pattern CORPG_TOTAL_PAGE_PATTERN = Pattern.compile("<span id=\"LblPageCount\".*>(\\d+)</span>");

	public static final Pattern IMAGE_PATTERN = Pattern.compile("src=\"InfoShowImg\\.aspx\\?StrCode=(\\w+)\"");

	public static final Pattern CORP_INFO_SCOPE_PATTERN = Pattern.compile("<span id=\"Lblcorp_jyfw\".*>(.+)</span>");

	public static final Step[] LOGIN_STEPS = new Step[] { 
		new Step() {public WebPage work(WebClient client, WebPage page) throws Exception {return step1(client, page);}}, 
		new Step() {public WebPage work(WebClient client, WebPage page) throws Exception {return step2(client, page);}}, 
		new Step() {public WebPage work(WebClient client, WebPage page) throws Exception {return step3(client, page);}}, 
	};

	public static final int CORP_INFO_SCOPE_INDEX = 5;

	public static final String[] INFO_TYPES = {
		InfoPK.IT_REGNO,
		InfoPK.IT_NAME2,
		InfoPK.IT_OWNER,
		InfoPK.IT_ADDR,
		InfoPK.IT_CAP,
		// InfoPK.IT_SCOPE,
		InfoPK.IT_TEL,
		InfoPK.IT_POSTCODE,
		InfoPK.IT_INDUSTRY,
		InfoPK.IT_PRECINCT,
		InfoPK.IT_CREATEDATE2,
		InfoPK.IT_REGORGAN,
		InfoPK.IT_CATEGORY,
	};

	private static final List<WebClient> clients = new ArrayList<WebClient>();

	private static final Samephore LACK_SAMEPHORE = new Samephore(0);

	private static final Map<String, String> DEFAULT_HEADERS = new Hashtable<String, String>();

	static {
		DEFAULT_HEADERS.put("Accept", ACCEPT_ALL_HEADER);		
		DEFAULT_HEADERS.put("Referer", "http://www.szgsw.com");
		DEFAULT_HEADERS.put("Accept-Language", "zh-cn");
		DEFAULT_HEADERS.put("Accept-Encoding", "gzip, deflate");
		DEFAULT_HEADERS.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		DEFAULT_HEADERS.put("Connection", "Keep-Alive");
		DEFAULT_HEADERS.put("Cache-Control", "no-cache");
	}

	private Niffler() {
	}

	/**
	 * open the login page: /login.aspx
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private static WebPage step1(WebClient client, WebPage p) throws Exception {
		return client.get(LOGIN_URL, DEFAULT_HEADERS, null, false);
	}

	/**
	 * get the validate code picture from page: VicCode.aspx
	 * then submit to login page: /login.aspx
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private static WebPage step2(WebClient client, WebPage p) throws Exception {
		Map<String, String> headers = new Hashtable<String, String>();
		headers.putAll(DEFAULT_HEADERS);
		headers.put("Referer", LOGIN_URL);
		WebPage r = client.get(VICODE_URL, headers, null, false);
		String vidCode = CodeChecker.getCode(r.getResponseBody());

		headers.put("Content-Type", "application/x-www-form-urlencoded");
		Map<String, String> params = new Hashtable<String, String>();
		params.put("__EVENTTARGET", __EVENT_TARGET);
		params.put("__EVENTARGUMENT", __EVENT_ARGUMENT);
		params.put("__VIEWSTATE", __VIEW_STATE);
		params.put("txtMACAddr", HARDWARE_CODE);
		params.put("login_cardmunber", CARD_NUMBER);
		params.put("login_password", CARD_PASSWORD);
		params.put("TxtVidCode", vidCode);
		params.put("__EVENTVALIDATION", __EVENT_VALIDATION);
		params.put("ImgBtnLogin.x", LOGIN_IMGBTNX);
		params.put("ImgBtnLogin.y", LOGIN_IMGBTNY);
		return client.post(LOGIN_URL, headers, params);
	}

	/**
	 * get /BaseInfo/Book.aspx page
	 * @param page
	 * @return
	 * @throws Exception
	 */
	private static WebPage step3(WebClient client, WebPage page) throws Exception {
		Map<String, String> headers = new Hashtable<String, String>();
		headers.putAll(DEFAULT_HEADERS);
		headers.put("Referer", INDEX_URL);
		return client.get(BASEINFO_URL, headers, null, true);
	}

	/**
	 * get /CorpSel/CorpSearchs.aspx page
	 * @param currPage
	 * @return
	 * @throws Exception
	 */
	public static WebPage search(long currPage) throws Exception {
		return search(null, currPage);
	}

	public static WebPage search(WebClient client, long currPage) throws Exception {
		log.info("Getting search page: " + currPage);
		if (client == null) client = getClient();
		Map<String, String> headers = new Hashtable<String, String>();
		headers.putAll(DEFAULT_HEADERS);
		headers.put("Referer", LEFT_URL);
		String url = MessageFormat.format(CORP_SEARCH_URL, currPage);
		WebPage page = client.get(url, headers, null, false);
		log.info("Got search page: " + currPage);
		return page;
	}

	/**
	 * get /CorpSel/CorpSearchsG.aspx page
	 * @param currPage
	 * @return
	 * @throws Exception
	 */
	private static WebPage searchG(long currPage) throws Exception {
		return searchG(null, currPage);
	}

	private static WebPage searchG(WebClient client, long currPage) throws Exception {
		log.info("Getting searchG page: " + currPage);
		if (client == null) client = getClient();
		Map<String, String> headers = new Hashtable<String, String>();
		headers.putAll(DEFAULT_HEADERS);
		headers.put("Referer", LEFT_URL);
		String url = MessageFormat.format(CORP_SEARCHG_URL, currPage);
		WebPage page = client.get(url, headers, null, false);
		log.info("Got searchG page: " + currPage);
		return page;
	}

	/**
	 * get corp list from /CorpSel/CorpSearchs.aspx page, 
	 * total page number is saved in the last corp
	 * @param currPage
	 * @return
	 * @throws Exception
	 */
	public static List<Corp> searchCorps(long currPage) throws Exception {
		WebPage page = search(currPage);
		List<Corp> list = new ArrayList<Corp>();
		Matcher m = CORP_INFO_PATTERN.matcher(page.getResponseString());
		Corp corp = null;
		int i = 0, idx = 1;
		long id = 0, max;
		while (m.find()) {
			id = Long.parseLong(m.group(1));
			i = m.end();
			// if (SyllaServiceUtil.getCorp(id) != null) continue;
			corp = new Corp();
			corp.setId(id);
			corp.setName1(m.group(2));
			corp.setCreateDate1(m.group(3));
			corp.setPageIndex(idx++);
			corp.setType(Corp.CT_GSQY);
			list.add(corp);
		}
		m = CORP_TOTAL_PAGE_PATTERN.matcher(page.getResponseString().substring(i));
		if (m.find()) {
			max = Long.parseLong(m.group(1));
			if (corp != null) corp.setMaxPage(max);
		}
		// if (list.size() <= 0) throw new Exception("Search page " + currPage + " read error!");
		if (list.size() > Constants.PAGE_SIZE) list.remove(0); 
		return list;
	}

	/**
	 * get corp list from /CorpSel/CorpSearchsG.aspx page, 
	 * total page number is saved in the last corp
	 * @param currPage
	 * @param currId
	 * @return
	 * @throws Exception
	 */
	public static List<Corp> searchGCorps(long currPage) throws Exception {
		WebPage page = searchG(currPage);
		List<Corp> list = new ArrayList<Corp>();
		Matcher m = CORP_INFOG_PATTERN.matcher(page.getResponseString());
		Corp corp = null;
		int i = 0, idx = 1;
		long id = 0, max;
		while(m.find()) {
			id = Long.parseLong(m.group(1));
			i = m.end();
			// if (SyllaServiceUtil.getCorp(id) != null) continue;
			corp = new Corp();
			corp.setId(id);
			corp.setName1(m.group(2));
			corp.setCreateDate1(m.group(3));
			corp.setPageIndex(idx++);
			corp.setType(Corp.CT_GTQY);
			list.add(corp);
		}
		// if (list.size() <= 0) throw new Exception("SearchG page " + currPage + " read error!");
		if (list.size() > Constants.PAGE_SIZE) list.remove(0);
		return list;
	}

	/**
	 * get the image(JPEG) from /CorpSel/InfoShowImg.aspx page
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static byte[] image(Image image) throws Exception {
		WebClient client = getClient();
		Map<String, String> headers = new Hashtable<String, String>();
		headers.putAll(DEFAULT_HEADERS);
		headers.put("Referer", INDEX_URL);
		String url = MessageFormat.format(IMAGE_URL, image.getCode());
		WebPage page = client.get(url, headers, null, false);
		return page.getResponseBody();
	}

	/**
	 * get corp info(codes) and scope from /CorpSel/Corp_info.aspx page
	 * @param corp
	 * @return
	 * @throws Exception
	 */
	public static List<Info> info(Corp corp) throws Exception {
		WebClient client = getClient();
		String ref = MessageFormat.format(CORP_SEARCH_URL, corp.getId());
		Map<String, String> headers = new Hashtable<String, String>();
		headers.putAll(DEFAULT_HEADERS);
		headers.put("Referer", ref);
		String url = MessageFormat.format(CORP_INFO_URL, corp.getId());
		WebPage page = client.get(url, headers, null, false);
		List<Info> list = new ArrayList<Info>();
		Matcher m1 = IMAGE_PATTERN.matcher(page.getResponseString());
		Matcher m2;
		Info info = null;
		int i = 0;
		while (m1.find()) {
			info = new Info();
			info.setCorpId(corp.getId());
			info.setType(INFO_TYPES[i++]);
			info.setCode(m1.group(1));
			// info.setStatus(Info.IS_DOWNLOADED);
			list.add(info);
			if (i == CORP_INFO_SCOPE_INDEX) {
				m2 = CORP_INFO_SCOPE_PATTERN.matcher(page.getResponseString().substring(m1.end()));
				if (m2.find()) corp.setScope(m2.group(1));
			}
		}
		return list;
	}

	/**
	 * get corpG info(codes) and scope from /CorpSel/Corp_infoG.aspx page
	 * @param corp
	 * @return
	 * @throws Exception
	 */
	public static List<Info> infoG(Corp corp) throws Exception {
		WebClient client = getClient();
		String ref = MessageFormat.format(CORP_SEARCHG_URL, corp.getId());
		Map<String, String> headers = new Hashtable<String, String>();
		headers.putAll(DEFAULT_HEADERS);
		headers.put("Referer", ref);
		String url = MessageFormat.format(CORP_INFOG_URL, corp.getId());
		WebPage page = client.get(url, headers, null, false);
		List<Info> list = new ArrayList<Info>();
		Matcher m1 = IMAGE_PATTERN.matcher(page.getResponseString());
		Matcher m2;
		Info info = null;
		int i = 0;
		while (m1.find()) {
			info = new Info();
			info.setCorpId(corp.getId());
			info.setType(INFO_TYPES[i++]);
			info.setCode(m1.group(1));
			// info.setStatus(Info.IS_DOWNLOADED);
			list.add(info);
			if (i == CORP_INFO_SCOPE_INDEX) {
				m2 = CORP_INFO_SCOPE_PATTERN.matcher(page.getResponseString().substring(m1.end()));
				if (m2.find()) corp.setScope(m2.group(1));
			}
		}
		return list;
	}

	/**
	 * simple way to delivery/reclaim the WebClient object.
	 * @return
	 */
	public static WebClient getClient() {
		WebClient wc = null;
		synchronized(clients) {
			wc = clients.remove(0);
			clients.add(wc);
		}
		return wc;
	}

	private static final class ClientThread implements Runnable {

		public void run() {
			WebPage page = null;
			int c = 0;
			WebClient client = WebClient.create(HOST);
			// To get search[G] or Corp_info[G] pages, you must login.
			if (Constants.PAGE_THREAD_COUNT > 0 || Constants.INFO_THREAD_COUNT > 0 || Constants.VERIFY_THREAD_COUNT > 0) {
				while (c < Constants.MAX_RETRY) {
					try {
						for (int i = 0; i < LOGIN_STEPS.length; i++) page = LOGIN_STEPS[i].work(client, page);
						Matcher m = BOOK_PATTERN.matcher(page.getResponseString());
						if (m.find() && m.groupCount() == 2) {
							StringBuffer sb = new StringBuffer();
							sb.append("Login times: ").append(m.group(1)).append(". Search corps: ").append(m.group(2));
							log.info(sb.toString());
							break;
						}
					} catch(Exception e) { }
					c++;
				}
				if (c == Constants.MAX_RETRY) {
					log.error("Failed to login and get BaseInfo/Book.aspx page!!!");
					return;
				}
				// must get first page before get other pages
				try {
					if (Constants.PAGE_THREAD_COUNT > 0 || Constants.VERIFY_THREAD_COUNT > 0) search(client, 1);
				} catch(Throwable tt) {
					log.error(tt, tt);
				}
			}
			for (int i = 0; i < Constants.CLIENT_THREAD_COUNT; i++) {
				clients.add(client);
				LACK_SAMEPHORE.v();
			}
		}
	}

	/**
	 * start the downloading, multiple threads
	 * @throws Exception
	 */
	public static void start() throws Exception {
		for (int i = 0; i < Constants.WEB_CLIENT_COUNT; i++) {
			new Thread(new ClientThread()).start();
		}
		// LACK_SAMEPHORE.p();
		Runnable[] rr = new Runnable[] {
			// new Runnable() {public void run() {Searcher.run(Constants.PAGE_THREAD_COUNT);}},
			// new Runnable() {public void run() {Verifier.run(Constants.VERIFY_THREAD_COUNT);}},
			// new Runnable() {public void run() {Informer.run(Constants.INFO_THREAD_COUNT);}},
			// new Runnable() {public void run() {Imager.run(Constants.IMAGE_THREAD_COUNT);}},	
			// new Runnable() {public void run() {Ocrer.run(Constants.OCR_THREAD_COUNT);}},
			new Runnable() {public void run() {Collator.run(Constants.COLLATE_THREAD_COUNT);}},
		};
		for (int i = 0; i < rr.length; i++) {
			new Thread(rr[i]).start();
		}
		// check();
	}

	private static void help() {
		System.out.println("?: print this help.");
		/*
		System.out.println("a: " + CommonUtil.getString("help.ma"));
		System.out.println("b: " + CommonUtil.getString("help.mb"));
		System.out.println("c: " + CommonUtil.getString("help.mc"));
		System.out.println("d: " + CommonUtil.getString("help.md"));
		System.out.println("e: " + CommonUtil.getString("help.me"));
		System.out.println("f: " + CommonUtil.getString("help.mf"));
		*/
		System.out.println("q: exit this program!");
		System.out.print("Please select a function: ");
	}

	public static void setLogLevel() throws Exception {
		LogManager.getRootLogger()
			.setLevel(Level.toLevel(PropsUtil.get(Constants.PROP_LOG4J_ROOT_LEVEL, "INFO")));
	}

	public static void showDataSourceInfo() throws Exception {
		String[] dataSources = PropsUtil.getArray(Constants.PROP_SPRING_HIBERNATE_DATA_SOURCES);
		DataSource ds;
		Connection conn = null;
		String dsName, dbName;
		int dbMajorVersion;
		for (int i = 0; i < dataSources.length; i++) {
			try {
				dsName = dataSources[i];
				ds = (DataSource) SpringUtil.getBean(dsName);
				conn = ds.getConnection();
				DatabaseMetaData metaData = conn.getMetaData();
				dbName = metaData.getDatabaseProductName();
				dbMajorVersion = metaData.getDatabaseMajorVersion();
				log.info(CommonUtil.getString("database.ds.info", new Object[]{dsName, dbName, new Integer(dbMajorVersion)}));
			} catch (Exception e) {
				e.printStackTrace(System.err);
			} finally {
				if (conn != null) try {conn.close();} catch (Exception exp) {}
			}
		}
	}

	public static void showSystemProperties() throws Exception {
		CommonUtil.dumpMap(System.getProperties(), System.out);
	}

	public static void showParameters() throws Exception {
		System.out.println(MessageFormat.format("Using {0} web clients, {1} threads per client. Threads: {2} page, {3} info, {4} image and {5} ocr!!!", 
				Constants.WEB_CLIENT_COUNT, Constants.CLIENT_THREAD_COUNT,
				Constants.PAGE_THREAD_COUNT, Constants.INFO_THREAD_COUNT, 
				Constants.IMAGE_THREAD_COUNT, Constants.OCR_THREAD_COUNT));
	}

	public static void main(String[] args) throws Exception {
		try {
			System.loadLibrary("ocr");
			setLogLevel();
			showDataSourceInfo();
			showParameters();
			// showSystemProperties();
			Niffler.start();
		} catch(Exception e) {
			log.error(e, e);
		} finally {
		}
	}

	/**
	 * check ./images/ii/ixxxxxxx folders, if miss image, store it
	 * @throws Exception
	 */
	private static void check() throws Exception {
		final long FOLDER_SIZE = 8192;
		final String FOLDER_NAME = "./images/ii/{0,number,0000000}/";
		final String IIMAGE_FILENAME = "i{0,number,0000000}.jpg";
		String name;
		File folder, file;
		long fCount, iCount, fTotal = 0, iTotal = 0;
		Long idx = 0L;
		List<Image> images;
		Image image;
		while(true) {
			name = MessageFormat.format(FOLDER_NAME, idx);
			folder = new File(name);
			if (!folder.exists()) break;
			fCount = folder.listFiles().length;
			iCount = SyllaServiceUtil.getImageCount(idx, idx + FOLDER_SIZE);
			fTotal += fCount;
			iTotal += iCount;
			if (iCount > fCount) {
				images = SyllaServiceUtil.getImages(idx, idx + FOLDER_SIZE);
				for (Iterator<Image> it = images.iterator(); it.hasNext(); ) {
					image = it.next();
					file = new File(folder, MessageFormat.format(IIMAGE_FILENAME, image.getIndex()));
					if (!file.exists()) {
						System.out.println(file.getAbsolutePath());
						writeBytes(file.getAbsolutePath(), Niffler.image(image));
					}
				}
			}
			idx += FOLDER_SIZE;
		}
		System.out.println("Total images: " + iTotal + ", total files: " + fTotal);
	}

	private static void writeBytes(String file, byte[] b) throws Exception {
		File f = new File(file);
		OutputStream os = new FileOutputStream(f);
		os.write(b);
		os.close();
	}

}
