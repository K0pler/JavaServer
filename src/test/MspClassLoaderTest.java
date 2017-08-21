package test;

import java.io.*;
import webserver.MspClassLoader;

public class MspClassLoaderTest
{
  public static void main(String[] args) throws Exception
  {
    File classFile = new File("test/TestClass.class");

    // Load the class file using the MspClassLoader

    MspClassLoader loader = new MspClassLoader( classFile );

    @SuppressWarnings("rawtypes")
	Class cls = loader.getLoadedClass();

    System.out.println("Class name = " + cls.getName());

    // Now instantiate an object of the loaded class, which also does the test output.

    cls.newInstance();
  }
}
