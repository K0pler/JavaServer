import java.io.*;
import java.net.*;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket client = new Socket("localhost", 1234);
		
		OutputStream outputStream = client.getOutputStream();
		
		PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));
		
		InputStream inputStream = client.getInputStream();
		
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
		
		BufferedReader cmdReader = new BufferedReader(new InputStreamReader(System.in));
		
		for (;;) {
			System.out.print("Command> ");
			String line = cmdReader.readLine();
			outputWriter.println(line);
			outputWriter.flush();
			if (line.equalsIgnoreCase("exit")) {
				break;
			}
			
			line = inputReader.readLine();
			System.out.println("Server wrote: " + line);
		}
		client.close();
	}

}
