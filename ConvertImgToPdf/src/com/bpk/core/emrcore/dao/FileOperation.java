package com.bpk.core.emrcore.dao;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileOperation
{

  /** Get File name */
  public static String getFileName(String label)
  {
    String fileName = null;
    System.out.println();
    System.out.println(label);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); 
    do{
      try
      {
        System.out.print("File > ");
        fileName = bufferedReader.readLine();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
    }while(fileName==null || fileName.trim().equals(""));
    return fileName;
  }
  
  /** Write text data to file */
  public static boolean writeFile(String filename, String data)
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(filename);
      Writer out = new OutputStreamWriter(fos);
      out.write(data);
      out.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
    return true;
  }
  
    
}
