package com.test.automation.uiAutomation.homepage;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.HomePage;

public class TC001_VerifyLoginWithInvalidCredentials extends TestBase {
	public static final Logger log = Logger
			.getLogger(TC001_VerifyLoginWithInvalidCredentials.class.getClass());
	HomePage homepage;

	@BeforeClass
	public void setUp() throws IOException {
		init();
	}

	@Test
	public void verifyLoginWithInvalidCredentials() throws InterruptedException {
		log.info("=========== Starting verifyLoginWithInvalidCredentials=======");
		homepage = new HomePage(driver);
		homepage.loginToApplication("test1@gmail.com", "password");
		Assert.assertEquals(homepage.getInvalidLoginText(),
				"Authentication failed.");
		log.info("=========== Ending verifyLoginWithInvalidCredentials=======");
	}

	@AfterClass
	public void endTest() {
		driver.close();
	}

}
