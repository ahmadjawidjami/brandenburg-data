package de.tu_berlin.open_data.airquality.brandenburgairqualitydata;

import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirData;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

/**
 * This class demonstrates how we can implement a row mapper that maps
 * a row found from an Excel document into a {@code StudentDTO} object. If
 * the Excel document has no header, we have to use this method for transforming
 * the input data into {@code StudentDTO} objects.
 *
 * @author Petri Kainulainen
 */
public class AirDataExcelRowMapper implements RowMapper<AirData> {

    @Override
    public AirData mapRow(RowSet rowSet) throws Exception {

        AirData airData = new AirData();

//        for (String current: rowSet.getCurrentRow()){
//            System.out.println(current);
//            System.out.println("this is it");
//        }

        airData.setMeasurementLocation(rowSet.getColumnValue(0));
        airData.setNO2DailyAverage(rowSet.getColumnValue(1));
        airData.setNO2Max1hAverage(rowSet.getColumnValue(2));
        airData.setNODailyAverage(rowSet.getColumnValue(3));
        airData.setNOMax1hAverage(rowSet.getColumnValue(4));
        airData.setCODailyAverage(rowSet.getColumnValue(5));
        airData.setCOMax8hAverage(rowSet.getColumnValue(6));
        airData.setFineDustPM10DailyAverage(rowSet.getColumnValue(7));
        airData.setFineDustPM10Max1hAverage(rowSet.getColumnValue(8));
        airData.setFineDustPM25DailyAverage(rowSet.getColumnValue(9));
        airData.setFineDustPM25Max1hAverage(rowSet.getColumnValue(10));
        airData.setSO2DailyAverage(rowSet.getColumnValue(11));
        airData.setSO2Max1hAverage(rowSet.getColumnValue(12));
        airData.setO3DailyAverage(rowSet.getColumnValue(13));
        airData.setO3Max8hAverage(rowSet.getColumnValue(14));

        return airData;
    }
}
