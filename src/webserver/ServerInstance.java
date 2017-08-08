package webserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
	
	String lookupContentType( String name )
    {
        name = name.toLowerCase(); // ignore case

        if ( name.endsWith(".htm") || name.endsWith(".html"))
            return "text/html";
        if ( name.endsWith(".txt") || name.endsWith(".text"))
            return "text/plain";
        if ( name.endsWith(".xml"))
            return "text/xml";
        if ( name.endsWith(".css"))
            return "text/css";
        if ( name.endsWith(".gif"))
            return "image/gif";
        if ( name.endsWith(".png"))
            return "image/png";
        if ( name.endsWith(".jpg") || name.endsWith(".jpeg"))
            return "image/jpeg";
        if ( name.endsWith(".wav"))
            return "audio/wav";
        if ( name.endsWith(".mpg") || name.endsWith(".mpeg"))
            return "video/mpeg";

        // Unknown extension

        return "text/plain"; // This will just show the file in plain-text
    }
	
	@SuppressWarnings("resource")
	public void run()
    {
        try {
          // Get the input/output as usual
          InputStream inputStream =
                  socket.getInputStream();
          BufferedReader inputReader =
            new BufferedReader(
               new InputStreamReader( inputStream ));
          OutputStream outputStream =
            socket.getOutputStream();
          PrintWriter outputWriter =
            new PrintWriter(
                new OutputStreamWriter( outputStream ));
   
          // Get the request line separately from the rest of the headers
          String requestLine = inputReader.readLine();
          System.out.println("Request line = " + requestLine);

          // Now read until the empty line, discard the header lines

          for (;;)
          {
            String line = inputReader.readLine();
            if ( line.equals( "" ))
                break;
            // System.out.println(line);
          }

          String[] tokens = requestLine.split(" ");
          String resource = tokens[1];
          String queryString = null;
          int qIndex = resource.indexOf('?');
          if (qIndex > 0) {
        	  queryString = resource.substring(qIndex + 1);
        	  System.out.println("Query String=" + queryString);
        	  resource = resource.substring(0, qIndex);
          }
          resource = URLDecoder.decode(resource, "UTF-8");
          
          for (int i = 0; i < MyWebletConfigs.myWebletConfigs.length; i++) {
        	  String url = MyWebletConfigs.myWebletConfigs[i].url;
        	  @SuppressWarnings("rawtypes")
			Class cls = MyWebletConfigs.myWebletConfigs[i].cls;
        	  
        	  //Compare url against resource name
        	  if (url.equalsIgnoreCase(resource)) {
        		  MyWebletProcessor mwp = new MyWebletProcessor();
        		  
        		  mwp.processMyWeblet(cls, outputWriter, resource, queryString);
        		  
        		  inputStream.close();
        		  socket.close();
        		  
        		  return;
        	  }
          }

          File dir = new File("D:\\Programmering\\MyOwnServerFiles" );
          File file  = new File( dir, resource );
          if ( file.exists() && file.isDirectory())
            file = new File( file, "index.html");

          if ( ! file.exists())
          {
            System.out.println("File " +  file.getAbsolutePath() + " does not exist");
            // Send error
            outputWriter.println("HTTP/1.0 404 Not Found");
            outputWriter.println(); // The empty line
          } else
          {
            System.out.println("Sending file " + file.getAbsolutePath());

            // Send the success header

            outputWriter.println("HTTP/1.0 200 OK");
            
            String ctype = lookupContentType( file.getName());
            System.out.println("Content-Type: " + ctype);
            
            outputWriter.println("Content-Type: " + ctype);
            outputWriter.println(); // The empty line

            // Send out the file
            outputWriter.flush();
            FileInputStream in = new FileInputStream( file );
            int c;
            while (( c = in.read()) >= 0 )
              outputWriter.write( c );
            in.close();
          }
          outputWriter.close();
          inputStream.close();
          socket.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
