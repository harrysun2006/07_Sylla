package com.sylla;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebClient {

	private static final Log log = LogFactory.getLog(WebClient.class);

	private static final int DEFAULT_TIMEOUT = 20000;

	private static final int MAX_CONNECTIONS = 10;

	private static final int MAX_TOTAL_CONNECTIONS = 600;

	private static final int MAX_RETRY_COUNT = 3;

	private static final HttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(MAX_RETRY_COUNT, true);

	private HttpClient client;

	private WebClient() {
		client = new HttpClient();
	}

	static {
		HttpURLConnection.setFollowRedirects(true);
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.single-cookie-header", new Boolean(true));
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.NETSCAPE);
	}

	public static WebClient create(String host) {
		WebClient wc = new WebClient();
		HttpClient client = wc.client;
		client.getParams().setContentCharset("UTF-8");
		HttpConnectionManager conManager = new MultiThreadedHttpConnectionManager();
		wc.client.setHttpConnectionManager(conManager);
		HttpClientParams clientParams = client.getParams();
		clientParams.setCookiePolicy(CookiePolicy.NETSCAPE);
		HostConfiguration hostConf = client.getHostConfiguration();
		hostConf.setHost(host);
		HttpConnectionManagerParams hcmParams = client.getHttpConnectionManager().getParams();
		hcmParams.setConnectionTimeout(DEFAULT_TIMEOUT);
		hcmParams.setSoTimeout(DEFAULT_TIMEOUT);
		hcmParams.setDefaultMaxConnectionsPerHost(MAX_CONNECTIONS);
		hcmParams.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
		// System.out.println(hcmParams.getReceiveBufferSize());
		// System.out.println(hcmParams.getSendBufferSize());
		// hcmParams.setReceiveBufferSize(32768);
		return wc;
	}

	public static void debug(WebPage page) throws Exception {
		debug(page, true);
	}

	public static void debug(WebPage page, boolean detail) throws Exception {
		Header[] headers = page.getRequestHeaders();
		StringBuffer sb = new StringBuffer();
		sb.append("\nRequest headers: \n");
		for (int i = 0; i < headers.length; i++) {
			sb.append(headers[i].getName()).append(": ")
					.append(headers[i].getValue()).append("\n");
		}
		sb.append("\nRequest entity: \n");
		sb.append(page.getRequestEntity());
		headers = page.getResponseHeaders();
		sb.append("\nResponse headers: \n");
		for (int i = 0; i < headers.length; i++) {
			sb.append(headers[i].getName()).append(": ")
					.append(headers[i].getValue()).append("\n");
		}
		sb.append("\nURL: ").append(page.getURI()).append("\n");
		// sb.append("Page title: ").append(page.getTitleText()).append("\n");
		sb.append("Status: ").append(page.getStatusText()).append("\n");
		if (detail)
			sb.append("Content: \n").append(page.getResponseString());
		log.info(sb.toString());
	}

	private void setHeaders(HttpMethod method, List<Header> headers) {
		if (headers == null) return;
		for (Iterator<Header> it = headers.iterator(); it.hasNext();) {
			method.addRequestHeader(it.next());
		}
	}

	private void setHeaders(HttpMethod method, Map<String, String> headers) {
		if (headers == null) return;
		Entry<String, String> e;
		for (Iterator<Entry<String, String>> it = headers.entrySet().iterator(); it.hasNext();) {
			e = it.next();
			method.addRequestHeader(new Header(e.getKey(), e.getValue()));
		}
	}

	private void setQueryString(HttpMethod method, Map<String, String> params) {
		if (params == null) return;
		NameValuePair[] nvp = new NameValuePair[params.size()];
		int index = 0;
		Entry<String, String> e;
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
			e = it.next();
			nvp[index++] = new NameValuePair(e.getKey(), e.getValue());
		}
		method.setQueryString(nvp);
	}

	private void setParameters(PostMethod method, Map<String, String> params) {
		if (params == null) return;
		Entry<String, String> e;
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
			e = it.next();
			method.addParameter(new NameValuePair(e.getKey(), e.getValue()));
		}
	}

	public WebPage get(String url, Map<String, String> headers, Map<String, String> params, boolean redirect) throws Exception {
		WebPage p;
		GetMethod method = new GetMethod(url);
		method.setFollowRedirects(redirect);
		try {
			setHeaders(method, headers);
			setQueryString(method, params);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_MOVED_TEMPORARILY && redirect) {
				p = redirect(method);
			} else {
				p = new WebPage(method);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			method.abort();
			method.releaseConnection();
		}
		return p;
	}

	public WebPage post(String url, Map<String, String> headers, Map<String, String> params)
			throws Exception {
		WebPage p;
		PostMethod method = new PostMethod(url);
		try {
			setHeaders(method, headers);
			setParameters(method, params);
			method.getParams().setContentCharset("UTF-8");
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
				p = redirect(method);
			} else {
				p = new WebPage(method);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			method.abort();
			method.releaseConnection();
		}
		return p;
	}

	private WebPage redirect(HttpMethod method) throws Exception {
		GetMethod redirect = null;
		WebPage p;
		try {
			Header location = method.getResponseHeader("Location");
			Header[] headers = method.getRequestHeaders();
			redirect = new GetMethod(location.getValue());
			Header cookie = redirect.getRequestHeader("Cookie");
			for (int i = 0; i < headers.length; i++)
				redirect.addRequestHeader(headers[i]);
			if (cookie != null) redirect.addRequestHeader(cookie);
			redirect.removeRequestHeader("Content-Type");
			redirect.removeRequestHeader("Content-Length");
			redirect.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);
			int code = client.executeMethod(redirect);
			if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
				p = redirect(redirect);
			} else {
				p = new WebPage(redirect);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (redirect != null) {
				method.abort();
				redirect.releaseConnection();
			}
		}
		return p;
	}
}
