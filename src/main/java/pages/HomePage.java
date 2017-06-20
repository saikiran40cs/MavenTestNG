package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

	WebDriver driver;
	By homePageUserName = By.xpath("//label[@for='logout-form']");
	
	public HomePage(WebDriver driver){
		this.driver = driver;
	}
	
	//Get the Logged in User name from Home Page
		public String getHomePageDashboardUserName(){
		 return	driver.findElement(homePageUserName).getText();
		}
}
