package com.sprint1Testcases.java;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.page.functions.TestBase;

public class User_Story_AAF499 extends TestBase{
	
	WebDriver driver=null;
	
	@Test(dataProvider="getData")
	public void AF499_TC01_createManage(Method testname,Object data) throws Exception{
		
		runStatus(testname);
		Map testData=(Map)data;
		//driver=getBrowser(testData.get("Browser").toString());
		System.out.println(getBrowser(testData.get("Browser").toString()));
		System.out.println(testData.get("Company").toString());
		
		
		
		
	}

	/*@Test(dataProvider="getData")
	public void AF499_TC01_Edit(Method testname,Object data) throws Exception{
		
		runStatus(testname);
		Map testData=(Map)data;
		driver=getBrowser(testData.get("Browser").toString());
		System.out.println(getBrowser(testData.get("Browser").toString()));
		System.out.println(testData.get("Company").toString());
		
		
		
		
	}
*/
	
	

}
