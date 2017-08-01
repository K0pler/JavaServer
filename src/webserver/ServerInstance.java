package webserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

public class ServerInstance extends Thread {
	
	Socket socket;
	
	public ServerInstance(Socket s) {
		socket = s;
	}
	
	public void run() {
		try {
			
			InputStream inputStream = socket.getInputStream();
			
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			
			OutputStream outputStream = socket.getOutputStream();
			
			PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));
			
			for (;;) {
				String line = inputReader.readLine();
				System.out.println(line);
				if (line.equals("")) {
					break;
				}
			}
			
			outputWriter.println("HTTP/1.0 200 OK");
			  outputWriter.println("Content-Type: text/html");
			  outputWriter.println(); // The empty line
			  outputWriter.println("<HTML>");
			  outputWriter.println("<HEAD>");
			  outputWriter.println("<link rel='shortcut icon' href='about:blank' />");
			  outputWriter.println("</HEAD>");
			  outputWriter.println("<BODY>");
			  outputWriter.println("Hello from ");
			  outputWriter.println("My Very Own ");
			  outputWriter.println("Web Server");
			  outputWriter.println("</BODY>");
			  outputWriter.println("</HTML>");
			  outputWriter.close();
			  inputStream.close();
			  socket.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
