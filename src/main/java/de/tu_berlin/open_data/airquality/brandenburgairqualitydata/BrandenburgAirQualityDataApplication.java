package de.tu_berlin.open_data.airquality.brandenburgairqualitydata;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.repository.TaskExecution;

import java.io.*;

@SpringBootApplication
@EnableTask
public class BrandenburgAirQualityDataApplication implements CommandLineRunner {


    public void init() throws IOException {

        ////        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File("Montag.xls");
//			Workbook wb = WorkbookFactory.create(file);
//			Sheet sheet = wb.getSheetAt(0);
//
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(fis);

     //   wb.getSheet("alo");
        HSSFSheet sheet = wb.getSheetAt(0);

        sheet.createRow(0);




//        removeRow(sheet, 0);
//        removeRow(sheet, 0);
//        removeRow(sheet, 0);
       // removeRow(sheet, 0);

        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        sheet.createRow(3);
       // removeRow(sheet, 0);

        Row row;

//        for (int i = 0; i < 1; i++){
//            row = sheet.getRow(i);
//
//            for (int j = 0; j < 15; j++){
//
//                row.getCell(j).setCellType(Cell.CELL_TYPE_BLANK);
//            }
//        }

//        sheet.autoSizeColumn(2);
//        sheet.autoSizeColumn(3);

        int mergedRegions = sheet.getNumMergedRegions();

        while ((mergedRegions = sheet.getNumMergedRegions()) > 0){

            sheet.removeMergedRegion(0);

        }

//        for (int index = 0; index < 15; index++){
//            sheet.removeMergedRegion(index);
//            sheet.removeMergedRegion(index);
//
//        }
//
//        sheet.removeMergedRegion(0);

//        mergedRegions = sheet.getNumMergedRegions();
//
//        for (int i = 0; i <)






//        for (int index = 0; index < 15; index ++){
//            sheet.autoSizeColumn(index, true);
//        }

//		//removeRow(sheet, 0);
////        removeRow(sheet, 1);
////        removeRow(sheet, 2);
//        //removeRow(sheet, 4);
//        //removeRow(sheet, 0);
//		Header header = sheet.getHeader();
//		header.setLeft("this is header");
//		header.setCenter("alo");
//		header.setRight("come");
//        File outWB = new File("Montag11.xls");
//        OutputStream out = new FileOutputStream(outWB);
//        wb.write(out);
//       // fis.close();
//        out.flush();
//        out.close();

//        System.out.println("this is before job\n===========================================\n");
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File("Montag.xls");
//        FileInputStream fis = new FileInputStream(file);
//        HSSFWorkbook wb = new HSSFWorkbook(fis);
//        HSSFSheet sheet = wb.getSheetAt(0);
//        sheet.removeRow(sheet.getRow(2));
//        sheet.removeRow(sheet.getRow(1));
//        sheet.removeRow(sheet.getRow(3));
//        sheet.removeRow(sheet.getRow(4));
//
//      //  sheet.shiftRows(3, 3, -1);
//
        File outWB = new File("Montag-work.xls");
        OutputStream out = new FileOutputStream(outWB);
        fis.close();
        wb.write(out);
        out.flush();
        out.close();


    }

    public static void main(String[] args) throws IOException {
      new BrandenburgAirQualityDataApplication().init();
        SpringApplication.run(BrandenburgAirQualityDataApplication.class, args);
    }


    public void removeRow(Sheet sheet, int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
if (rowIndex == lastRowNum) {
        Row removingRow = sheet.getRow(rowIndex);
        	if (removingRow != null) {
        sheet.removeRow(removingRow);
        }
        }
    }


    @Override
    public void run(String... strings) throws Exception {


    }
}
