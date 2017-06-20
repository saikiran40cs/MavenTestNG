package regressionPack_2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;

public class TC002ExecuteWithPOM {

	WebDriver driver;
	LoginPage objLogin;
	HomePage objHomePage;
	
	@BeforeTest
	public void setup(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://demo.borland.com/InsuranceWebExtJS/");
	}

	/**
	 * This regressionPack_2 case will loginButton in http://demo.borland.com/InsuranceWebExtJS/
	 * Login to application
	 * Verify the home page using Dashboard message
	 */
	@Test(priority=0)
	public void testLoginPage(){
		//Create Login Page object
	objLogin = new LoginPage(driver);
	//loginButton to application
	objLogin.loginToApp("john.smith@gmail.com", "john");
	// go the next page
	objHomePage = new HomePage(driver);
	//Verify home page
	Assert.assertTrue(objHomePage.getHomePageDashboardUserName().equalsIgnoreCase("John Smith"));
	}
	
	@AfterTest
	public void close(){
		driver.close();
	}
	
}
