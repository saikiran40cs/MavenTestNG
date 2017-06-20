package functionLibrary;

import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import junit.framework.AssertionFailedError;

public class ErrorHandling extends sharedFunctions{
	
	/**
	 * AssertEquals function is used to verify the expected and actual value
	 * @author saikiran.nataraja
	 * @param expected value needs to be passed
	 * @param actual value retreived from the application
	 * @param message to be logged if the match is true
	 */
	public  void assertEquals(Object expected, Object actual,String message) {
		if (expected == null && actual == null) {
			testInstance.log(Status.FAIL, "Expected and Actual values are NULL");
			Reporter.log("Expected and Actual values are NULL", false);
			extentInstance.setTestRunnerOutput("Expected and Actual values are NULL"); 
			return;
		}
		if (expected != null && expected.equals(actual)) {
			testInstance.log(Status.PASS, message);
			Reporter.log(message);
			extentInstance.setTestRunnerOutput(message); 
			return;
		} else{
			Reporter.log("Expected Result: '" + expected + "' does NOT match with Actual Result: '" + actual + "' .");
			extentInstance.setTestRunnerOutput("Expected Result: '" + expected + "' does NOT match with Actual Result: '" + actual + "' .");
			Reporter.log("Error Snapshot is Located in : " + Const.ErrorReportPath+ Captialize(runOnBrowser) + "_" + executingTestCaseName + Const.DateFormat + ".jpg");
			fail(format("", expected, actual));
		}
	}

	/**
	 * Sub to check Assertion failure messages
	 * @author saikiran.nataraja
	 * @param message
	 */
	public void fail(String message) {
		if (message == null) {
			throw new AssertionFailedError();
		}
		throw new AssertionFailedError(message);
	}

	/**
	 * Sub to report the issue in the required formatted text
	 * @author saikiran.nataraja
	 * @param message
	 * @param expected
	 * @param actual
	 * @return
	 */
	public  String format(String message, Object expected, Object actual) {
		String formatted = "The Expected Outcome is not matched with Actual Outcome::";
		if (message != null && message.length() > 0) {
			formatted = message + "  ";
		}
		return formatted + "Expected :'" + expected + "' but Actual is:'" + actual + "'";
	}

}
