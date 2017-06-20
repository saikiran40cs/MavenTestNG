package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	@FindBy(name = "login-form:email")
	WebElement emailIDTextField;

	@FindBy(name = "login-form:password")
	WebElement passwordTextField;

	@FindBy(name = "login-form:login")
	WebElement loginButton;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}

	// Set user name in textbox
	public void setUserName(String strUserName) {
		emailIDTextField.sendKeys(strUserName);

	}

	// Set passwordTextField in passwordTextField textbox
	public void setPassword(String strPassword) {
		passwordTextField.sendKeys(strPassword);
	}

	// Click on loginButton button
	public void clickLogin() {
		loginButton.click();
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
