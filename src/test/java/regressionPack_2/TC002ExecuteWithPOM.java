package regressionPack_2;

import org.testng.Assert;
import org.testng.annotations.Test;

import functionLibrary.sharedFunctions;
import pages.HomePage;
import pages.LoginPage;

public class TC002ExecuteWithPOM extends sharedFunctions{

	LoginPage objLogin;
	HomePage objHomePage;	

	/**
	 * This regressionPack_2 case will loginButton in http://demo.borland.com/InsuranceWebExtJS/
	 * Login to application
	 * Verify the home page using Dashboard message
	 */
	@Test(priority=0)
	public void testLoginPage(){
		initializeExtentReport();
		webdriver.get("http://demo.borland.com/InsuranceWebExtJS/");
		//Create Login Page object
		objLogin = new LoginPage(webdriver);
		//loginButton to application
		objLogin.loginToApp("john.smith@gmail.com", "john");
		// go the next page
		objHomePage = new HomePage(webdriver);
		//Verify home page
		Assert.assertTrue(objHomePage.getHomePageDashboardUserName().equalsIgnoreCase("John Smith"));
	}	
}
