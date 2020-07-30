package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Chethan
 *
 */
public class ExcelUtil {

	private static XSSFSheet excelWSheet;
	private static XSSFWorkbook excelWBook;
	private static XSSFCell cell;
	private static XSSFRow row;
	private static String path;

	/**
	 * This method is to set the File path and to open the Excel file, Pass Excel
	 * @param filename Excel filename
	 * @param sheetName Excel sheetname
	 * @throws IOException
	 */
	public static void setExcelFile(String filename, String sheetName) throws IOException {
		path = System.getProperty("user.dir") + "\\Resources\\" + filename;
		FileInputStream excelFile = new FileInputStream(path);
		excelWBook = new XSSFWorkbook(excelFile);
		excelWSheet = excelWBook.getSheet(sheetName);
	}

	/**
	 * This method is to read the test data from the Excel cell
	 * @param RowNum Excel row number to read
	 * @param ColNum Excel column number to read
	 * @return Cell value
	 * @throws Exception
	 */
	public static String getCellData(int RowNum, int ColNum) throws Exception {
		cell = excelWSheet.getRow(RowNum).getCell(ColNum);
		String CellData = cell.getStringCellValue();
		return CellData;
	}

	/**
	 * This method is to write in the Excel cell
	 * @param Value To write in the Excel cell
	 * @param RowNum Row number of Excel
	 * @param ColNum Column number of Excel
	 * @throws IOException
	 */
	public static void setCellData(String Value, int RowNum, int ColNum) throws IOException {
		row = excelWSheet.getRow(RowNum);
		cell = row.getCell(ColNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);

		if (cell == null) {
			cell = row.createCell(ColNum);
			cell.setCellValue(Value);
		} else {
			cell.setCellValue(Value);
		}

		FileOutputStream fileOut = new FileOutputStream(path);
		excelWBook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}
