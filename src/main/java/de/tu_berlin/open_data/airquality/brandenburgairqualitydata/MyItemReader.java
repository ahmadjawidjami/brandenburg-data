package de.tu_berlin.open_data.airquality.brandenburgairqualitydata;

import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.excel.AbstractExcelItemReader;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.core.io.Resource;

import java.io.*;

/**
 * Created by ahmadjawid on 6/30/17.
 */
public class MyItemReader extends AbstractExcelItemReader<AirData> {


    private HSSFWorkbook workbook;
    private InputStream workbookStream;



    protected Sheet getSheet(int sheet) {
        return (Sheet) new MyPoiSheet(this.workbook.getSheetAt(sheet));
    }

    protected int getNumberOfSheets() {
        return this.workbook.getNumberOfSheets();
    }

    protected void doClose() throws Exception {
        if(this.workbook instanceof Closeable) {
            this.workbook.close();
        }

        if(this.workbookStream != null) {
            this.workbookStream.close();
        }

        this.workbook = null;
        this.workbookStream = null;
    }

    protected void openExcelFile(Resource resource) throws Exception {

        FileInputStream fis = new FileInputStream(new File("Montag.xls"));
 HSSFWorkbook wb = new HSSFWorkbook(fis);

      //  this.workbookStream = resource.getInputStream();
       // if(!this.workbookStream.markSupported() && !(this.workbookStream instanceof PushbackInputStream)) {
      //      throw new IllegalStateException("InputStream MUST either support mark/reset, or be wrapped as a PushbackInputStream");
     //   } else {
            this.workbook = (HSSFWorkbook) WorkbookFactory.create(new File("Montag-work.xls"));
            //this.workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
     //  }
    }
}
