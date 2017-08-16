package webserver;

import java.io.*;

//
//  This class is used to break up a MSP file into "MSP Parts"
//

public class MspClassLoader extends ClassLoader
{
    @SuppressWarnings("rawtypes")
	Class loadedClass;

    // Constructor loads the class contained in the 'classFile'

    public MspClassLoader( File classFile ) throws IOException
    {
        byte[] classBytes = new byte[ (int) classFile.length() ];

        FileInputStream input = new FileInputStream( classFile );

        int ofs = 0;
        while ( ofs < classBytes.length )
            ofs += input.read( classBytes, ofs, classBytes.length - ofs );

        input.close();

        // Now load the bytes as a Java class

        loadedClass = defineClass( null, classBytes, 0, classBytes.length );
        resolveClass( loadedClass );

    }

    @SuppressWarnings("rawtypes")
	public Class getLoadedClass() { return loadedClass; }

}
