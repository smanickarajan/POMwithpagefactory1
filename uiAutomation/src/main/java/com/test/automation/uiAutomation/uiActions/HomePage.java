package com.test.automation.uiAutomation.uiActions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class HomePage {
	public static final Logger log = Logger
			.getLogger(HomePage.class.getClass());
	WebDriver driver;
	
	@FindBy(xpath="//A[@class='login']")
	WebElement signIn;
	
	@FindBy(xpath="//INPUT[@id='email']")
	WebElement loginEmailAddress;
	
	@FindBy(xpath="//INPUT[@id='passwd']")
	WebElement loginPassword;
	
	@FindBy(xpath="//button[@id='SubmitLogin1']")
	WebElement submitButton;
	
	@FindBy(xpath="(//LI[text()='Authentication failed.'])[1]")
	WebElement authenticationFailed;
	
	@FindBy(xpath="(//A[@class='logout'])")
	WebElement signout;
	
	public HomePage (WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginToApplication(String emailAddress,String password) throws InterruptedException{
		signIn.click();
		log(" Clicked on Sign in"+signIn.toString());
		loginEmailAddress.sendKeys(emailAddress);
		log(" Entered the email address"+emailAddress+" object is "+loginEmailAddress.toString());
		loginPassword.sendKeys(password);
		log(" Entered the email address"+password+" object is "+loginPassword.toString());
		submitButton.click();
		log(" Clicked on submit button");
		Thread.sleep(2000);
		log(" waited for 2 seconds");
	}
	public String getInvalidLoginText(){
		log(" error message is "+authenticationFailed.getText());
		return authenticationFailed.getText();
}
	public boolean signoutDisplayed(){
		
		try {
			signout.isDisplayed();
			log("signout displayed");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public void signout() throws InterruptedException{
		signout.click();
		log(" Clicked on signout button  ");
		Thread.sleep(2000);
		log(" waited for 2 seconds");
	}
	
	public void log(String data){
		log.info(data);
		Reporter.log(data);
		
	}
}