package webserver;

import java.io.*;
import java.io.PrintWriter;

public class MyWebletProcessor {

	@SuppressWarnings("rawtypes")
	public void processMyWeblet(Class cls, PrintWriter outputWriter) {
		// TODO Auto-generated method stub
		try {
			Object instance = cls.newInstance();
			
			MyWeblet myWeblet = (MyWeblet) instance;
			
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			
			PrintWriter out = new PrintWriter(byteArray);
			
			myWeblet.doRequst(null, null, null, out);
			
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

}
