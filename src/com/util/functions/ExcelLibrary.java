package com.util.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.page.functions.TestBase;

import java.util.*;

/**
 * 
 * @Author Naveen.Kanak
 * @Date 29 Apr 2016
 * @Time 06:48:17
 *TODO
 */
public class ExcelLibrary 
{
	public XSSFWorkbook workbook;
	public XSSFSheet sheetName;
	public int totalRowsInSheet;
	Cell cell = null;

	public ExcelLibrary() throws IOException, Exception
	{
		workbook = new XSSFWorkbook(new FileInputStream(new File(TestBase.TEST_DATA)));
	}

	public ExcelLibrary(String path) throws IOException, Exception
	{
		workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
	}

	public void setSheet(String sheetName)
	{
		this.sheetName = workbook.getSheet(sheetName);
	}


	public String getExcelData(int row, int cell)
	{
		
		/*System.out.println(row +" "+cell);
		System.out.println(sheetName.getSheetName());*/
		return sheetName.getRow(row).getCell(cell).toString();

	}

	public int getExcelRowCount()
	{	

		System.out.println(sheetName.getSheetName()+"  --> "+sheetName.getLastRowNum());

		return sheetName.getLastRowNum();

	}


	public boolean searchForText(String TestCase)
	{


		boolean flag = false;

		//System.out.println("Chkpt : Checking run staus for test case "+TestCase);

		TextSearch:
			for (int j = 1; j <= getSheetCount()-2; j++) 
			{
				setSheet("Sprint "+j+" TestCase");

				/*//System.out.println("Sprint "+j+" TestCase");*/

				for (int i = 1; i <= getExcelRowCount(); i++) 
				{
					try {
						/*System.out.println(TestCase);
						System.out.println(getExcelData(i, 0).replace("-", "_"));
*/
						
						
						if (TestCase.contains(getExcelData(i, 0).replace("-", "_")) && getExcelData(i, 2).equalsIgnoreCase("Yes"))
						{
							flag = true;
							break TextSearch;

						}
					} catch (Exception e) {
						// TODO: handle exception
						
						e.printStackTrace();
					}

				}

			}



		return flag;
	}
	public int getSheetCount()
	{		
		return workbook.getNumberOfSheets();
	}

	public Row findCell(String text) 
	{    
		for(Row row : sheetName)
		{
			////System.out.println(row.getCell(0));
			if (row.getCell(0).toString().equalsIgnoreCase(text)) 
			{	
				return row;
			}

		}

		return null;
	}

	public int getTableEndRowNum(int tableStartRow)
	{


		for (int rowNum = tableStartRow; rowNum < totalRowsInSheet; rowNum++) 
		{
			try {
				
				if(sheetName.getRow(rowNum).getCell(0).toString().equalsIgnoreCase(""))
				{
					return rowNum;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				
				return rowNum;
			}
		}

		return 0;
	}

	public Map getTestSpecificData(int tableStartRow, int tableEndRow)
	{
		int i = 1;

		Map completeData = new HashMap<String, Map>();

		int iterationNo = 1;

		for (int rowNum = tableStartRow; rowNum < tableEndRow; rowNum++) 
		{
			Map singleIterationData = new HashMap();
			int columnCount = sheetName.getRow(rowNum).getLastCellNum();



			if (getExcelData(rowNum, 1).equalsIgnoreCase("Y")) 
			{

				singleIterationData.put("iteration", iterationNo++);

				for (int columnNum = 0; columnNum < columnCount; columnNum++) 
				{		

					//System.out.println("Adding " +getExcelData(tableStartRow-1, columnNum).trim() +"="+ getExcelData(rowNum, columnNum).trim());
					singleIterationData.put(getExcelData(tableStartRow-1, columnNum).trim(), getExcelData(rowNum, columnNum).trim());	

				}

				//System.out.println(singleIterationData);

				for (Object obj : DataProviderScript.commonData.keySet()) 
				{
					//System.out.println("Checking for "+obj);


					//System.out.println(singleIterationData.containsKey("Browser"));

					if (singleIterationData.containsKey(obj)==false) 
					{
						//System.out.println("****************** Added "+obj);
						singleIterationData.put(obj, DataProviderScript.commonData.get(obj));
					}
				}

				completeData.put((i++), singleIterationData);
			}

			/********************/

		}		

		return completeData;
	}

	public void getCommonData()
	{
		DataProviderScript.commonData = new HashMap();

		for (int rownum = 1; rownum <= totalRowsInSheet; rownum++) 
		{

			try 
			{
				DataProviderScript.commonData.put(getExcelData(rownum, 0).trim(), getExcelData(rownum, 1).trim());

			} catch (NullPointerException e) {

			}

			//int m = i;

			////System.out.println(rownum);

			/*if (getCell(rownum, 0) == null || getCell(rownum, 1) == null) 
			{
				//System.out.println("**************************************");
			}else 
			{

			}*/



		}

		/*//System.out.println(DataProviderScript.commonData);*/

	}

	public Cell getCell(int rownum, int cellnum)
	{
		//System.out.println(sheetName.getSheetName());
		return sheetName.getRow(rownum).getCell(cellnum);
	}

	public void writeStatus(String testName, int iteration, String status) throws Exception
	{
		ExcelLibrary e = new ExcelLibrary(TestBase.resultFolderPath+"\\Results.xlsx");

		Sheet s = e.workbook.getSheet("Data");
		int rowNum = e.searchText(testName, 0, "Data");

		System.out.println("rowNum "+rowNum);

		int tableStart = rowNum+1;
		int tableEnd = tableStart;
		System.out.println(s.getRow(tableEnd).getCell(0).toString());
		while (s.getRow(tableEnd).getCell(0).toString().contains("AF")==false) 
		{
			System.out.println(s.getRow(tableEnd).getCell(0).toString());
			tableEnd++;
		}

		tableEnd = tableEnd -2;
		System.out.println("tableStart "+tableStart);
		System.out.println("tableEnd "+tableEnd);

		System.out.println();

		int lastColumn = s.getRow(tableStart).getLastCellNum();

		int dataColumn = writeColumnName(s, tableStart, lastColumn);		

		cell = s.getRow(tableStart+iteration).getCell(dataColumn);

		if (cell==null) 
		{
			s.getRow(tableStart+iteration).createCell(dataColumn);
		}

		CellStyle cs =  e.workbook.createCellStyle();

		if (status.equalsIgnoreCase("Pass")) 
		{
			s.getRow(tableStart+iteration).getCell(dataColumn).setCellValue("Pass");
			cs.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			s.getRow(tableStart+iteration).getCell(dataColumn).setCellStyle(cs);
		}else 
		{
			s.getRow(tableStart+iteration).getCell(dataColumn).setCellValue("Fail");
			cs.setFillBackgroundColor(IndexedColors.RED.getIndex());
			s.getRow(tableStart+iteration).getCell(dataColumn).setCellStyle(cs);
		}

		FileOutputStream fos = new FileOutputStream(TestBase.resultFolderPath+"\\Results.xlsx");

		e.workbook.write(fos);
		fos.close();









	}

	public int writeColumnName(Sheet s, int tableStart, int lastColumn)
	{
		if (s.getRow(tableStart).getCell(lastColumn-1).toString().equalsIgnoreCase("Result")==false) 
		{
			cell = s.getRow(tableStart).getCell(lastColumn);
			if (cell==null) 
			{
				s.getRow(tableStart).createCell(lastColumn);
			}

			s.getRow(tableStart).getCell(lastColumn).setCellValue("Result");

			return lastColumn;
		}else {
			return lastColumn-1;
		}

	}

	public int searchText(String text, int column, String sheet)
	{
		int rowNumber=0;

		Sheet s = workbook.getSheet(sheet);
		int rowCount = s.getLastRowNum();

		loop1:
			for (int i = 0; i < rowCount; i++) 
			{
				System.out.println(s.getRow(i).getCell(column).toString()+"  --  > "+text.substring(0, text.lastIndexOf("_")));

				if (s.getRow(i).getCell(column).toString().equalsIgnoreCase(text.substring(0, text.lastIndexOf("_")))) 
				{
					rowNumber = i;
					break loop1;
				}
			}

		return rowNumber;
	}
	
}
