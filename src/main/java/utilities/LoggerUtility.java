package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class LoggerUtility {
	// Initialize Log4j logs

	private static final Logger log4jLogger = (Logger) LogManager.getLogger(LoggerUtility.class.getName());

	/**
	 * @author saikirannataraja
	 * @see This is to print log for the beginning of the test case
	 * @param sTestCaseName
	 */
	public void startTestCase(String sTestCaseName) {

		System.out.println("log4j.configurationFile :: " + System.getProperty("log4j.configurationFile"));
		log4jLogger.trace("****************************************************************************************");
		log4jLogger.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		log4jLogger.trace("****************************************************************************************");
	}

	/**
	 * @author saikirannataraja
	 * @see This is to print log for the ending of the test case
	 * @param sTestCaseName
	 */
	public void endTestCase(String sTestCaseName) {
		log4jLogger
				.trace("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
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

	public void trace(String message) {
		log4jLogger.trace(message);
	}
}
