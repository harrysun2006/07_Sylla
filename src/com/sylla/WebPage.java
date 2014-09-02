package com.sylla;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class WebPage {

	private String path;

	private String uri;

	private String statusText;

	private int statusCode;

	private Header[] requestHeaders;

	private String queryString;

	private NameValuePair[] requestParams;

	private String requestCharSet;

	private String requestEntity;

	private Header[] responseHeaders;

	private Header[] responseFooters;

	private byte[] responseBody;

	private String responseString;

	public WebPage(HttpMethod method) throws IOException {
		this.path = method.getPath();
		this.uri = method.getURI().toString();

		this.requestHeaders = method.getRequestHeaders();
		this.queryString = method.getQueryString();
		if (method instanceof PostMethod) {
			PostMethod post = (PostMethod) method;
			this.requestParams = post.getParameters();
			this.requestCharSet = post.getRequestCharSet();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			post.getRequestEntity().writeRequest(os);
			this.requestEntity = new String(os.toString(post.getRequestCharSet()));
		}
		this.responseHeaders = method.getResponseHeaders();
		this.responseFooters = method.getResponseFooters();
		this.responseBody = method.getResponseBody();
		this.responseString = method.getResponseBodyAsString();
		this.statusCode = method.getStatusCode();
		this.statusText = method.getStatusText();
	}

	public String getPath() {
		return path;
	}

	public String getURI() {
		return uri;
	}

	public String getQueryString() {
		return (queryString == null) ? "" : queryString;
	}

	public Header[] getRequestHeaders() {
		return requestHeaders;
	}

	public NameValuePair[] getRequestParams() {
		return requestParams;
	}

	public byte[] getResponseBody() {
		return responseBody;
	}

	public Header[] getResponseFooters() {
		return responseFooters;
	}

	public Header[] getResponseHeaders() {
		return responseHeaders;
	}

	public String getResponseString() {
		return (responseString == null) ? "" : responseString;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public String getRequestCharSet() {
		return (requestCharSet == null) ? "" : requestCharSet;
	}

	public String getRequestEntity() {
		return (requestEntity == null) ? "" : requestEntity;
	}

}
