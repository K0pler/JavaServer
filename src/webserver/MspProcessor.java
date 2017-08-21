package webserver;

import java.io.*;
import java.util.*;

public class MspProcessor
{
    private static class MspInfo
    {
        @SuppressWarnings("rawtypes")
		public Class mspClass;
             // The loaded class
        public long lastModified;
            // The timestamp of the MSP file
    }

    Hashtable<File,MspInfo> mspTable = new Hashtable<File,MspInfo>();

    @SuppressWarnings("rawtypes")
	public Class getMspClass( File mspFile ) throws IOException
    {
        // Check if we already have an mspInfo

        MspInfo info = mspTable.get( mspFile );
        if ( info != null && info.lastModified == mspFile.lastModified())
            return info.mspClass;

        // Don't have the class in memory, or it's out of date, find or build it

        Class cls = loadMspClass( mspFile );

        info = new MspInfo();
        info.mspClass = cls;
        info.lastModified = mspFile.lastModified();

        mspTable.put( mspFile, info );  // Save for future

        return cls;
    }

    @SuppressWarnings("rawtypes")
	Class loadMspClass( File mspFile ) throws IOException
    {
        // Get the path of the .class file
        String classFileName = mspFile.getName().replace( ".ksp", ".class" );
        File classFile = new File(
              new File( "C:\\Users\\Kopler\\git\\JavaServer\\src\\msp\\" ),
              classFileName );

        // If classFile doesn't exist, or has a timestamp older
        // than mspFile, build a new ClassFile

        if (( ! classFile.exists()) ||
            classFile.lastModified() < mspFile.lastModified())
        {
            System.out.println("Building MSP " + mspFile.getAbsolutePath());
            buildMspClass( mspFile );
        }

        // At this time, the classfile either already exists
        // or has been built.  Load it.

        MspClassLoader classLoader = new MspClassLoader( classFile );
        return classLoader.getLoadedClass();
    }

    void buildMspClass( File mspFile ) throws IOException
    {
        // Build the java file
        File javaFile = buildJavaFileFromMsp( mspFile );

        // Compile it.

        compileJavaFile( javaFile );
    }

    @SuppressWarnings("incomplete-switch")
	File buildJavaFileFromMsp( File mspFile ) throws IOException
    {
        // First of all, parse the MSP file
        List<MspPart> mspParts = 
              MspPart.parseMsp( mspFile );

        // Now create a Java file

        File javaFile = new File("C:\\Users\\Kopler\\git\\JavaServer\\src\\msp\\" + mspFile.getName().replace( ".ksp", ".java" ));
        String className = javaFile.getName().replace( ".java", "" );
        System.out.println(className);

        PrintWriter javaWriter = new PrintWriter( javaFile );

        javaWriter.println( "package msp;" );
        javaWriter.println();
        javaWriter.println( "import java.io.*;" );
        javaWriter.println( "import java.util.*;" );
        javaWriter.println( "import webserver.*;" );
        javaWriter.println();
        javaWriter.println( "public class " + className + " extends MyWeblet" );
        javaWriter.println( "{" );

        // Output all the declarations before starting 'doRequest'
        for ( MspPart part: mspParts )
        {
          switch ( part.partType )
          {
            case Declaration:
              javaWriter.println( part.partText );
              break;
          }
        }

        javaWriter.println();
        javaWriter.println( "  public void doRequest( String resource, String queryString," );
        javaWriter.println( "    HashMap<String,String> parameters," );
        javaWriter.println( "    PrintWriter out )" );
        javaWriter.println( "  {" );

        // CODEGEN: This is where we output the code for doRequest.
        for ( MspPart part: mspParts )
        {
          switch ( part.partType )
          {
            case Text:
              javaWriter.println( "    out.print( \"" + part.partText + "\" );" );
    	  break;
            case TextLine:
              javaWriter.println( "    out.println( \"" + part.partText + "\" );" );
              break;
          case Declaration:
              // NOt handled here
              break;
          case Expression:
              javaWriter.println( "    out.print( " + part.partText + ");" );
              // No double quotes this time
	     break;
           case Code:
             javaWriter.println( part.partText );
             // Just print it out
	    break;
          }
        }

        javaWriter.println( "  }" );
        javaWriter.println( "}" );

        javaWriter.close();

        return javaFile;

    }

    void compileJavaFile( File javaFile ) throws IOException
    {
        Process p = Runtime.getRuntime().exec( new String[] {
          "F:\\Java\\jdk1.8.0_121\\bin\\javac.exe",
          "-classpath",
          "C:\\Users\\Kopler\\git\\JavaServer\\src", // E.g. "C:\\MVOWS\\classes"
          "-d",
          "C:\\Users\\Kopler\\git\\JavaServer\\src",
          javaFile.getAbsolutePath()
         }
        );
        InputStream in = p.getErrorStream();
        int c;
        while (( c = in.read()) >= 0 )
            System.out.print( (char) c );
        try {
            p.waitFor();  // Wait until the compile is done
        } catch (InterruptedException ex) {}
    }
}