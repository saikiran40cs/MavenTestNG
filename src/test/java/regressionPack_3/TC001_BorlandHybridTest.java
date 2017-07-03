package regressionPack_3;

import java.util.Properties;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import functionLibrary.ReadObject;
import functionLibrary.UIOperation;
import functionLibrary.sharedFunctions;

/**
 * @author saikiran40cs
 * THIS IS THE EXAMPLE OF KEYWORD WITH DATA DRIVEN TEST CASE
 *
 */
public class TC001_BorlandHybridTest extends sharedFunctions{
	
	
		
	/**
	 * Read Row by row from Excel and execute the test step
	 * @param testStepName
	 * @param keyword
	 * @param objectName
	 * @param objectType
	 * @param value
	 * @throws Exception
	 */
	@Test(priority = 0, enabled = true,dataProvider = "hybridData")
	public void testLogin(String testStepName, String keyword, String objectName, String objectType, String value)
			throws Exception {
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		UIOperation operation = new UIOperation();
		
		if (testStepName != null && testStepName.length() != 0) {
			initializeExtentReport();
			testInstance.log(Status.PASS,"'"+testStepName+"' is executed using hybrid framework");
		}
		// Call perform function to perform operation on UI
		operation.perform(allObjects, keyword, objectName, objectType, value);
	}	
}
