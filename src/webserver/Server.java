package webserver;
import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(1024);
		
		for (String className: MyWebletConfigs.serverStartupClasses) {
			
			try {
				@SuppressWarnings("rawtypes")
				Class cls = Class.forName(className);
				ServerStartup serverStartupObject = (ServerStartup) cls.newInstance();
				// Call the startup method
				serverStartupObject.onServerStartup();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		for (;;) {
			System.out.println("Waiting for client connection");
            Socket socket = server.accept();
            System.out.println("Have client connection, launching thread");
			new ServerInstance(socket).start();
		}
		
		

	}

}
