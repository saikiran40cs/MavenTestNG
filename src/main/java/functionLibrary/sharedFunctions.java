package functionLibrary;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.How;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import utilities.ExtentManager;
import utilities.Log;
import utilities.TestUtilities;

public class sharedFunctions extends ExtentManager {

	protected String executingTestCaseName;
	private Log loggerInstance;
	protected ExtentReports extentInstance;
	protected ExtentTest testInstance;
	protected ExtentHtmlReporter htmlReporterInstance; 
	public final String getCurrentlyLoggedInUser=System.getProperty("user.name");
	protected WebDriver webdriver= null;
	protected String runOnBrowser = null;
	protected Properties OR = null;
	protected String baseURL = "";
	private int proxyType=ProxyType.AUTODETECT.ordinal();
	
	/**
	 * @see How to access the data  provider from a sub class
	 * 1. Import the required package.
	 * 2. Add to the test annotation dataProviderClass= sharedFunctions.class
	 * @returns array of objects
	 * @author saikiran.nataraja
	 */
	@DataProvider(name="testgetdata")
	public Object[][] testgetdata() {
		try {
			Object[][] testgetdata = TestUtilities.getTestDataBasedOnTestCase(Const.getTCXLS(), super.getClass().getSimpleName());
			return testgetdata;
		} catch (NullPointerException e) {
			Reporter.log("The test data provider in WAS Generic Functions could not identify excel");
			throw e;
		}
	}

	@BeforeSuite
	/**
	 * Function to change the Error Screenshot folder name before the Suite starts
	 * @author saikiran.nataraja
	 */
	public void CreateErrorRepFolder() throws Exception{
		Const.setDateFormatSettings(); //set the date format before starting each test
		Const.ErrorReportPath=Const.ReportPath+Const.DateFormat+"ErrorScreenshots";
	}

	/**
	 * This is to create an extent manager instance before every new class
	 * @author saikiran.nataraja
	 */
	@BeforeClass
	public void createExtentInstance(){
		loggerInstance=new Log();
		ExtentManager xtentMgr= new ExtentManager();
		extentInstance = xtentMgr.createExtentRep();
		
	}

	@Parameters({"BrowserType"})
	@BeforeTest
	public void browserSetup(@Optional("chrome") String browser) {
		
		runOnBrowser=browser;
		switch (runOnBrowser) {
		case "firefox": 
			/** For Firefox below 47.0.2
			 * 	If you have selenium 3.4 version libraries then you cannot have firefox version below 48*/
			//For Firefox above 47.0.2
			System.setProperty("webdriver.gecko.driver",Const.firefoxDriverPath);
			System.setProperty("webdriver.log.logfile", "INFO");
			webdriver = new FirefoxDriver(createFirefoxProfile());
			break;
		case "chrome":
			webdriver = new ChromeDriver(createChromeCapabilites());
			break;
		case "ie":
			//Add Pop-up allowed in IE manually
			webdriver = new InternetExplorerDriver(createIECapabilities());
			break;
		case "safari":
			webdriver = new SafariDriver(createSafariCapabilities());
			break;
		case "opera":
			webdriver = new OperaDriver(createOperaCapabilities());
			break;
		default:
			Reporter.log("Driver Not Found");
			break;
		}
		webdriver.manage().window().maximize();
		webdriver.manage().deleteAllCookies();
		webdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}
	
	/**
	 * @return
	 */
	public FirefoxProfile createFirefoxProfile(){
		FirefoxProfile firefoxProfile= new FirefoxProfile();
		firefoxProfile.clean(null);
		firefoxProfile.setAcceptUntrustedCertificates(true); 	// Set profile to accept untrusted certificates
		firefoxProfile.setAssumeUntrustedCertificateIssuer(false);	// Set profile to not assume certificate issuer is untrusted
		firefoxProfile.setPreference("browser.download.folderList", 2);		//0- Desktop, 1-Browser's Default Path , 2- Custom Download Path
		firefoxProfile.setPreference("plugin.scan.plid.all", false);
		firefoxProfile.setPreference("plugin.scan.Acrobat", "99");
		firefoxProfile.setPreference("marionette.logging", false);
		firefoxProfile.setPreference("browser.download.dir", Const.downloadsPath);
		firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream;application/pdf");
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
		firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
		firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		firefoxProfile.setPreference("browser.download.manager.closeWhenDone", false);
		firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
		firefoxProfile.setPreference("browser.download.manager.useWindow", false);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("network.proxy.type",proxyType );
		firefoxProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
		//Do NOT COMMENT Below line as it is required to download the PDF into download location
		firefoxProfile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");	
		return firefoxProfile;
	}
	
	/**
	 * @return
	 */
	public DesiredCapabilities createChromeCapabilites(){
		ChromeOptions chromeOptions = new ChromeOptions();
		DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
		chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);					
		chromeCapabilities.setCapability("network.proxy.type", proxyType);
		// Set ACCEPT_SSL_CERTS  variable to true
		chromeCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		chromeCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); //17-05-2016 - Newly added to remove cache issues
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.extensions_to_open", "pdf");
		prefs.put("--always-authorize-plugins",false);
		prefs.put("download.prompt_for_download", "true");
		prefs.put("download.default_directory", Const.downloadsPath);
		chromeOptions.setExperimentalOption("prefs", prefs);
		System.setProperty("webdriver.chrome.driver",Const.chromeDriverPath);
		System.setProperty("webdriver.chrome.args", "--disable-logging");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		return chromeCapabilities;
	}
	
	/**
	 * @return
	 */
	public DesiredCapabilities createIECapabilities(){
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		// Set ACCEPT_SSL_CERTS  variable to true
		ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ieCapabilities.setCapability("network.proxy.type", ProxyType.AUTODETECT.ordinal());
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); //Added to avoid the Protected Mode settings for all zones
		ieCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
		ieCapabilities.setCapability(InternetExplorerDriver.SILENT, true);
		ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);					
		System.setProperty("webdriver.ie.driver",Const.IEDriverPath);
		return ieCapabilities;
	}
	
	/**
	 * @return
	 */
	public DesiredCapabilities createOperaCapabilities(){
		OperaOptions operaOptions = new OperaOptions();					
		DesiredCapabilities operaCapabilities = DesiredCapabilities.chrome();
		operaCapabilities.setCapability(ChromeOptions.CAPABILITY, operaOptions);
		operaCapabilities.setCapability("network.proxy.type", proxyType);
		// Set ACCEPT_SSL_CERTS  variable to true
		operaCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		System.setProperty("webdriver.opera.driver",Const.operaDriverPath);
		System.setProperty("opera.arguments", "--disable-logging");
		System.setProperty("webdriver.opera.silentOutput", "true");
		return operaCapabilities;
	}
	
	/**
	 * @return
	 */
	public DesiredCapabilities createSafariCapabilities(){
		SafariOptions safariOptions=new SafariOptions();
		DesiredCapabilities SafariCapabilities = DesiredCapabilities.safari();
		SafariCapabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);
		// Set ACCEPT_SSL_CERTS  variable to true
		SafariCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		SafariCapabilities.setCapability("network.proxy.type", proxyType);
		return SafariCapabilities;
	}
	
	/**
	 * Function to return browser version 
	 * @author saikiran.nataraja
	 * @return browser name and version
	 */
	public String getBrowserVersion() {
		String browser_version;
		Capabilities cap = ((RemoteWebDriver) webdriver).getCapabilities();
		String browsername = cap.getBrowserName();
		String GetuAgent = (String) ((JavascriptExecutor) webdriver).executeScript("return navigator.userAgent;");
		// This block to find out IE Version number
		if ("internet explorer".equalsIgnoreCase(browsername)) {
			// uAgent return as "MSIE 8.0 Windows" for IE8
			if (GetuAgent.contains("MSIE") && GetuAgent.contains("Windows")) {
				browser_version = GetuAgent.substring(GetuAgent.indexOf("MSIE") + 5, GetuAgent.indexOf("Windows") - 2);
			} else if (GetuAgent.contains("Trident/7.0")) {
				browser_version = "11.0";
			} else {
				browser_version = "0.0";
			}
		} 
		else if ("firefox".equalsIgnoreCase(browsername)) {
			browser_version=GetuAgent.substring(GetuAgent.indexOf("Firefox")).split(" ")[0].replace("/", "-");
			browser_version=browser_version.replace("Firefox-", "");
		}else{	//Browser version for Chrome and Opera
			browser_version = cap.getVersion();
		}		
		String browserversion = browser_version.substring(0, browser_version.indexOf('.'));
		return Captialize(browsername) + " browser (Version: " + browserversion +" )";
	}

	/**
	 * Function to Captialize the word
	 * @param RequiredWord
	 * @return
	 */
	public String Captialize(String RequiredWord)
	{
		return RequiredWord.substring(0,1).toUpperCase()+RequiredWord.substring(1, RequiredWord.length()).toLowerCase();
	}

	/**
	 * Function to initialize Extent report and it must be called only @Test annotation
	 * @author saikiran.nataraja
	 */	
	public void initializeExtentReport(){
		//Instantiating the ExtentReports
		executingTestCaseName = super.getClass().getSimpleName();
		loggerInstance.startTestCase(executingTestCaseName);
		
		//Create testInstance
		testInstance=extentInstance.createTest(executingTestCaseName, "'"+executingTestCaseName+"' is used to check details in Application.");
		testInstance.assignAuthor(getCurrentlyLoggedInUser);
		testInstance.assignCategory("RegressionTestCases_Test");
	}

	
	@AfterTest
	public void closeSession(){
		if(webdriver!=null){
			webdriver.close();
		}
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("taskkill /F /IM geckodriver_win32_v0.17.0.exe");
			rt.exec("taskkill /F /IM chromedriver_win32_v2.30.exe");
			rt.exec("taskkill /F /IM IEDriverServer_Win32_v3.4.0.exe");
			rt.exec("taskkill /F /IM operadriver_win32_v2.27.exe");
		} catch (IOException e) {
			 // clean up state...
		    Thread.currentThread().interrupt();
		}
	}
	
	@AfterClass
	public void tearDownFunction()  {
		loggerInstance.endTestCase(executingTestCaseName);
		// write all resources to report file
		extentInstance.flush();
		
	}
	
	/**
	 * Capture the operations on Test completion
	 * @param testResult
	 * @author saikiran.nataraja
	 * @throws AWTException 
	 * @throws IOException
	 * @throws  
	 */
	@AfterMethod 
	public void operationsOnTestCompletion(ITestResult testResult) throws AWTException, IOException {
		if (testResult.getStatus() == ITestResult.FAILURE){ 
			Reporter.log(" - FAILED.",true);
			//Create Error Screenshot Directory if doesnot exists
			File dir = new File(Const.ErrorReportPath);
			dir.mkdirs();
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			//Here the screenshot path is reduced to a maximum of 20 literals
			File imagePath=new File(Const.ErrorReportPath+Const.fs+Const.DateFormat+Captialize(runOnBrowser)+"_"+executingTestCaseName.substring(0, Math.min(executingTestCaseName.length(), 20)) +".jpeg");
			try {
				ImageIO.write(image, "JPG", imagePath);
			} catch (IOException e) {
				throw e;
			}
			//Extent Reports take screenshot
			testInstance.log(Status.FAIL, "Failure Stack Trace: "+ testResult.getThrowable().getMessage());
			//Check below line if the screenshot is NOT displayed properly
			String relativeErrImgPath=imagePath.getAbsoluteFile().toString().replace(Const.ReportPath, "."+Const.fs);
			Reporter.log(relativeErrImgPath,true);
			// adding screenshots to log
			testInstance.log(Status.INFO,"Refer below Snapshot: ",MediaEntityBuilder.createScreenCaptureFromPath(relativeErrImgPath).build());						
		}else if (testResult.getStatus() == ITestResult.SKIP) {
			Reporter.log(" - SKIPPED.",true);
			testInstance.log(Status.SKIP, "Test skipped: " + testResult.getThrowable().getMessage());
		}else{
			Reporter.log(" - PASSED.",true);
			testInstance.log(Status.PASS, "'"+executingTestCaseName+"' is passed based on the test criteria.");
		}
		
	}
	

}
