package msp;

import java.io.*;
import java.util.*;
import webserver.*;

public class cube extends MyWeblet
{

    // A method to return cube of a number

    int cube( int n )
    {
        return n * n * n;
    }



  public void doRequest( String resource, String queryString,
    HashMap<String,String> parameters,
    PrintWriter out )
  {
    out.println( "<HTML>" );
    out.println( "<BODY>" );
    out.println( "" );

if ( parameters.get( "number" ) != null )
{
    try {
        int n = Integer.parseInt(
                   parameters.get( "number" ));

        out.print("The cube of " + n + " = " +
                    cube( n ));
    } catch (NumberFormatException nex)
    {
        out.print("Please enter a valid number." +
           " You entered: " +
           parameters.get( "number" ));
    }
    out.println("<HR>");
}

    out.println( "" );
    out.println( "<FORM METHOD=GET ACTION=\"cube.ksp\">" );
    out.println( "Enter number: <INPUT TYPE=TEXT SIZE=4 NAME=\"number\">" );
    out.println( "<P>" );
    out.println( "<INPUT TYPE=SUBMIT>" );
    out.println( "</FORM>" );
    out.println( "</BODY>" );
    out.print( "</HTML>" );
  }
}
