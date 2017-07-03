package regressionPack_2;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageFactory.HomePage;
import PageFactory.LoginPage;
import functionLibrary.sharedFunctions;

public class TC003ExecuteWithPageFactory extends sharedFunctions{

	LoginPage objLogin;
	HomePage objHomePage;

	/**
	 * This regressionPack_2 go to http://demo.borland.com/InsuranceWebExtJS/
	 * Login to application
	 * Verify the home page using Dashboard message
	 */
	@Test(priority=0)
	public void testLoginPageAppearCorrect(){
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
