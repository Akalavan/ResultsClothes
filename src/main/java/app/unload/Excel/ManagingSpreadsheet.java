package app.unload.Excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ManagingSpreadsheet  {

    private static XSSFWorkbook workbook;
    private static File file;

    public static void OpenWorkBook() {
        try {
            file = new File("C:/excel/Учёт выдачи средств индивидуальной защиты.xlsx");
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            if (file.isFile() && file.exists()) {
                System.out.println(".xlsx file open successfully.");
            } else {
                System.out.println("Error to open .xlsx file.");
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void UpdateWorkBook() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        // Табельный номер
        XSSFCell cell = sheet.getRow(0).getCell(5);
        cell.setCellValue("TABLE_№");
        // ФИО
        cell = sheet.getRow(2).getCell(1);
        cell.setCellValue("SecondName");

        cell = sheet.getRow(3).getCell(1);
        cell.setCellValue("Name");

        cell = sheet.getRow(3).getCell(5);
        cell.setCellValue("patronymic");
        // Профессия
        cell = sheet.getRow(6).getCell(3);
        cell.setCellValue("position");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        ManagingSpreadsheet.OpenWorkBook();
        ManagingSpreadsheet.UpdateWorkBook();
    }

}
