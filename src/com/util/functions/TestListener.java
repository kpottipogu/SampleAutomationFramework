package com.util.functions;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.page.functions.TestBase;

public class TestListener implements ITestListener 
{
	WebDriver driver=null;
	String filePath = "D:\\SCREENSHOTS";
	
	public void onTestFailure(ITestResult result) 
	{
		System.out.println("***** Error "+result.getName()+" test has failed *****");
		String methodName=result.getName().toString().trim();
		//takeScreenShot(methodName);
		
		 Object[] m = result.getAttributeNames().toArray();
		 
		 System.out.println(m.length);
		 
		 
		 ExcelLibrary excel = null;
			try {
				excel = new ExcelLibrary();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Object[] n = result.getParameters();
				 
			Map d = (Map) n[1];
			
			
			
			
			String testName = result.getMethod().getMethodName();
			int iteration = Integer.parseInt(d.get("iteration").toString());
			
			
			
			//excel.writePass(mn.substring(0, mn.lastIndexOf("_")).toString(), ((Map)n[1]).get("iteration"), "Pass");
			
			try {
				excel.writeStatus(testName, iteration, "Fail");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 
		 
		 
	}
	
	public void onFinish(ITestContext context) 
	{
		
		System.out.println("End:");
		System.out.println("**************************************************************");
	}

	public void onTestStart(ITestResult result) 
	{  
		System.out.println("**************************************************************");
		System.out.println("Started Test Case : "+result.getMethod().getMethodName().toString());
		
	}

	public void onTestSuccess(ITestResult result) { 
		
		ExcelLibrary excel = null;
		try {
			excel = new ExcelLibrary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] n = result.getParameters();
			 
		Map d = (Map) n[1];
		
		
		
		
		String testName = result.getMethod().getMethodName();
		int iteration = Integer.parseInt(d.get("iteration").toString());
		
		
		
		//excel.writePass(mn.substring(0, mn.lastIndexOf("_")).toString(), ((Map)n[1]).get("iteration"), "Pass");
		
		try {
			excel.writeStatus(testName, iteration, "Pass");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void onTestSkipped(ITestResult result) {   }

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {   }

	public void onStart(ITestContext context) {   }
}  