package utilities;

import java.io.File;

import org.testng.Reporter;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;

import functionLibrary.Const;

/**
 * THIS IS THE MAIN CORE FILE FOR EXTENT REPORTS
 * @author saikiran.nataraja
 */
public class ExtentManager {
	
	private static final int mongodb_port = 27017;
	private static final String mongodb_host = "localhost";
	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentXReporter extentxReporter;
	private static String filePath;
	
	public ExtentManager(){
		filePath= Const.ReportPath +Const.DateFormat+"AutomationTestReport.html";
		//Create ExtentReport Directory if doesnot exists
		File dir = new File(Const.ReportPath);
		//If SecurityManager.checkWrite(java.lang.String) method denies write access to the file.Hence made the directory writable
		if(!(dir.setWritable(true))){
			Reporter.log("Exception in ExtentManager function"); 
		}
		dir.mkdirs();
	}
	
	public ExtentReports createExtentRep(){
		if (extent != null)
            return extent; //avoid creating new instance of HTML file if it is not null
		htmlReporter=getHtmlReporter();
		extentxReporter=getXReporter();
        extent = new ExtentReports();		
        extent.setSystemInfo("Operating System", "Windows 7");
        extent.setSystemInfo("Selenium Version", "3.4");
        // class view:
        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
//        extent.attachReporter(htmlReporter,extentxReporter);
		extent.attachReporter(htmlReporter);
		return extent;
	}
 
	private static ExtentHtmlReporter getHtmlReporter() {
		Reporter.log(filePath,false);
		htmlReporter = new ExtentHtmlReporter(new File(filePath));		
        htmlReporter.loadXMLConfig(Const.configPath+"extent-config.xml");
        htmlReporter.setAppendExisting(false);    
        return htmlReporter;
	}
	
	private static ExtentXReporter getXReporter() {
		// initialize ExtentXReporter
		extentxReporter = new ExtentXReporter(mongodb_host, mongodb_port);
		extentxReporter.config().setServerUrl("http://"+mongodb_host+":1337");
		// if using multiple projects, set project name - doing so will allow you to filter by project and view all its reports
		extentxReporter.config().setProjectName("Kiran_MavenProject");
		// report or build name
		extentxReporter.config().setReportName("Build-1224 - Tests made by Kiran");
		return extentxReporter;
	}
		
	public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		return test;
	}
}