package apps;

import java.io.PrintWriter;
import java.util.HashMap;

public class HelloWorldMyWeblet extends webserver.MyWeblet {

	@Override
	public void doRequst(String resource, String queryString, HashMap<String, String> parameters, PrintWriter out) {
		// TODO Auto-generated method stub
		setContentType("text/plain");
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("<H2> Hello, World</H2>");
		out.println("Hello from My First MyWeblet");
		out.println("</BODY>");
		out.println("</HTML>");
	}

}
