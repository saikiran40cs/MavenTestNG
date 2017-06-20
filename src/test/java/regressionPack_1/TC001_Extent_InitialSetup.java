package regressionPack_1;

import java.io.File;
import java.util.Date;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import functionLibrary.Const;
import functionLibrary.sharedFunctions;
import utilities.Retry;

public class TC001_Extent_InitialSetup extends sharedFunctions{
	
	
	@Test(priority = 0, enabled = true, dataProvider = "",retryAnalyzer = Retry.class)
	public void tc001()	throws Exception {
		initializeExtentReport();
		String currentDir = "file://" + new File(System.getProperty("user.dir")).getAbsolutePath() + File.separator + "pom.xml";
        testInstance.log(Status.PASS, "First Test  is executed on:"+Const.sdf.format(new Date()));
        testInstance.log(Status.INFO,"<a href='" + currentDir + "'><font color='green'>View Pom file</font></a>");
	}
}
