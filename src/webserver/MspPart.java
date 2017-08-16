package webserver;

import java.io.*;
import java.util.*;

//
//  This class is used to break up a MSP file into "MSP Parts"
//

public class MspPart
{
    // Enumerator describing the parts.

    public enum MspPartType {
       Text,           // Text, no newline
       TextLine,       // Text with newline
       Code,           // Java code, embedded within <% and %>
       Expression,     // Java expression, embedded within <%= and %>
       Declaration     // Java declaration, embedded within <%! and %>
    };

    public MspPartType partType;  // The type of this MSP part
    public String partText;       // The text of this MSP part

    // Constructor is only used internally by parseMsp
    MspPart( MspPartType type, String text )
    {
        partType = type;
        partText = text;
    }

    // The MSP parser, generates a list of the parts.

    public static List<MspPart> parseMsp(File file) throws IOException
    {
        byte[] b = new byte[ (int) file.length() ];
        FileInputStream input = new FileInputStream( file );
        int ofs = 0;
        while ( ofs < b.length )
            ofs += input.read( b, ofs, b.length - ofs );
        input.close();
        String str = new String( b );
        int index;
        ArrayList<MspPart> result = new ArrayList<MspPart>();
        while (( index = str.indexOf( "<%" )) >= 0 )
        {
            if ( index > 0 )
            {
              addString( result, str.substring( 0, index ));
            }
            str = str.substring( index+2 );
            index = str.indexOf( "%>" );
            String sub = str.substring( 0, index );
            if ( sub.startsWith( "=" ))
                result.add( new MspPart( MspPartType.Expression, sub.substring(1)));
            else if ( sub.startsWith( "!" ))
                result.add( new MspPart( MspPartType.Declaration, sub.substring(1)));
            else
                result.add( new MspPart( MspPartType.Code, sub ));
            str = str.substring( index+2 );
        }
        addString( result, str );
        return result;
    }

    // Method to escape double and single quotes and the backslash character.

    static String escapeString( String str )
    {
        return str.replace( "\\", "\\\\" ).replace( "\"", "\\\"" ).replace("'", "\\'" );
    }

    // Breaks up a string into TextLine and Text parts, and adds to the 'list' being built

    static void addString( List<MspPart> list, String str )
    {
        int index;
        while (( index = str.indexOf( '\n' )) >= 0 )
        {
            if ( index > 0 && str.charAt( index-1 ) == '\r' )
                list.add( new MspPart( MspPartType.TextLine, escapeString( str.substring( 0, index-1 ))));
            else
                list.add( new MspPart( MspPartType.TextLine, escapeString( str.substring( 0, index ))));
            str = str.substring( index+1 );
        }
        if ( ! str.equals( "" ))
            list.add( new MspPart( MspPartType.Text, escapeString( str )));
    }
}
