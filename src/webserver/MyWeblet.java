package webserver;

import java.io.*;
import java.util.*;


public abstract class MyWeblet {
	
	String contentType = null;
	String redirect = null;
	int errorCode = 200;
	String description = "OK";
	
	//Override this method to process the request
	public abstract void doRequst(String resource, String queryString, HashMap<String,String> parameters, PrintWriter out);
	
	//Call this method to set content-type, default being text/html
	protected void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	// Call this method to send back an error.
	protected void setError(int errorCode, String description) {
		this.errorCode = errorCode;
		this.description = description;
	}
	
	//Call this method to tell the browser to go to a different URL instead
	protected void sendRedirect(String newUrl) {
		this.redirect = newUrl;
	}
}
