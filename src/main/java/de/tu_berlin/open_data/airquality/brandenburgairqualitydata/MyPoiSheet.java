package de.tu_berlin.open_data.airquality.brandenburgairqualitydata;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.batch.item.excel.Sheet;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ahmadjawid on 6/30/17.
 */
public class MyPoiSheet implements Sheet {

    private final org.apache.poi.ss.usermodel.Sheet delegate;
    private final int numberOfRows;
    private final String name;
    private int numberOfColumns = -1;
    private FormulaEvaluator evaluator;

    MyPoiSheet(org.apache.poi.ss.usermodel.Sheet delegate) {
        this.delegate = delegate;
        this.numberOfRows = this.delegate.getLastRowNum() + 1;
        this.name = this.delegate.getSheetName();
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public String getName() {
        return this.name;
    }

    public String[] getRow(int rowNumber) {
        Row row = this.delegate.getRow(rowNumber);
        if(row == null) {
            return null;
        } else {
            List<String> cells = new LinkedList();

            for(int i = 0; i < this.getNumberOfColumns(); ++i) {
                Cell cell = row.getCell(i);
                switch(cell.getCellType()) {
                    case 0:
                        if(DateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            cells.add(String.valueOf(date.getTime()));
                        } else {
                            cells.add(String.valueOf(cell.getNumericCellValue()));
                        }
                        break;
                    case 1:
                    case 3:
                        cells.add(cell.getStringCellValue());
                        break;
                    case 2:
                        cells.add(this.getFormulaEvaluator().evaluate(cell).formatAsString());
                        break;
                    case 4:
                        cells.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    default:
                        throw new IllegalArgumentException("Cannot handle cells of type " + cell.getCellType());
                }
            }

            return (String[])cells.toArray(new String[cells.size()]);
        }
    }

    private FormulaEvaluator getFormulaEvaluator() {
        if(this.evaluator == null) {
            this.evaluator = this.delegate.getWorkbook().getCreationHelper().createFormulaEvaluator();
        }

        return this.evaluator;
    }

    public int getNumberOfColumns() {
        if(this.numberOfColumns < 0) {
            this.numberOfColumns = this.delegate.getRow(0).getLastCellNum();
        }

        return this.numberOfColumns;
    }
}
