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
	boolean terminate = false;
	
	public ServerInstance(Socket s) {
		socket = s;
	}
	
	public void run() {
		try {
			
			InputStream inputStream = socket.getInputStream();
			
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			
			OutputStream outputStream = socket.getOutputStream();
			
			PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));
			
			while (! terminate) {
				process ( inputReader, outputWriter);
			}
			
			socket.close(); // Graceful termination
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	void process(BufferedReader input, PrintWriter output) throws IOException {
		String line = input.readLine();
		//echo the line back
		output.println("Echo: " + line);
		output.flush();
		
		if (line.equalsIgnoreCase("exit")) {
			terminate = true;
			System.out.println("Connection terminated");
		}
	}

}
