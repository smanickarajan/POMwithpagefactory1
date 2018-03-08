package com.test.automation.uiAutomation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.uiAutomation.excelReader.Excel_Reader;

public class TestBase {
	public static final Logger log = Logger
			.getLogger(TestBase.class.getClass());
	public static WebDriver driver;
	public Properties OR;
	 File file;
	 FileInputStream f;
	 public static ExtentReports extent;
		public static ExtentTest test;
		public ITestResult result;

	String url = "http://automationpractice.com/index.php";
	String browser = "chrome";
	Excel_Reader excel;
	
	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\report\\test" + "_"+formater.format(calendar.getTime()) + ".html", false);
	}


	public void init() throws IOException {
		getConfig();
		selectbrowser(OR.getProperty("browser"));
		String log4jconfpath = "log4j.properties";
		PropertyConfigurator.configure(log4jconfpath);
		
	}

	public void selectbrowser(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ "/drivers/chromedriver.exe");
			log("Creating object for " + browser);
			driver = new ChromeDriver();
			   log("navigating to:-"+OR.getProperty("url"));
			   driver.get(OR.getProperty("url"));
			   log("Opening the URL " + url);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		} else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir")
							+ "/drivers/IEDriverServer.exe");
			log("Creating object for " + browser);
			driver = new InternetExplorerDriver();
			 log("navigating to:-"+OR.getProperty("url"));
			   driver.get(OR.getProperty("url"));
			   log("Opening the URL " + url);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}

		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/drivers/geckodriver.exe");
			log("Creating object for " + browser);
			driver = new FirefoxDriver();
			 log("navigating to:-"+OR.getProperty("url"));
			   driver.get(OR.getProperty("url"));
			   log("Opening the URL " + url);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
	}

	/*public void geturl(String url) {
		driver.get(url);
		log("Opening the URL " + url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}*/
	 public void getConfig() throws IOException{
		  OR = new Properties();
		  file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\automation\\uiAutomation\\config\\config.properties");
		  f = new FileInputStream(file);
		  OR.load(f);
	 }
	public String[][] getData(String excelName, String sheetName) {
		String path = System.getProperty("user.dir") + "/data/" + excelName;

		excel = new Excel_Reader(path);
		String[][] data = excel.getDataFromSheet(sheetName, excelName);
		return data;

	}

	public void waitForElement(int timeoutseconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutseconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void getScreenshot(String name) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir"))
					.getAbsolutePath()
					+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\screenshot\\";
			
			File destFile = new File((String) reportDirectory + name + "_"
					+ formatter.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			Reporter.log("<a href='" + destFile.getAbsolutePath()
					+ "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a>");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public void log(String data){
		log.info(data);
		Reporter.log(data);
		test.log(LogStatus.INFO, data);
		
	}
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\screenshot\\";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
	public void getresult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}

	@AfterMethod()
	public void afterMethod(ITestResult result) {
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}
	public void closeBrowser() {
		driver.quit();
		log.info("browser closed");
		extent.endTest(test);
		extent.flush();
	}
}
