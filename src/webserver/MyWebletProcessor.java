package webserver;

import java.io.*;
import java.util.*;
import java.net.*;

public class MyWebletProcessor
{
    HashMap<String,String> getRequestCookies( String cookieHeaders )
    {
        HashMap<String,String> requestCookies = new HashMap<String,String>();

        if ( cookieHeaders == null || ! cookieHeaders.startsWith( "Cookie: "))
            return requestCookies;  // Empty container, no cookies

        // Remove the leading "Cookie:"
        String lineSubstr = cookieHeaders.substring( "Cookie: ".length());
        StringTokenizer cookieHdrTokenizer =
                new StringTokenizer( lineSubstr, ";" );
        while ( cookieHdrTokenizer.hasMoreTokens())
        {
          String[] cookieTokens = 
            cookieHdrTokenizer.nextToken().split( "=" );
          if ( cookieTokens.length == 2 )
          {
            // Get the cookie name, make sure to remove spaces around it
            String cookieName = cookieTokens[0].trim();
            String cookieValue = cookieTokens[1].trim();
            requestCookies.put( cookieName, cookieValue );
          }
        }

        return requestCookies;
    }

    void printCookieHeaders( HashMap<String,String> responseCookies,
                             PrintWriter output )
    {
        for (String cookieName: responseCookies.keySet())
        {
            output.print("Set-Cookie: " );
            output.print( cookieName );
            output.print( "=" );
            String cookieValue = responseCookies.get( cookieName );
            output.print( cookieValue );
            output.println( "; Path=/" );
        }
    }

    HashMap<String,String> parseQueryString( String queryString )
         throws UnsupportedEncodingException
    {
        HashMap<String,String> parameters = new HashMap<String,String>();

        if ( queryString == null )
            return parameters;

        StringTokenizer qtokens = new StringTokenizer( queryString, "&" );
        while ( qtokens.hasMoreTokens())
        {
            String[] ptokens = qtokens.nextToken().split("=");
            if ( ptokens.length == 2 )
            {
                String parameterName = 
                    URLDecoder.decode( ptokens[0], "utf-8" );
                String parameterValue = 
                    URLDecoder.decode( ptokens[1], "utf-8" );
                parameters.put( parameterName, parameterValue );
             }
        }

        return parameters;
    }

    void processMyWeblet( @SuppressWarnings("rawtypes") Class cls, PrintWriter outputWriter, String resource, String queryString, String cookieHeaderLine )
    {
        try {
            Object instance = cls.newInstance();
                // This makes a new object of class 'cls'

            MyWeblet myWeblet = (MyWeblet) instance;
               // The class 'cls' subclasses mvows.MyWeblet,
               // therefore we can cast the object down

            myWeblet.requestCookies = getRequestCookies( cookieHeaderLine );

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            PrintWriter out = new PrintWriter( byteArray );


            HashMap<String,String> parameters = parseQueryString( queryString );

            myWeblet.doRequest( resource, queryString, parameters, out );

            // When "doRequest" returns, check for redirect or error
            if ( myWeblet.redirect != null )
            {
                outputWriter.println("HTTP/1.0 302 Found" );
                outputWriter.println("Location: " + myWeblet.redirect );
                // Print out the cookies as part of the headers
                printCookieHeaders( myWeblet.responseCookies, outputWriter );
                outputWriter.println(); // End headers with empty line
            } else if ( myWeblet.error > 0 )
            {
                outputWriter.println("HTTP/1.0 " + myWeblet.error + " "
                            + myWeblet.errDesc );
                // Print out the cookies as part of the headers
                printCookieHeaders( myWeblet.responseCookies, outputWriter );
                outputWriter.println(); // End headers with empty line
            } else
            {
 
                // the output is in the ByteArray "byteArray"

                // Send the default headers
                outputWriter.println("HTTP/1.0 200 OK" );
                if ( myWeblet.contentType != null )
                    outputWriter.println("Content-Type: " + myWeblet.contentType );
                else
                    outputWriter.println("Content-Type: text/html" );

                // Print out the cookies as part of the headers
                printCookieHeaders( myWeblet.responseCookies, outputWriter );

                outputWriter.println(); // End headers with empty line

                // Send content

                out.flush();  // Make sure all output is in 'byteArray'

                outputWriter.println( byteArray.toString());
            }
            
                  
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try {
            // Make sure the connection is closed in any case.
            outputWriter.close(); 
        } catch (Exception ex)
        {
        }
    }
}    