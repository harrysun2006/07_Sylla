package com.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FormEncodingType;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.sylla.CodeChecker;

public class Bargain {

	private final static Log log = LogFactory.getLog(Bargain.class);

	private final static String LOGIN_URL = "http://218.4.189.213/login.aspx";

	private final static String VICODE_URL = "http://218.4.189.213/VicCode.aspx";

	private final static String __EVENT_TARGET = "";

	private final static String __EVENT_ARGUMENT = "";

	private final static String __VIEW_STATE = "/wEPDwUKLTg5MzAwODMzNmQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFC0ltZ0J0bkxvZ2lu9qONp4vpfpo+m0hpiHy1LmxxWK4=";

	private final static String __EVENT_VALIDATION = "/wEWBQKe8Mh4Avi6n6EGArjOqqsKAri//VkCnMC7yAcXvR1YLFDV21zLgqC+1KbuIThTPw==";

	private final static String CARD_NUMBER = "001000000548";

	private final static String CARD_PASSWORD = "263651967016";

	private final static String HARDWARE_CODE = "BFEBFBFF000006FDx86 Family 6 Model 15 Stepping 13CPU0Intel(R) Pentium(R) Dual  CPU  E2180  @ 2.00GHz1363trueMicro-StartrueMS-7255 V1.2To be filled by O.E.M.1.2底板undefinedundefinedBase Board";

	private final static String LOGIN_IMGBTNX = "56";

	private final static String LOGIN_IMGBTNY = "17";

	private final static String BASEINFO_URL = "http://218.4.189.213/BaseInfo/Book.aspx";

	private final static int MAX_SPEED = 15;

	private WebClient webClient = null;

	private CookieManager cookieMan = null;

	private String vidCode = "";

	static {
		WebClient.setIgnoreOutsideContent(true);
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.single-cookie-header", new Boolean(true));
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.NETSCAPE);
	}

	private static void debug(Page page) throws Exception {
		debug(page, true);
	}

	private static void debug(Page page, boolean detail) throws Exception {
		if (page != null && page instanceof HtmlPage) {
			HtmlPage htmlPage = (HtmlPage) page;
			WebResponse response = page.getWebResponse();
			List l = response.getResponseHeaders();
			Iterator it = l.iterator();
			NameValuePair header;
			StringBuffer sb = new StringBuffer();
			sb.append("\nResponse headers: \n");
			while (it.hasNext()) {
				header = (NameValuePair) it.next();
				sb.append(header.getName()).append(": ").append(header.getValue()).append("\n");
			}
			sb.append("Page URL: ").append(response.getUrl()).append("\n");
			sb.append("Page title: ").append(htmlPage.getTitleText()).append("\n");
			sb.append("Page state: ").append(htmlPage.getReadyState()).append("\n");
			if (detail) sb.append("Page content: \n").append(response.getContentAsString());
			log.info(sb.toString());
		}
	}

	private static void debug(HtmlForm form) throws Exception {
		if (form != null) {
			StringBuffer sb = new StringBuffer(50);
			sb.append("form[");
			DomAttr attr = null;
			for (Iterator it = form.getAttributesCollection().iterator(); it.hasNext();) {
				attr = (DomAttr) it.next();
				sb.append(attr.getName()).append(" = ").append(attr.getValue()).append(", ");
			}
			if (attr != null) sb.delete(sb.length() - 2, sb.length());
			sb.append("]\r\n{");
			HtmlElement element = null;
			HtmlInput input;
			HtmlTextArea textarea;
			for (Iterator<HtmlElement> it = form.getAllHtmlChildElements().iterator(); it.hasNext();) {
				element = it.next();
				if (HtmlInput.class.isInstance(element)) {
					input = (HtmlInput) element;
					sb.append(input.getNameAttribute())
						.append(" = ")
						.append(input.getValueAttribute())
						.append(", ");
				} else if (HtmlTextArea.class.isInstance(element)) {
					textarea = (HtmlTextArea) element;
					sb.append(textarea.getNameAttribute())
						.append(" = ")
						.append(textarea.getText())
						.append(", ");
				} else {
					// sb.append(element.asText()).append(", ");
				}
			}
			if (element != null)
				sb.delete(sb.length() - 2, sb.length());
			sb.append("}");
			log.info(sb.toString());
		}
	}

	private static void debug(CookieManager cm) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\nCookies: \n");
		Cookie cookie;
		for (Iterator<Cookie> it = cm.getCookies().iterator(); it.hasNext();) {
			cookie = it.next();
			sb.append(cookie.getName())
				.append(" = ")
				.append(cookie.getValue())
				.append("\n");
		}
		log.info(sb.toString());
	}

	// open the login page: login.aspx
	private Page step1(Page page) throws Exception {
		URL url = new URL(LOGIN_URL);
		Map<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("Accept", "*/*");
		headers.put("Referer", "http://www.szgsw.com");
		headers.put("Accept-Language", "zh-cn");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		headers.put("Host", "218.4.189.213");
		headers.put("Connection", "Keep-Alive");
		headers.put("Cache-Control", "no-cache");
		WebRequestSettings settings = new WebRequestSettings(url, HttpMethod.GET);
		settings.setAdditionalHeaders(headers);
		return webClient.getPage(settings);
	}

	// get the validate code picture: VicCode.aspx
	private Page step2(Page page) throws Exception {
		URL url = new URL(VICODE_URL);
		Map<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("Accept", "*/*");
		headers.put("Referer", LOGIN_URL);
		headers.put("Accept-Language", "zh-cn");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		headers.put("Host", "218.4.189.213");
		headers.put("Connection", "Keep-Alive");
		headers.put("Cache-Control", "no-cache");
		WebRequestSettings settings = new WebRequestSettings(url, HttpMethod.GET);
		settings.setAdditionalHeaders(headers);
		return webClient.getPage(settings);
	}

	// submit login page
	private Page step3(Page page) throws Exception {
		URL url = new URL(LOGIN_URL);
		Map<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, */*");
		headers.put("Referer", LOGIN_URL);
		headers.put("Accept-Language", "zh-cn");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		headers.put("Host", "218.4.189.213");
		headers.put("Connection", "Keep-Alive");
		headers.put("Cache-Control", "no-cache");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("__EVENTTARGET", __EVENT_TARGET));
		params.add(new NameValuePair("__EVENTARGUMENT", __EVENT_ARGUMENT));
		params.add(new NameValuePair("__VIEWSTATE", __VIEW_STATE));
		params.add(new NameValuePair("txtMACAddr", HARDWARE_CODE));
		params.add(new NameValuePair("login_cardmunber", CARD_NUMBER));
		params.add(new NameValuePair("login_password", CARD_PASSWORD));
		params.add(new NameValuePair("TxtVidCode", vidCode));
		params.add(new NameValuePair("__EVENTVALIDATION", __EVENT_VALIDATION));
		params.add(new NameValuePair("ImgBtnLogin.x", LOGIN_IMGBTNX));
		params.add(new NameValuePair("ImgBtnLogin.y", LOGIN_IMGBTNY));
		WebRequestSettings settings = new WebRequestSettings(url, HttpMethod.POST);
		settings.setAdditionalHeaders(headers);
		settings.setRequestParameters(params);
		settings.setEncodingType(FormEncodingType.URL_ENCODED);
		settings.setCharset("UTF-8");
		return webClient.getPage(settings);
	}

	// Baseinfo page
	private Page step4(Page page) throws Exception {
		URL url = new URL(BASEINFO_URL);
		Map<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, */*");
		headers.put("Referer", "http://218.4.189.213/index.htm");
		headers.put("Accept-Language", "zh-cn");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		headers.put("Host", "218.4.189.213");
		headers.put("Connection", "Keep-Alive");
		headers.put("Cache-Control", "no-cache");
		WebRequestSettings settings = new WebRequestSettings(url, HttpMethod.GET);
		settings.setAdditionalHeaders(headers);
		return webClient.getPage(settings);
	}

	void run() throws Exception {
		cookieMan = new CookieManager();
		cookieMan.setCookiesEnabled(true);
		// cookieMan.addCookie(new Cookie("218.4.189.213", "UserInfo", USER_INFO));
		webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6_0);
		webClient.setTimeout(MAX_SPEED * 1000);
		webClient.setCookieManager(cookieMan);
		webClient.setJavaScriptEnabled(false);
		webClient.setRedirectEnabled(true);
		webClient.setRefreshHandler(new ThreadedRefreshHandler());
		webClient.setThrowExceptionOnScriptError(true);
		Page page = null;
		page = step1(page);
		debug(page);
		page = step2(page);
		debug(page);
		byte[] vicPic = page.getWebResponse().getResponseBody();
		vidCode = CodeChecker.getCode(vicPic);
		page = step3(page);
		debug(page);
		page = step4(page);
		debug(page);
	}

}
