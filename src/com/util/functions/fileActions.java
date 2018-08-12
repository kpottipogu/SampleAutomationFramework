package com.util.functions;

import java.io.File;
import java.util.Arrays;

import com.page.functions.TestBase;

public class fileActions 
{
	private static String path;
	
	public fileActions(String path)
	{
		this.path = path;
	}
	
	public String[] getListOfFiles()
	{
		
		System.out.println("Searching in path :- "+path);
		 File directory = new File(path);
	        //get all the files from a directory
	        File[] fList = directory.listFiles();
	        
	        String[] fileNames = new String[fList.length];
	        
	        for (int i = 0; i < fileNames.length; i++) 
	        {
	        	fileNames[i] = fList[i].getName().replace("(1)", "").replace("(2)", "");
	        	//System.out.println("fileNames["+i+"] :- "+fileNames[i]);
			}
	        		
	        
	        
	        return fileNames;
	}
	
	public void DeleteFiles()
	{
		
		 File directory = new File(path);
	        //get all the files from a directory
	        File[] fList = directory.listFiles();
	        
	        String[] fileNames = new String[fList.length];
	        
	        for (int i = 0; i < fileNames.length; i++) 
	        {
	        	fList[i].deleteOnExit();
			}
	        
	    System.out.println(path +" is cleared");    
	}
	
	public boolean compareList(String[] List1, String[] List2)
	{
		Arrays.sort(List1);
		Arrays.sort(List2);
		if (Arrays.equals(List1, List2)) 
		{
			System.out.println("Pass");
			return true;
		}else {
			System.out.println("Fail");
			return false;
		}
		
	}
	
	public static void main(String[] args) 
	{
		
		String[] a1 = new String[]{"Excel.xls","JPGImage.jpg","PDF.pdf","RichTextFormat.rtf","TextFile.txt","ExcelOffice.xlsx","WordDocument.doc","WordOffice.docx"};
		
		
		fileActions  f = new fileActions(TestBase.downloadPath);
				
		f.compareList(a1, f.getListOfFiles());
		f.DeleteFiles();
		
	}
}
