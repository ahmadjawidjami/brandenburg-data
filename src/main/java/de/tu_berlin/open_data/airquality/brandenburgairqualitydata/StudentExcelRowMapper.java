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
public class StudentExcelRowMapper implements RowMapper<AirData> {

    @Override
    public AirData mapRow(RowSet rowSet) throws Exception {
       // StudentDTO student = new StudentDTO();

        AirData airData = new AirData();

        airData.setMeasurementLocation(rowSet.getColumnValue(0));

//        student.setName(rowSet.getColumnValue(0));
//        student.setEmailAddress(rowSet.getColumnValue(1));
//        student.setPurchasedPackage(rowSet.getColumnValue(2));

        return airData;
    }
}
