package regressionPack_3;

import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import functionLibrary.Const;
import functionLibrary.ReadObject;
import functionLibrary.UIOperation;
import functionLibrary.sharedFunctions;
import utilities.XL_Reader;

/**
 * @author saikiran40cs
 * THIS IS THE EXAMPLE OF KEYWORD WITH DATA DRIVEN TEST CASE
 *
 */
public class TC001_BorlandHybridTest extends sharedFunctions{
	
	
	/**
	 * Read Row by row from Excel and execute the test step
	 * @param testcaseName
	 * @param keyword
	 * @param objectName
	 * @param objectType
	 * @param value
	 * @throws Exception
	 */
	@Test(dataProvider = "hybridData")
	public void testLogin(String testcaseName, String keyword, String objectName, String objectType, String value)
			throws Exception {
		if (testcaseName != null && testcaseName.length() != 0) {
			initializeExtentReport();			
		}
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		UIOperation operation = new UIOperation(webdriver);
		// Call perform function to perform operation on UI
		operation.perform(allObjects, keyword, objectName, objectType, value);
			
	}

	@DataProvider(name = "hybridData")
	public Object[][] getDataFromDataprovider() throws IOException {
		Object[][] object = null;
		XL_Reader file = new XL_Reader();

		// Read keyword sheet
		Sheet KiranSheet = file.readExcel(Const.testDataPath, "TestCase.xlsx","Hybrid_Key_Framework");
		// Find number of rows in excel file
		int rowCount = KiranSheet.getLastRowNum() - KiranSheet.getFirstRowNum();
		object = new Object[rowCount][5];
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			Row row = KiranSheet.getRow(i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				object[i][j] = row.getCell(j).toString();
			}
		}
		return object;
	}
	
	

}
