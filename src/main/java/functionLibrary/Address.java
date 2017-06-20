package functionLibrary;

import java.io.File;	

public class Address {

	
	public static final String fs=File.separator;
	/** Test Data Worksheet part for all applications */
	public static final String TESTCASES_WORKSHEET ="Testcases";
	public static final String TESTCASES_TESTCASEID="TestCaseID";
	public static final String TESTDATA_WORKSHEET = "ApplicationLevelTestData";
	
	/** RUN MODES FOR BROWSERS */
	public static final String RUNMODE="Runmode";
	public static final String RUN_ON_FIREFOX = "RunOnFirefox";
	public static final String RUN_ON_CHROME = "RunOnChrome";
	public static final String RUN_ON_IE = "RunOnIE";
	public static final String RUN_ON_SAFARI = "RunOnSafari";
	public static final String RUN_ON_OPERA = "RunOnOpera";
	public static final String RUN_ON_HEADLESS = "RunOnHeadless";
	public static final String YES="Y";
	
	/** Reports Worksheet */
	public static String REP_INPUT_FILE;

	private Address() {
	    throw new IllegalAccessError("Address class");
	}
}
