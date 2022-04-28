package app.unload.Excel;

import app.ConnectDataBase;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.print.attribute.HashPrintServiceAttributeSet;
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

public class ExcelParser {

    static ConnectDataBase CDB = new ConnectDataBase();
    static Map<String, Integer> positions = new HashMap<>();
    static Map<String, Integer> services = new HashMap<>();

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

        CDB.getStmt();
        ResultSet set = ConnectDataBase.query("SELECT * FROM SERVICE");

        try {
            while (set.next()) {
                services.put(
                        set.getString("NAME"),
                        set.getInt("ID_SERVICE"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        set = ConnectDataBase.query("SELECT * FROM POSITION");

        try {
            while (set.next()) {
                positions.put(
                        set.getString("NAME"),
                        set.getInt("ID_POSITION"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(positions);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();

        while (it.hasNext()) {

            result += "INSERT INTO WORKER (\"TABLE\", ID_SERVICE, SECOND_NAME, FIRST_NAME, SURNAME, DATE_ACCEPTANCE, ID_POSITION) VALUES (";
            //INSERT INTO WORKER (TABLE, ID_SERVICE, SECOND_NAME, FIRST_NAME, SURNAME, DATE_ACCEPTANCE, ID_POSITION)
            //    VALUES (2311802, 3, 'Бусыгин', 'Владимир', 'Иванович', '12.03.2004', 0);

            Row row = it.next();
            Iterator<Cell> cells = row.iterator();

            while (cells.hasNext()) {
                Cell cell = cells.next();
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case STRING:
                        String s = cell.getStringCellValue().trim();
                            if (services.containsKey(s))
                                result += services.get(s) + ", ";
                            else if (positions.containsKey(s))
                                result += positions.get(s);
                            else
                                result += "'" + s + "', ";
                        break;
                    case NUMERIC:
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = cell.getDateCellValue();
                            result += "'" + df.format(date) + "', ";
                        } else {
                            result += "'" + (int) cell.getNumericCellValue() + "', ";
                        }
                        break;

                }
            }
            result += ");\n";
        }

        return result;

    }

    public static void main(String[] args) {
        System.out.println(parse("C:/Java/ResultsClothesV0.5/src/main/java/app/unload/Excel/db.xls"));
    }


}
