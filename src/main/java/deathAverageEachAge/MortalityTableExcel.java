package deathAverageEachAge;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MortalityTableExcel {
	InputStream ExcelFileToRead;
	XSSFWorkbook wb;

	public MortalityTableExcel() throws IOException {
		ExcelFileToRead = new FileInputStream("src\\main\\resources\\Generationssterbetafeln.xlsx");
		wb = new XSSFWorkbook(ExcelFileToRead);
	}

	public List<RowData> getRowDataList(String sheetName) {
		XSSFSheet sheet = wb.getSheet(sheetName);
		List<RowData> rowDatas = new ArrayList<RowData>();

		int age = 0; // RowDatas age

		double livingPeople = 100000; // Living people at the beginning of
		double deadPeople = 0;

		for (Iterator<Row> rows = sheet.rowIterator(); rows.hasNext();) {
			XSSFRow row = (XSSFRow) rows.next();

			int cellNum = 0; // Access to column of dead people
			for (Iterator<Cell> cells = row.cellIterator(); cells.hasNext();) {
				XSSFCell cell = (XSSFCell) cells.next();

				if (cellNum == 4 && datacell(row.getRowNum()) && cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					double diedPeople = cell.getNumericCellValue();
					livingPeople = livingPeople - diedPeople;
					deadPeople = deadPeople + diedPeople;

					rowDatas.add(new RowData(age, (int) Math.round(livingPeople), (int) Math.round(diedPeople),
							livingPeople / 1000, deadPeople / 1000));

					age++;
				}
				cellNum++;
			}
		}
		return rowDatas;
	}

	private boolean datacell(int rowNum) {
		if (rowNum > 6 && rowNum < 57) {
			return true;
		} else if (rowNum > 64 && rowNum < 116) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		MortalityTableExcel get;
		try {
			get = new MortalityTableExcel();
			get.getRowDataList("1896_M_V1");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
