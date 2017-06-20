package utilities;

import org.apache.log4j.Logger;

public class Log {
	// Initialize Log4j logs 
	private Logger log4jLogger=Logger.getLogger(Log.class.getName());
	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

	public void startTestCase(String sTestCaseName) {
		
		log4jLogger.info("****************************************************************************************");
		log4jLogger.info("****************************************************************************************");
		log4jLogger.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		log4jLogger.info("****************************************************************************************");
		log4jLogger.info("****************************************************************************************");
	}

	// This is to print log for the ending of the test case
	public void endTestCase(String sTestCaseName) {
		log4jLogger.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
		log4jLogger.info("X");
		log4jLogger.info("X");
		log4jLogger.info("X");
		log4jLogger.info("X");
	}

	// Need to create these methods, so that they can be called
	public void info(String message) {
		log4jLogger.info(message);
	}

	public void warn(String message) {
		log4jLogger.warn(message);
	}

	public void error(String message) {
		log4jLogger.error(message);
	}

	public void fatal(String message) {
		log4jLogger.fatal(message);
	}

	public void debug(String message) {
		log4jLogger.debug(message);
	}
}