package webserver;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

public class MyWebletProcessor {

	@SuppressWarnings("rawtypes")
	public void processMyWeblet(Class cls, PrintWriter outputWriter, String resource, String queryString) {
		// TODO Auto-generated method stub
		try {
			Object instance = cls.newInstance();
			
			MyWeblet myWeblet = (MyWeblet) instance;
			
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			
			PrintWriter out = new PrintWriter(byteArray);
			
			
			
			myWeblet.doRequst(resource, queryString, parseQueryString(queryString), out);
			
			if (myWeblet.redirect != null) {
				outputWriter.println("HTTP/1.0 302 Found");
			} else {
				outputWriter.println("HTTP/1.0 " + myWeblet.errorCode + " " + myWeblet.description);
			}
			
			if (myWeblet.contentType != null) {
				outputWriter.println("Content-Type: " + myWeblet.contentType);
			} else {
				outputWriter.println("Content-Type: text/html");
			}
			
			if (myWeblet.redirect != null) {
			outputWriter.println("Location: " + myWeblet.redirect);
			}
			outputWriter.println();
			
			out.flush();
			
			outputWriter.println(byteArray.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			outputWriter.close();
		} catch (Exception ex) {
			
		}
	}
	
	HashMap<String, String> parseQueryString(String queryString) throws UnsupportedEncodingException {
	HashMap<String, String> parameters = new HashMap<String, String>();
	
	if (queryString == null) {
		return parameters;
	}
	
	StringTokenizer qtokens = new StringTokenizer(queryString, "&");
	
	while (qtokens.hasMoreTokens()) {
		String[] ptokens = qtokens.nextToken().split("=");
		
		if (ptokens.length == 2) {
			
			String parameterName = URLDecoder.decode(ptokens[0], "utf-8");
			String parameterValue = URLDecoder.decode(ptokens[1], "utf-8");
			parameters.put(parameterName, parameterValue);
		}
	}
	return parameters;
	}

}
