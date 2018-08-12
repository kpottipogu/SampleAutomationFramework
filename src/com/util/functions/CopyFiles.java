package com.util.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CopyFiles 
{
	
	public static void copy(String source, String dest) throws Exception 
	{
		
		File testData =  new File(source);
		File result =  new File(dest);
		Files.copy(testData.toPath(), result.toPath());
				
	}
		
}
