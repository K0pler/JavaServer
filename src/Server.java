import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(1234);
		
		for (;;) {
			System.out.println("Waiting for client connection");
            Socket socket = server.accept();
            System.out.println("Have client connection, launching thread");
			new ServerInstance(socket).start();
		}
		
		/*System.out.println("Server created, waiting for client");
		
		Socket socket = server.accept();
		
		System.out.println("Client has connected");
		
		InputStream inputStream = socket.getInputStream();
		
		InputStreamReader isReader = new InputStreamReader(inputStream);
		
		BufferedReader inputReader = new BufferedReader(isReader);
		
		System.out.println("Client wrote: " + inputReader.readLine());
		
		OutputStream outputStream = socket.getOutputStream();
		
		PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));
		
		outputWriter.println("Hello from server");
		
		outputWriter.flush();*/
		

	}

}
