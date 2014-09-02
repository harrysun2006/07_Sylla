/*
 * Created on 2005-9-27
 *
 * created by harry
 */
package com.sylla;

/**
 * 
 * @author hysun
 * 
 */
public interface Step {

	public WebPage work(WebClient client, WebPage page) throws Exception;

}
