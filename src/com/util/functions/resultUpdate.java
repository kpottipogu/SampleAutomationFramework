package com.util.functions;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;

import com.page.functions.TestBase;

public class resultUpdate 
{
	
	public void update() throws Exception 
	{
		resultUpdate resultStatus = new resultUpdate();
		resultStatus.updatePassOrFail();
		resultStatus.updateSkipped();
	}
	
	public void updatePassOrFail() throws Exception
	{
		ExcelLibrary excelLibraryData = new ExcelLibrary(TestBase.resultFilePath);
		excelLibraryData.setSheet("Data");
		
		
		ExcelLibrary excelLibrary = new ExcelLibrary(TestBase.resultFilePath);
		excelLibrary.setSheet("Sprint 1 TestCase");
		
		
		int rowCount = excelLibrary.getExcelRowCount();
		
		for (int row = 1; row < rowCount; row++) 
		{
			//System.out.println(row +" --> "+ excelLibrary.getExcelData(row, 2));
			
			if (excelLibrary.getExcelData(row, 2).equalsIgnoreCase("Yes")) 
			{
				//System.out.println(excelLibrary.getCell(row, 0));
				
				int startRow = search(excelLibrary.getExcelData(row, 0).toString().trim());
				
				//System.out.println("Data row number "+startRow);
				
				if (excelLibraryData.getExcelData(startRow+1, excelLibraryData.sheetName.getRow(startRow+2).getLastCellNum()-1).equalsIgnoreCase("Result")) 
				{
					//System.out.println("Data "+excelLibraryData.getExcelData(startRow, 0));
					boolean testStatus = true;
					boolean exist = false;
					int i=2;
					text:
					while (excelLibraryData.getExcelData(startRow+i, excelLibraryData.sheetName.getRow(startRow+i).getLastCellNum()-1).trim().length()>0) 
					{
						//System.out.println("Data "+(i-1)+" "+excelLibraryData.getExcelData(startRow+i, excelLibraryData.sheetName.getRow(startRow+i).getLastCellNum()-1));
						
						if (excelLibraryData.getExcelData(startRow+i, excelLibraryData.sheetName.getRow(startRow+i).getLastCellNum()-1).equalsIgnoreCase("Fail")) 
						{
							testStatus = false;
							break text; 
						}
						
						i++;
					}
					
					System.out.println(excelLibrary.getExcelData(row, 0)+" -->  "+testStatus);
					
					/*System.out.println("Data 2 "+excelLibraryData.getExcelData(startRow+3, excelLibraryData.sheetName.getRow(startRow+3).getLastCellNum()-1));
					System.out.println("Data 3 "+excelLibraryData.getExcelData(startRow+4, excelLibraryData.sheetName.getRow(startRow+4).getLastCellNum()-1));*/
					
					ExcelLibrary e = new ExcelLibrary(TestBase.resultFolderPath+"\\Results.xlsx");

					Sheet s = e.workbook.getSheet("Sprint 1 TestCase");
					

					Cell cell = s.getRow(row).getCell(3);

					if (cell==null) 
					{
						s.getRow(row).createCell(3);
					}

					CellStyle cs =  e.workbook.createCellStyle();

					if (testStatus==true) 
					{	
						
						s.getRow(row).getCell(3).setCellValue("Pass");
						
						
					}else 
					{
						s.getRow(row).getCell(3).setCellValue("Fail");
						
					}

					FileOutputStream fos = new FileOutputStream(TestBase.resultFolderPath+"\\Results.xlsx");

					e.workbook.write(fos);
					fos.close();

				}
				
			}
			
		}
		
	}
	
	public void updateSkipped() throws IOException, Exception
	{
		ExcelLibrary excelLibrary = new ExcelLibrary(TestBase.resultFilePath);
		excelLibrary.setSheet("Sprint 1 TestCase");
		
		Sheet s = excelLibrary.sheetName;
		
		
		int rowCount = excelLibrary.getExcelRowCount();
		
		for (int row = 1; row <= rowCount; row++) 
		{
			if (excelLibrary.getExcelData(row, 2).equalsIgnoreCase("Yes") &&  excelLibrary.getExcelData(row, 3).trim().length()==0) 
			{
				s.getRow(row).getCell(3).setCellValue("Skipped");
			}
		}
		FileOutputStream fos = new FileOutputStream(TestBase.resultFolderPath+"\\Results.xlsx");

		excelLibrary.workbook.write(fos);
		fos.close();
	}	
	
	public int search(String text) throws Exception
	{
		//System.out.println("Search Text "+text);
		
		ExcelLibrary excelLibraryData = new ExcelLibrary(TestBase.resultFilePath);
		excelLibraryData.setSheet("Data");
		int rowCount = excelLibraryData.getExcelRowCount();
		
		for (int row = 0; row < rowCount-1; row++) 
		{
			//System.out.println("Loop 2 "+row+" "+excelLibraryData.getExcelData(row, 0));
			
			if (excelLibraryData.getExcelData(row, 0).equalsIgnoreCase(text.replace("-", "_"))) 
			{
				return row;
			}
		}
		
		return 0;
	}
	
	
	public static void main(String[] args) throws Exception {
		resultUpdate r = new resultUpdate();
		r.update();
	}
}
