package com.util.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.page.functions.TestBase;

public class DriverScript 
{
	
	
	@SuppressWarnings("deprecation")
	public void runTestNGTest(Map<String,String> testngParams) throws Exception 
	{ 
			List<String> m = new ArrayList<String>();
			
			TestNG afTestNG = new TestNG();
			XmlSuite afSuite = new XmlSuite();
			afSuite.setName("AF Portal Automation Execution Report");
			
			String listener = "com.util.functions.TestListener";
			afSuite.addListener(listener );
			afSuite.addListener("org.uncommons.reportng.HTMLReporter" );
			afSuite.addListener("org.uncommons.reportng.JUnitXMLReporter");
			
			XmlTest afTest = new XmlTest(afSuite);
			
			afTest.setName("SPRINT 1");
			
			afSuite.setParallel("classes");
			afSuite.setThreadCount(6);
						
			List<XmlClass> myClasses = new ArrayList<XmlClass> ();

			ExcelLibrary excelLibrary = new ExcelLibrary();
			int sheetCount = excelLibrary.getSheetCount()-2;
			for (int j = 1; j <= sheetCount; j++) 
			{
				 TestBase.sprintNo = j;
				 
				 excelLibrary.setSheet("Sprint "+TestBase.sprintNo+" TestCase");
				 
				int totalTestCase = excelLibrary.getExcelRowCount();	
				for (int i = 0; i <= totalTestCase; i++) 
				{					
					
					if(excelLibrary.getExcelData(i, 2).equalsIgnoreCase("Yes"))
					{
						
						if(m.contains(new XmlClass("com.sprint"+TestBase.sprintNo+"Testcases.java."+
								"User_Story_"+excelLibrary.getExcelData(i, 0).replace("-", "_").
								substring(0, excelLibrary.getExcelData(i, 0).length()-5)).toString().toString())==false)
						{
							myClasses.add(new XmlClass("com.sprint"+TestBase.sprintNo+"TestCases.java."+
									"User_Story_"+excelLibrary.getExcelData(i, 0).replace("-", "_").
											substring(0, excelLibrary.getExcelData(i, 0).length()-5)));
							
							m.add(new XmlClass("com.sprint"+TestBase.sprintNo+"TestCases.java."+
									"User_Story_"+excelLibrary.getExcelData(i, 0).replace("-", "_").
											substring(0, excelLibrary.getExcelData(i, 0).length()-5)).toString());
						}
					}
				}
				
			}			

			afTest.setXmlClasses(myClasses);
			List<XmlTest> myTests = new ArrayList<XmlTest>();
			myTests.add(afTest);
			afSuite.setTests(myTests);

			List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
			
			/*Object listener = new Object[]{"org.uncommons.reportng.HTMLReporter","org.uncommons.reportng.JUnitXMLReporter"};
			afTestNG.addListener(listener );*/
			
			mySuites.add(afSuite);
			
			
			afTestNG.setXmlSuites(mySuites);
			afTestNG.run();
		
	}		 

	public static void main(String args[]) throws Exception {

		System.out.println("Running the Driver Script");
		
		DriverScript script = new DriverScript();
		Map<String,String> testngParams = new HashMap<String,String> ();
		script.runTestNGTest(testngParams);

	}
}
