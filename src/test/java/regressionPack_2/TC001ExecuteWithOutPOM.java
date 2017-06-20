package regressionPack_2;

import java.util.Date;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import functionLibrary.Const;
import functionLibrary.sharedFunctions;


public class TC001ExecuteWithOutPOM extends sharedFunctions{

	/**
	 * @author saikiran.nataraja
	 * This regressionPack_2 case will loginButton in http://demo.borland.com/InsuranceWebExtJS/
	 * Login to application
	 * Verify the home page using Dashboard message
	 */
	@Test(priority=0)
	public void testLoginPage(){
		initializeExtentReport();
		webdriver.get("http://demo.borland.com/InsuranceWebExtJS/");
		//Find user name and fill user name
	    webdriver.findElement(By.name("login-form:email")).sendKeys("john.smith@gmail.com");
	    //find passwordTextField and fill it
	    webdriver.findElement(By.name("login-form:password")).sendKeys("john");
	    //click loginButton button
	    webdriver.findElement(By.name("login-form:login")).click(); 
	    String homeText = webdriver.findElement(By.xpath("//label[@for='logout-form']")).getText();
	    //verify loginButton success
		Assert.assertTrue(homeText.equalsIgnoreCase("John Smith"));
		testInstance.log(Status.PASS, "First Test  is executed on:"+Const.sdf.format(new Date()));
		webdriver.close();
	}
	
}
