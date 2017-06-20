package regressionPack_1;

import java.util.Date;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import functionLibrary.Const;
import functionLibrary.sharedFunctions;

public class TC002_Extent_SuiteTest extends sharedFunctions{
	
	@Test
	public void tc002()	throws Exception {
		initializeExtentReport();
		testInstance.log(Status.INFO, "Second Test is executed on: "+Const.sdf.format(new Date()));
	}
}
