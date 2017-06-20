package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

import functionLibrary.Address;
import functionLibrary.Const;


public class XL_Reader {
	private String path;
	private FileInputStream fis = null;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;


	/**
	 * @param path
	 * @throws IOException 
	 */
	public XL_Reader(String path) throws IOException {
		// System.gc();
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);

		} catch (Exception e) {
			throw e;
		}finally{
			if(fis!=null){
				fis.close();
			}
		}

	}


	public XL_Reader() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Function to readExcel and send it to main class for Hybrid framework
	 * @param filePath
	 * @param fileName
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public XSSFSheet readExcel(String filePath,String fileName,String sheetName) throws IOException{
		//Create a object of File class to open xlsx file
		File file =	new File(filePath+File.separator+fileName);
		//Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook KiranWorkbook = null;
		//Find the file extension by spliting file name in substing and getting only extension name
		String fileExtensionName = fileName.substring(fileName.indexOf('.'));
		//Check condition if the file is xlsx file
		if(fileExtensionName.equals(".xlsx")){
			//If it is xlsx file then create object of XSSFWorkbook class
			KiranWorkbook = new XSSFWorkbook(inputStream);
		}
		//Check condition if the file is xls file
		else if(fileExtensionName.equals(".xls")){
			//If it is xls file then create object of HSSFWorkbook class
			Reporter.log("Please pass and XLSX file");
		}
		//Read sheet inside the workbook by its name
		return  KiranWorkbook.getSheet(sheetName);	
	}

	/**
	 * @author saikiran.nataraja
	 * @param sheetName
	 * @returns 0 - if index is -1, else returns the last row number
	 */
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	/**
	 * Author:- Sai Kiran Nataraja Get Column Count for a particular row in a
	 * worksheet
	 * 
	 * @param sheetName
	 * @param rowNumber
	 * @return
	 */
	public int getColCountForParticularRow(String sheetName, int rowNumber) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int noOfColumns = sheet.getRow(rowNumber).getPhysicalNumberOfCells();			// getLastCellNum();
			return noOfColumns;
		}

	}

	/**
	 * Function to get cell data from Particular test case
	 * @author saikiran.nataraja
	 * @param ExcelFileName
	 * @param sheetName
	 * @param TCName
	 * @param colName
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	public String getCellDataFromParticularTestCase(String ExcelFileName,String sheetName, String TCName, String colName) throws Exception {
		int rowNum = 0;
		try {
			workbook = new XSSFWorkbook(ExcelFileName);
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			rowNum = findRowNumber(TCName)+1;
			row = sheet.getRow(rowNum);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())){
					Reporter.log(row.getCell(i).getStringCellValue().trim());
					col_Num = i;
					break;
				}
			}
			if (col_Num == -1)
				return "@null";
			rowNum= rowNum + 1;
			row = sheet.getRow(rowNum);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			Reporter.log("File IO Exception in XLS_REadWrite row " + rowNum + " or column " + colName + " does not exist in xls" + e.toString());
			throw e;
		}
	}

	// returns the data from a cell
	/**
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "@null";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {
			Reporter.log("File IO Exception in XLS_REadWrite.java row " + rowNum + " or column " + colName + " does not exist in xls" + e.toString());
			throw e;
		}
	}

	// returns the data from a cell
	/**
	 * @param sheetName
	 * @param colNum
	 * @param rowNum
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			Reporter.log("File IO Exception in XLS_REadWrite row " + rowNum + " or column " + colNum + " does not exist in xls" + e.toString());
			throw e;
		}
	}


	/**
	 * Function to find the row number of the specific string available in the
	 * excel
	 * 
	 * @author saikiran.nataraja
	 * @param cellContent
	 * @returns Row number of the String present, Otherwise returns 0
	 */
	@SuppressWarnings("deprecation")
	public int findRowNumber(String cellContent) {
		sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(cellContent)) {
						return row.getRowNum();
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Function to autosize columns in a workbook
	 * 
	 * @author saikiran.nataraja
	 */
	public void autoSizeColumns() {
		sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if (sheet.getPhysicalNumberOfRows() > 0) {
			Row row = sheet.getRow(0);
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				sheet.autoSizeColumn(columnIndex);
			}
		}
	}

	/**
	 * Function to set cell data in the spreadsheet
	 * 
	 * @author saikiran.nataraja
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param data
	 * @returns true if data is set successfully else false
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			XSSFColor redColor = new XSSFColor(IndexedColors.RED);
			XSSFColor greenColor = new XSSFColor(IndexedColors.BRIGHT_GREEN);
			XSSFColor goldColor = new XSSFColor(IndexedColors.GOLD);
			XSSFCellStyle fail = workbook.createCellStyle();
			XSSFCellStyle pass = workbook.createCellStyle();
			XSSFCellStyle skip = workbook.createCellStyle();
			fail.setFillForegroundColor(redColor);
			fail.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// Set the border style for the workbook
			fail.setBorderBottom(BorderStyle.THIN);
			fail.setBorderLeft(BorderStyle.THIN);
			fail.setBorderRight(BorderStyle.THIN);
			fail.setBorderTop(BorderStyle.THIN);
			fail.setAlignment(HorizontalAlignment.LEFT);

			pass.setFillForegroundColor(greenColor);
			pass.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// Set the border style for the workbook
			pass.setBorderBottom(BorderStyle.THIN);
			pass.setBorderLeft(BorderStyle.THIN);
			pass.setBorderRight(BorderStyle.THIN);
			pass.setBorderTop(BorderStyle.THIN);
			pass.setAlignment(HorizontalAlignment.LEFT);

			skip.setFillForegroundColor(goldColor);
			skip.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// Set the border style for the workbook
			skip.setBorderBottom(BorderStyle.THIN);
			skip.setBorderLeft(BorderStyle.THIN);
			skip.setBorderRight(BorderStyle.THIN);
			skip.setBorderTop(BorderStyle.THIN);
			skip.setAlignment(HorizontalAlignment.LEFT);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);

			DataFormatter formatter = new DataFormatter();
			for (Iterator<?> iterator = sheet.rowIterator(); iterator.hasNext();) {
				XSSFRow row = (XSSFRow) iterator.next();
				for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
					XSSFCell cell = row.getCell(i);
					if (formatter.formatCellValue(cell).equals(colName.trim())) {
						colNum = i;
						break;
					}
				}
				if (colNum > 0) {
					break;
				}
			}

			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum);
			if (row == null)
				row = sheet.createRow(rowNum);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			XSSFCellStyle TableContentsOnLeft = workbook.createCellStyle();
			// Set the Font details for the entire sheet
			XSSFFont defaultFont;
			defaultFont = workbook.createFont();
			defaultFont.setFontHeightInPoints((short) 11);
			defaultFont.setFontName("Calibri");
			defaultFont.setColor(IndexedColors.BLACK.getIndex());
			defaultFont.setBold(true);
			defaultFont.setItalic(false);

			// create style for cells in table contents
			TableContentsOnLeft.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			TableContentsOnLeft.setFillPattern(FillPatternType.NO_FILL);
			// TableContentsOnLeft.setWrapText(false);
			// Set the border style for the workbook
			TableContentsOnLeft.setBorderBottom(BorderStyle.THIN);
			TableContentsOnLeft.setBorderLeft(BorderStyle.THIN);
			TableContentsOnLeft.setBorderRight(BorderStyle.THIN);
			TableContentsOnLeft.setBorderTop(BorderStyle.THIN);
			TableContentsOnLeft.setAlignment(HorizontalAlignment.LEFT);
			switch (colName) {
			case "Execution Status":
				switch (data) {
				case "PASSED":
					cell.setCellStyle(pass);
					break;
				case "FAILED":
					cell.setCellStyle(fail);
					break;
				case "NOT EXECUTED":
					cell.setCellStyle(skip);
					break;
				}
				break;

			default:
				cell.setCellStyle(TableContentsOnLeft);
			}

			cell.setCellValue(data);
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

			fileOut.close();
		} catch (Exception e) {
			Reporter.log("File IO Exception in XLS_REadWrite.java" + e.toString());
			return false;
		}
		return true;
	}

	/**
	 * Function to set the data for a value from a test case
	 * 
	 * @author saikiran.nataraja
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public boolean setCellDataFromTestCase(String sheetName, String colName, String TestcaseName, String data) throws Exception {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int rowNum = 0;
			for (int i = 2; i <= Const.getTCXLS().getRowCount(sheetName); i++) {
				if (Const.getTCXLS().getCellData(sheetName, Address.TESTCASES_TESTCASEID, i)
						.equalsIgnoreCase(TestcaseName)) {
					rowNum = i + 1; // Pass the Header Row Number for that test
					// case so that it can get the required
					// header
					break;
				}
			}

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1); // Get the Header Row Name as per
			// test case
			// System.out.println("Column Count
			// is:"+getColCountForParticularRow(sheetName,rowNum));
			for (int i = 0; i < getColCountForParticularRow(sheetName, rowNum); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (!(row.getCell(i).getStringCellValue().isEmpty())) {
					if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
						colNum = i;
						break;
					}
				}
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum);// Get the Value of Header Row passed as
			// per test case
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			//Set the default cell format as text
			cs.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text")); //Added to make all formatting as text
			cell.setCellStyle(cs);
			cell.setCellValue(data);

			/*
			 * //History Management - To know the previously used test data
			 * CellStyle PrevCs = workbook.createCellStyle();
			 * PrevCs.setWrapText(true);
			 * PrevCs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.
			 * getIndex()); PrevCs.setFillPattern(CellStyle.SOLID_FOREGROUND);
			 * rowNum=rowNum+1; row=sheet.getRow(rowNum); HistoryCell =
			 * row.getCell(colNum); if(HistoryCell==null)
			 * HistoryCell=row.createCell(colNum);
			 * HistoryCell.setCellStyle(PrevCs);
			 * HistoryCell.setCellValue("Previously Used "+colName+"is: "+data);
			 */
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			fis.close();
		} catch (Exception e) {
			Reporter.log("File IO Exception in XLS_REadWrite.java" + e.toString());
			throw e;
		}
		return true;
	}

	// find whether sheets exists
	/**
	 * @param sheetName
	 * @return
	 */
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	// returns number of columns in a sheet
	/**
	 * @param sheetName
	 * @return
	 */
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();	// return row.getPhysicalNumberOfCells();
	}
}
