/*'*************************************************************************************************************************************************
' Class Name			: Const
' Description			: Used to store the variables that are used across.
' How to Use			:
'-----------------------------------------------------------------
' Author                    Version          Creation Date         
'-----------------------------------------------------------------
' Sai Kiran Nataraja         v1.0             16-December-2016		
'*************************************************************************************************************************************************
 */
package functionLibrary;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import utilities.XL_Reader;

/** @author saikiran.nataraja*/

public final class Const{

	private static XL_Reader TCXLS;
	private static XL_Reader CREDXLS;
	private static XL_Reader REPORTXLS;

	//for additional accuracy use the String format for date as "ddmmyyyy_hhmmss_sss_"
	public static final String DateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss_").format(new Date());
	public final static String WEEK_NUMBER=new SimpleDateFormat("w").format(new java.util.Date());	
	//For Report start time and End time format
	private static SimpleDateFormat DateFormatSettings;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
	public static final String fs=File.separator;
	public static final String configPath=System.getProperty("user.dir")+fs+"src"+fs+"main"+fs+"resources"+fs+"configuration"+fs;
	public static final String testDataPath=System.getProperty("user.dir")+fs+"src"+fs+"test"+fs+"resources"+fs+"testData"+fs;
	public static String ReportPath=System.getProperty("user.dir")+fs+"target"+fs+"ExtentReports"+fs+"Week"+Const.WEEK_NUMBER+fs;
	public static final String downloadsPath=System.getProperty("user.dir")+fs+"downloads"+fs;
	public static String ErrorReportPath=System.getProperty("user.dir")+fs+"target"+fs+"ExtentReports"+fs+"Week"+Const.WEEK_NUMBER+fs;
	//Object Repository location
	public static final String objectRepPath=System.getProperty("user.dir")+fs+"src"+fs+"main"+fs+"resources"+fs+"objects"+fs;
	//Driver path location
	public static final String firefoxDriverPath=configPath+fs+"geckodriver_win32_v0.17.0.exe";
	public static final String chromeDriverPath=configPath+fs+"chromedriver_win32_v2.30.exe";
	public static final String IEDriverPath=configPath+fs+"IEDriverServer_Win32_v3.4.0.exe";
	public static final String operaDriverPath=configPath+fs+"operadriver_win32_v2.27.exe";

	private Const() {
		throw new IllegalAccessError("Const class");
	}

	/**
	 * @return
	 */
	public static XL_Reader getTCXLS() {
		return TCXLS;
	}

	/**
	 * @param tCXLS
	 */
	public static void setTCXLS(XL_Reader tCXLS) {
		TCXLS = tCXLS;
	}

	/**
	 * @return
	 */
	public static XL_Reader getCREDXLS() {
		return CREDXLS;
	}

	/**
	 * @param cREDXLS
	 */
	public static void setCREDXLS(XL_Reader cREDXLS) {
		CREDXLS = cREDXLS;
	}

	/**
	 * @return
	 */
	public static XL_Reader getREPORTXLS() {
		return REPORTXLS;
	}

	/**
	 * @param rEPORTXLS
	 */
	public static void setREPORTXLS(XL_Reader rEPORTXLS) {
		REPORTXLS = rEPORTXLS;
	}
	
	/**
	 * @return the dateFormatSettings
	 */
	public static SimpleDateFormat getDateFormatSettings() {
		return DateFormatSettings;
	}

	/**
	 * @param dateFormatSettings the dateFormatSettings to set
	 */
	public static void setDateFormatSettings() {
		DateFormatSettings = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss",Locale.ENGLISH);
	}
}
