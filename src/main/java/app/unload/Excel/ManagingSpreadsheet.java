package app.unload.Excel;

import app.objects.print.Clothes;
import app.objects.print.PrintChecklist;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import java.awt.*;
import java.io.*;

public class ManagingSpreadsheet  {

    private static XSSFWorkbook workbook;
    private static File file;
    private PrintChecklist checklist;

    public ManagingSpreadsheet(PrintChecklist checklist) {
        this.checklist = checklist;
        print();
    }

    public void OpenWorkBook() {
        try {
            file = new File("C:/java/ResultsClothes/cap.xlsx");
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

    public void UpdateWorkBook() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        // Табельный номер
        XSSFCell cell = sheet.getRow(0).getCell(5);
        cell.setCellValue(checklist.getTableNumber());
        // ФИО
        cell = sheet.getRow(2).getCell(1);
        cell.setCellValue(checklist.getSurname());

        cell = sheet.getRow(3).getCell(1);
        cell.setCellValue(checklist.getName());

        cell = sheet.getRow(3).getCell(5);
        cell.setCellValue(checklist.getPatronymic());
        // Профессия
        cell = sheet.getRow(6).getCell(3);
        cell.setCellValue(checklist.getPosition().getName());

        // Пункт типовых отраслевых норм (объединение ячеек)
       // sheet.addMergedRegion(new CellRangeAddress(17, sheet.getLastRowNum(), 5, 5));

        // Пункт типовых отраслевых норм
        cell = sheet.getRow(17).getCell(5);
        cell.setCellValue("checklist.getDocument().getName()");

        XSSFCell cellQuantityYear;
        int i = 17;
        for (Clothes clothes:
             checklist.getClothesList()) {
            // Наименование средств индивидульноай защиты (объединение ячеек)
            //sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 4));

            cell = sheet.getRow(i).getCell(0);
            cell.setCellValue(clothes.getNameClothes());
            cell.setCellStyle(createStyleForBorder(XSSFCellBorder.BorderSide.LEFT, BorderStyle.THICK));

            // Единица измерения
            cell = sheet.getRow(i).getCell(6);
            cell.setCellValue(clothes.getUnit());


            // Кол-во на год
            cell = sheet.getRow(i).getCell(7);
            cell.setCellValue(clothes.getQuantity_year());
            cell.setCellStyle(createStyleForBorder(XSSFCellBorder.BorderSide.RIGHT, BorderStyle.THICK));

            i++;
        }
        XSSFRow row = sheet.getRow(i);
        row.setRowStyle(createStyleForBorder(XSSFCellBorder.BorderSide.BOTTOM, BorderStyle.THICK));
       // sheet.getRow(i).setRowStyle(createStyleForBorder(XSSFCellBorder.BorderSide.BOTTOM, BorderStyle.THICK));

        try {
            String name = "Перечень " + checklist.getPosition().getName() + " " + checklist.getSurname() + " " + checklist.getName() + " " + checklist.getPatronymic() +".xlsx";
            file = new File("C:/excel/" + name);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            Desktop desktop = null;
            if (Desktop.isDesktopSupported())
                desktop = Desktop.getDesktop();
            desktop.open(file);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private XSSFCellStyle createStyleForBorder(XSSFCellBorder.BorderSide borderSide, BorderStyle borderStyle) {
        XSSFCellStyle style = workbook.createCellStyle();
        switch (borderSide) {
            case TOP:
                style.setBorderTop(borderStyle);
                break;
            case BOTTOM:
                style.setBorderBottom(borderStyle);
                break;
            case LEFT:
                style.setBorderLeft(borderStyle);
                break;
            case RIGHT:
                style.setBorderRight(borderStyle);
                break;
        }
        return style;
    }

    static void test() {

        try {
            file = new File("C:/java/ResultsClothes/cap.xlsx");
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


        XSSFSheet sheet = workbook.getSheetAt(0);
      //  sheet.addMergedRegion(new CellRangeAddress(17, sheet.getLastRowNum(), 5, 5));
       // sheet.addMergedRegion(new CellRangeAddress(17, 17, 0, 4));

        XSSFCell cellQuantityYear = sheet.getRow(18).getCell(6);

        System.out.println(cellQuantityYear.getStringCellValue());
//        for (int i = 19;  i < sheet.getLastRowNum(); i++) {
//            sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 4));
//        }
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream(file);
//            workbook.write(outputStream);
//            Desktop desktop = null;
//            if (Desktop.isDesktopSupported())
//                desktop = Desktop.getDesktop();
//            desktop.open(file);
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void print() {
        OpenWorkBook();
        UpdateWorkBook();
    }

    public static void main(String[] args) {
      //  ManagingSpreadsheet.test();
    }

}
