package msp;

import java.io.*;
import java.util.*;
import webserver.*;

public class CurrentTime extends MyWeblet
{

  public void doRequest( String resource, String queryString,
    HashMap<String,String> parameters,
    PrintWriter out )
  {

    java.util.Date date = new java.util.Date();

    out.println( "" );
    out.println( "<HTML>" );
    out.println( "<BODY>" );
    out.print( "<P> hesky billoba The time is now " );
    out.print(  date );
    out.println( "" );
    out.println( "</BODY>" );
    out.print( "</HTML>" );
  }
}
