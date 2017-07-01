package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.batch;

import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirData;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemCountAware;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.excel.poi.PoiItemReader;

/**
 * Created by ahmadjawid on 7/1/17.
 */
public class MyItemReader extends PoiItemReader<AirData> {


    private ExecutionContext executionContext;
    private StepExecution stepExecution;

    @BeforeStep
    public void saveExecutionContext(StepExecution stepExecution) {
        executionContext = stepExecution.getExecutionContext();
        this.stepExecution = stepExecution;
        System.out.println("got context inside read");
    }

    @Override
    public AirData read() throws Exception, UnexpectedInputException, ParseException {

        AirData airData = super.read();
        try {
            if (airData.getMeasurementLocation().equals(" ") || airData.getMeasurementLocation().equals("")) {
                //System.out.println("inside condition:"+airData.getMeasurementLocation()+":alo:"+airData.getCODailyAverage()+":");
                return null;

            }
        } catch (NullPointerException e) {
            return null;
        }


        // System.out.println("This is:" + airData.getMeasurementLocation()+":the chunk");

//        if (executionContext.containsKey("readerStop"))
//            return null;

        return airData;
    }
}
