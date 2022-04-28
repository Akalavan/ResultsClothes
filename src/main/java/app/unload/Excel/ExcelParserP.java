package app.unload.Excel;

import app.ConnectDataBase;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelParserP {


    public static String parse(String fileName) {

        String result = "";
        InputStream inputStream = null;
        HSSFWorkbook workbook = null;

        try {
            inputStream = new FileInputStream(fileName);
            workbook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Sheet sheet = workbook.getSheetAt(1);
        Iterator<Row> it = sheet.iterator();

        while (it.hasNext()) {

            Row row = it.next();
            Iterator<Cell> cells = row.iterator();

            while (cells.hasNext()) {
                Cell cell = cells.next();
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case STRING:
                        result += "INSERT INTO POSITION (NAME) VALUES ('" + cell.getStringCellValue().trim() + "'); ";
                        break;

                }
            }
            result += "\n";
        }

        return result;

    }

    public static void main(String[] args) {
        System.out.println(parse("C:/Java/ResultsClothesV0.5/src/main/java/app/unload/Excel/db.xls"));
    }


}
