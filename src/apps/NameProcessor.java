package apps;

import java.io.PrintWriter;
import java.util.HashMap;

public class NameProcessor extends webserver.MyWeblet {

	@Override
	public void doRequst(String resource, String queryString, HashMap<String, String> parameters, PrintWriter out) {
		// TODO Auto-generated method stub
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("Hello " + parameters.get("firstname") + " " + parameters.get("lastname"));
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
