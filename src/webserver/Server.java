package webserver;
import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(1024);
		
		for (;;) {
			System.out.println("Waiting for client connection");
            Socket socket = server.accept();
            System.out.println("Have client connection, launching thread");
			new ServerInstance(socket).start();
		}

	}

}
