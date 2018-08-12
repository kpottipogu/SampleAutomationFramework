package com.page.functions;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;


import com.google.common.base.Function;
import com.util.functions.CopyFiles;
import com.util.functions.DataProviderScript;
import com.util.functions.ExcelLibrary;
import com.util.functions.fileActions;
import com.util.functions.resultUpdate;


public class TestBase 
{
	private WebDriver firefoxDriver;
	private WebDriver internetExplorerDriver;
	private WebDriver chromeDriver;

	public static Logger log;

	public static int sprintNo; 

	public ExcelLibrary excelLibrary = null;

	public static final String TEST_URL = "http://192.168.253.49:8080/asset-finance/home";
			//"http://aldermore-test.backbasecloud.com/asset-finance/home";//; 
	public static final String TEST_DATA = System.getProperty("user.dir")+"\\src\\com\\data\\java\\TestData.xlsx";
	
	public static String errorScreenShotPath = System.getProperty("user.dir")+"//ErrorScreenShot"; 
	public static String resultFolderPath = System.getProperty("user.dir")+"//Results"; 
	public static String resultFilePath = resultFolderPath+"\\Results.xlsx";
	public static String downloadPath = System.getProperty("user.dir")+"//Download";; 
	
	
	@BeforeSuite
	public void setUp() throws Exception
	{		
		File errorScreenShot = new File(errorScreenShotPath);
		File result = new File(resultFolderPath);

		if (errorScreenShot.exists())
		{
			new fileActions(errorScreenShotPath).DeleteFiles();
			
			errorScreenShot.delete();
			System.out.println("Deleted the existing error screenshot folder");
		}

		errorScreenShot.mkdir();

		if (result.exists())
		{
			result.delete();
			
		}

		result.mkdir();
		
		if(new File(resultFilePath).exists())
		{
			new File(resultFilePath).delete();
		}

		
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		Date d = new Date();
		
		String[] n = s.format(d).split(" ");
				
		resultFilePath = resultFolderPath+"\\"+"Results_Date"+n[0].replace("-", "_")+"-"+"Time"+n[1].replace(":", "_")+".xlsx";
		System.out.println(resultFilePath);
		
		CopyFiles.copy(TEST_DATA, resultFilePath);
		
		

	}


	/*	@BeforeMethod
	public void openBrowser(Method m) throws Exception
	{
		log = Logger.getLogger(m.getName());

		System.out.println("openBrowser");
		firefoxDriver = new FirefoxDriver();
		firefoxDriver.get("http://aldermore-test.backbasecloud.com/asset-finance/submit-proposal");
		log.config("Invoking the test url");
		firefoxDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		firefoxDriver.manage().window().maximize();

		try {

			firefoxDriver.findElement(By.name("lognsfc")).sendKeys("Kotaiah.pottipogu@Aldermore.co.uk");
			firefoxDriver.findElement(By.name("lsubmit")).click();
			firefoxDriver.findElement(By.name("passsfc")).sendKeys("07ZxEzuY");
			firefoxDriver.findElement(By.name("bsubmit")).click();

		} catch (Exception e) {
			// TODO: handle exception
		}
		Thread.sleep(1000L);

	}*/

	public void runStatus(Method testName) 
	{		
		try {
			excelLibrary = new ExcelLibrary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("********searchForText **** "+testName.getName().toString()+" ************************** : "+excelLibrary);
		if(excelLibrary.searchForText(testName.getName().toString()) == false)
		{		
			
			throw new SkipException(" ******************* The Test Case "+testName.getName()+" is skipped *********************");
		}
	}

	public void logout(WebDriver driver)
	{
		if (driver!=null) {
			System.out.println("CloseBrowser");
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	/**
	 * @throws Exception ************/

	@DataProvider
	public Object[][] getData(Method m) throws Exception 
	{

		
		DataProviderScript s = new DataProviderScript();
		s.setCommonData();

		String n = m.getName().toString();
		
		System.out.println("Retriving data for "+n);
		
		return s.getData(n.substring(0, n.indexOf("_", n.indexOf("_")+1)));

		/*return new Object[][]
				{
			{"FF"}
			,{"FF"}
			,{"FF"}

			};*/

	}

	public void login(WebDriver driver) throws Exception
	{		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		try {

			/*driver.findElement(By.name("lognsfc")).sendKeys("Kotaiah.pottipogu@Aldermore.co.uk");
			driver.findElement(By.name("lsubmit")).click();
			driver.findElement(By.name("passsfc")).sendKeys("07ZxEzuY");
			driver.findElement(By.name("bsubmit")).click();*/

		} catch (Exception e) {
			// TODO: handle exception
		}
		Thread.sleep(1000L);

	}

	private WebDriver getFirefoxDriver() throws Exception
	{
			    		
		System.out.println("openBrowser");
		
		
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffprofile = profile.getProfile("aldermore");		
		firefoxDriver = new FirefoxDriver(ffprofile);
		firefoxDriver.get(TEST_URL);
		login(firefoxDriver);
		Thread.sleep(1000L);

		return firefoxDriver;
	}

	private WebDriver getChromeDriver() throws Exception
	{
		System.out.println("openBrowser");
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		//chromePrefs.put("download.default_directory", downloadPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		
		chromeDriver = new ChromeDriver(cap);
		chromeDriver.get(TEST_URL);
		login(chromeDriver);
		Thread.sleep(1000L);

		return chromeDriver;
	}

	private WebDriver getInternetExplorerDriver() throws Exception
	{
		System.out.println("openBrowser");
		System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
		/*DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
		String version= "10";
		desiredCapabilities.setVersion(version);*/
		/*desiredCapabilities.*/
		internetExplorerDriver = new InternetExplorerDriver();
		internetExplorerDriver.get(TEST_URL);
		login(internetExplorerDriver);
		Thread.sleep(1000L);

		return internetExplorerDriver;
	}

	public WebDriver getBrowser(String browser) throws Exception
	{
		if (browser.equalsIgnoreCase("FF"))
		{
			return getFirefoxDriver();

		}else if (browser.equalsIgnoreCase("IE"))
		{
			return getInternetExplorerDriver();

		}else if (browser.equalsIgnoreCase("CH")) 
		{
			return getChromeDriver();

		}else 
		{
			return null;
		}
		
		/*return getChromeDriver(); */
	}

	/**
	 * 
	 * @Author Naveen.Kanak
	 * @Date 26 Apr 2016
	 * @Time 05:47:54
	 * @return
	 */
	public String getDateTime()
	{
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		Date d = new Date();
		return s.format(d);
	}	
	
	public void seleniumWait()
	{

	}
	
	public void takeScreenShot(ITestResult method, WebDriver driver) throws Exception 
	{
		
		if (driver != null) {
			if (method.isSuccess()==false) 
			{
				try {
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					String format = "jpg";
					String fileName = TestBase.errorScreenShotPath+"//"+method.getName()+"." + format;
					FileUtils.copyFile(scrFile, new File(fileName));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	@AfterSuite
	public void suiteEnd() throws Exception
	{
		resultUpdate rs = new resultUpdate();
		rs.update();

	}
	
	public void msg(String text)
	{
		System.out.println("Step : "+text);
	}

	public static WebElement waitForElement(final WebElement webElement, WebDriver driver)
	{
		// Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       .withTimeout(60, TimeUnit.SECONDS)
		       .pollingEvery(5, TimeUnit.SECONDS)
		       .ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);

		   WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
		     public WebElement apply(WebDriver driver) {
		       return webElement;
		     }
		   });
				
		return webElement;
	}

	public FirefoxProfile FirefoxDriverProfile() throws Exception {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", downloadPath);
		profile.setPreference("browser.helperApps.neverAsk.openFile",
		//		"application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
		return profile;
	}

	public void rename(String path, String referenceNo)
	{
		
		System.out.println("path : "+path);
		System.out.println("New Path : "+path+"_"+referenceNo);
		new File(path).renameTo(new File(path+"_"+referenceNo));
	}
	
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
	    File dir = new File(downloadPath);
	    File[] dir_contents = dir.listFiles();
	  	    
	    for (int i = 0; i < dir_contents.length; i++) {
	        if (dir_contents[i].getName().equals(fileName))
	            return flag=true;
	            }

	    return flag;
	}
	public void clickUsingActionClass(WebDriver driver, WebElement elle)
    {
          new Actions(driver).click(elle).build().perform();
    }

}
