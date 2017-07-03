package functionLibrary;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class UIOperation extends sharedFunctions{

	WebDriver driver;
	public UIOperation(){
		this.driver = webdriver;
	}
	
	/**
	 * Function to perform actions in UIr
	 * @param properties
	 * @param operation
	 * @param objectName
	 * @param objectType
	 * @param value
	 * @throws Exception
	 */
	public void perform(Properties p,String operation,String objectName,String objectType,String value) throws Exception{
		switch (operation.toUpperCase()) {
		case "CLICK":
			//Perform click
			driver.findElement(this.getObject(p,objectName,objectType)).click();
			break;
		case "SETTEXT":
			//Set text on control
			driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
			break;
		case "SELECTTEXT":
			//Select the value from the application
			new Select(driver.findElement(this.getObject(p, objectName, objectType))).selectByVisibleText(value);
			break;
		case "GOTOURL":
			//Get URL of the application
			driver.get(p.getProperty(value));
			break;
		case "GETTEXT":
			//Get text of an element
			driver.findElement(this.getObject(p,objectName,objectType)).getText();
			break;

		default:
			break;
		}
	}

	/**
	 * Find element BY using object type and value
	 * @param properties
	 * @param objectName
	 * @param objectType
	 * @returns the object
	 * @throws Exception
	 */
	private By getObject(Properties p,String objectName,String objectType) throws Exception{
		// Find by xpath
		if ("XPATH".equalsIgnoreCase(objectType)) {
			return By.xpath(p.getProperty(objectName));
		}
		// find by ID
		else if ("ID".equalsIgnoreCase(objectType)) {
			return By.id(p.getProperty(objectName));
		}
		// find by class
		else if ("CLASSNAME".equalsIgnoreCase(objectType)) {
			return By.className(p.getProperty(objectName));
		}
		// find by name
		else if ("NAME".equalsIgnoreCase(objectType)) {
			return By.name(p.getProperty(objectName));
		}
		// Find by css
		else if ("CSS".equalsIgnoreCase(objectType)) {
			return By.cssSelector(p.getProperty(objectName));
		}
		// find by link
		else if ("LINK".equalsIgnoreCase(objectType)) {
			return By.linkText(p.getProperty(objectName));
		}
		// find by partial link
		else if ("PARTIALLINK".equalsIgnoreCase(objectType)) {
			return By.partialLinkText(p.getProperty(objectName));
		} else {
			throw new Exception("Wrong object type");
		}
	}
}
