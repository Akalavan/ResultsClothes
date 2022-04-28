package app.unload.Excel;

import app.MainApp;
import app.objects.print.Clothes;
import app.objects.print.PrintChecklist;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;

public class ManagingSpreadsheet  {

    private static XSSFWorkbook workbook;
    private static File file;
    private PrintChecklist checklist;
    private File path;

    public ManagingSpreadsheet(PrintChecklist checklist) {
        this.checklist = checklist;
        print();
    }

    public void OpenWorkBook() {
        try {
            int countPath = getClass().getResource("").getPath().split("/").length;
            System.out.println(getClass().getResource(""));
            String s = String.valueOf(getClass().getResource(""));
            String[] fullPath = getClass().getResource("").getPath().split("/");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, s);
            alert.showAndWait();
            StringBuilder pathCap = new StringBuilder();
            for (int i = 1; i < fullPath.length - 4; i++) {
                pathCap.append(fullPath[i]);
                pathCap.append("/");
            }
            pathCap.append("cap.xlsx");
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, pathCap.toString());
            alert1.showAndWait();
            file = new File(pathCap.toString());
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

        int i = 17;
        for (Clothes clothes:
             checklist.getClothesList()) {
            // Наименование средств индивидульноай защиты (объединение ячеек)
            sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 5));

            cell = sheet.getRow(i).getCell(0);
            cell.setCellValue(clothes.getNameClothes());
            XSSFCellBorder.BorderSide[] brSid = {XSSFCellBorder.BorderSide.LEFT, XSSFCellBorder.BorderSide.TOP, XSSFCellBorder.BorderSide.RIGHT};
            BorderStyle[] brSty = {BorderStyle.MEDIUM, BorderStyle.THIN, BorderStyle.THIN};
            for (int j = 1; j < 7; j++) {
                cell.setCellStyle(createStyleForBorder(brSid, brSty));
                cell = sheet.getRow(i).getCell(j);
            }
            // Единица измерения
            cell = sheet.getRow(i).getCell(7);
            cell.setCellValue(clothes.getUnit());
            brSid = new XSSFCellBorder.BorderSide[] {XSSFCellBorder.BorderSide.LEFT, XSSFCellBorder.BorderSide.TOP, XSSFCellBorder.BorderSide.RIGHT};
            brSty = new BorderStyle[] {BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN};
            cell.setCellStyle(createStyleForBorder(brSid, brSty));


            // Кол-во на год
            cell = sheet.getRow(i).getCell(8);
            cell.setCellValue(clothes.getQuantity_year());
            brSid = new XSSFCellBorder.BorderSide[] {XSSFCellBorder.BorderSide.RIGHT, XSSFCellBorder.BorderSide.LEFT, XSSFCellBorder.BorderSide.TOP};
            brSty = new BorderStyle[] {BorderStyle.MEDIUM, BorderStyle.THIN, BorderStyle.THIN};
            cell.setCellStyle(createStyleForBorder(brSid, brSty));

            i++;
        }
        XSSFRow row = sheet.getRow(i);
        for (int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(createStyleForBorder(new XSSFCellBorder.BorderSide[]{XSSFCellBorder.BorderSide.TOP}, new BorderStyle[]{BorderStyle.MEDIUM}));
        }
    //    row.setRowStyle(createStyleForBorder(XSSFCellBorder.BorderSide.BOTTOM, BorderStyle.THICK));
       // sheet.getRow(i).setRowStyle(createStyleForBorder(XSSFCellBorder.BorderSide.BOTTOM, BorderStyle.THICK));
        cell = sheet.getRow(17).getCell(6);
        cell.setCellValue(checklist.getDocument().getName());
        cell.setCellStyle(createStyleForBorder(new XSSFCellBorder.BorderSide[]{XSSFCellBorder.BorderSide.TOP}, new BorderStyle[]{BorderStyle.THIN}));
        sheet.addMergedRegion(new CellRangeAddress(17, i - 1, 6, 6));
        XSSFCellStyle style = cell.getCellStyle();
        style.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setCellStyle(style);


        try {
            String name = "Перечень " + checklist.getPosition().getName() + " " + checklist.getSurname() + " " + checklist.getName() + " " + checklist.getPatronymic() +".xlsx";
            file = new File(path.getPath() + "/" + name);
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

    private XSSFCellStyle createStyleForBorder(XSSFCellBorder.BorderSide[] borderSide, BorderStyle[] borderStyle) {
        XSSFCellStyle style = workbook.createCellStyle();
        for (int i = 0; i < borderSide.length; i++) {
            switch (borderSide[i]) {
                case TOP:
                    style.setBorderTop(borderStyle[i]);
                    break;
                case BOTTOM:
                    style.setBorderBottom(borderStyle[i]);
                    break;
                case LEFT:
                    style.setBorderLeft(borderStyle[i]);
                    break;
                case RIGHT:
                    style.setBorderRight(borderStyle[i]);
                    break;
            }
        }
//        switch (borderSide) {
//            case TOP:
//                style.setBorderTop(borderStyle);
//                break;
//            case BOTTOM:
//                style.setBorderBottom(borderStyle);
//                break;
//            case LEFT:
//                style.setBorderLeft(borderStyle);
//                break;
//            case RIGHT:
//                style.setBorderRight(borderStyle);
//                break;
//        }
        style.setWrapText(true);
        return style;
    }

    static void test() {
        try {
            file = new File("cap.xlsx");
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            if (file.isFile() && file.exists()) {
                System.out.println(".xlsx file open successfully.");
            } else {
                System.out.println("Error to open .xlsx file.");
            }
            inputStream.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Не открыли");
            alert.showAndWait();
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
        if (path == null) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Выберите папку");
            String patchDesktop = System.getProperty("user.home") + "/" + "Desktop";
            directoryChooser.setInitialDirectory(new File(patchDesktop));
            path = directoryChooser.showDialog(MainApp.getPrimaryStage());

        }
        if (path != null)
            UpdateWorkBook();
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Не выбрана папка");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
      //  ManagingSpreadsheet.test();
    }

}
