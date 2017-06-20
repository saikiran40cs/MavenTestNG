package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	WebDriver driver;
	By emailIDTextField = By.name("login-form:email");
	By passwordTextField = By.name("login-form:password");
	By loginButton = By.name("login-form:login");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	// Set user name in text box
	public void setUserName(String strUserName) {
		driver.findElement(emailIDTextField).sendKeys(strUserName);
	}

	// Set passwordTextField in passwordTextField text box
	public void setPassword(String strPassword) {
		driver.findElement(passwordTextField).sendKeys(strPassword);
	}

	// Click on loginButton button
	public void clickLogin() {
		driver.findElement(loginButton).click();
	}

	/**
	 * This POM method will be exposed in regressionPack_2 case to loginButton in the
	 * application
	 * 
	 * @param strUserName
	 * @param strPasword
	 * @return
	 */
	public void loginToApp(String strUserName, String strPasword) {
		// Fill user name
		this.setUserName(strUserName);
		// Fill passwordTextField
		this.setPassword(strPasword);
		// Click Login button
		this.clickLogin();

	}
}
