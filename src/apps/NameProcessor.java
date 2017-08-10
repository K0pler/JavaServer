package apps;

import java.io.PrintWriter;
import java.util.HashMap;

public class NameProcessor extends webserver.MyWeblet {
	
	static int cookieNumber = 1;
	static Object cookieLock = new Object();
	
	@Override
	public void doRequest(String resource, String queryString, HashMap<String, String> parameters, PrintWriter out) {
		// TODO Auto-generated method stub
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("Hello " + parameters.get("firstname") + " " + parameters.get("lastname"));
		String cookie = getRequestCookie("TestCookie");
		
		if (cookie == null) {
			synchronized (cookieLock) {
				cookie = "Cookie_" + cookieNumber;
			}
			setResponseCookie("TestCookie", cookie);
			System.out.println("Visiting browser does not have a cookie");
			System.out.println("Setting cookie to: " + cookie);
		} else {
			System.out.println("Browser hase cookie: " + cookie);
		}
		
		out.println( "<P> The time is now ");
	      out.println( new java.util.Date());
	      out.println( "<P>The Resource Name is " +
	                            resource );

	      out.println( "<P>The Query String = " +
	                            queryString );
	      out.println( "</BODY>");
	      out.println( "</HTML>");
	}

}
