package webserver;

import java.io.*;
import java.util.*;


public abstract class MyWeblet {
	
	String contentType = null;
	String redirect = null;
	int error = 0;
	String errDesc = null;
	HashMap<String, String> requestCookies = null;
	HashMap<String, String> responseCookies = new HashMap<String, String>();
	
	//Override this method to process the request
	public abstract void doRequest(String resource, String queryString, HashMap<String,String> parameters, PrintWriter out);
	
	//Call this method to set content-type, default being text/html
	protected void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	// Call this method to send back an error.
	protected void setError(int errorCode, String description) {
		this.error = errorCode;
		this.errDesc = description;
	}
	
	//Call this method to tell the browser to go to a different URL instead
	protected void sendRedirect(String newUrl) {
		this.redirect = newUrl;
	}
	
	public String getRequestCookie(String cookieName) {
		return requestCookies.get(cookieName);
	}
	
	public void setResponseCookie(String cookieName, String cookieValue) {
		responseCookies.put(cookieName, cookieValue);
	}
}
