package com.util.functions;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;

/**
 * 
 * @Author Naveen.Kanak
 * @Date 29 Apr 2016
 * @Time 08:16:27
 *TODO
 */
public class DataProviderScript 
{
	
	public static Map commonData;
	

	/**
	 * 
	 * @Author Naveen.Kanak
	 * @Date 29 Apr 2016
	 * @Time 08:16:33
	 * @param testCaseName
	 * @return 
	 * @throws IOException
	 * @throws Exception
	 */
	public Object[][] getData(String testCaseName) throws IOException, Exception
	{
		
		ExcelLibrary excelLibrary = new ExcelLibrary();

		excelLibrary.setSheet("Data");
		excelLibrary.totalRowsInSheet = excelLibrary.getExcelRowCount();
		Row row = excelLibrary.findCell(testCaseName);

		int tableStartRow = row.getRowNum()+2;
		int tableEndRow = excelLibrary.getTableEndRowNum(tableStartRow);
		
		Map testSpecificData = excelLibrary.getTestSpecificData(tableStartRow, tableEndRow);
			
		/*System.out.println(testSpecificData.get(1));
		System.out.println(testSpecificData.get(2));*/
		
		
		Object[][] data = new Object[testSpecificData.size()][1];
		
		for (int i = 0; i < data.length; i++) 
		{
			data[i][0] = testSpecificData.get(i+1);
			
			System.out.println(data[i][0]);
			
		}
		
		return data;
			
	}

	
	public void setCommonData() throws Exception, Exception
	{
		ExcelLibrary excelLibrary = new ExcelLibrary();

		excelLibrary.setSheet("Common Data");
		excelLibrary.totalRowsInSheet = excelLibrary.getExcelRowCount();
		excelLibrary.getCommonData();
	}

	public static void main(String[] args) throws IOException, Exception 
	{
		DataProviderScript s = new DataProviderScript();
		s.setCommonData();
		System.out.println(s.getData("AF499_TC01"));
	}
}
