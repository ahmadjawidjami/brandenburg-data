package de.tu_berlin.open_data.airquality.brandenburgairqualitydata;

import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

/**
 * This custom {@code ItemProcessor} simply writes the information of the
 * processed student to the log and returns the processed object.
 *
 * @author Petri Kainulainen
 */
public class LoggingStudentProcessor implements ItemProcessor<AirData, AirData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentProcessor.class);

    private StepExecution stepExecution;
    private ExecutionContext executionContext;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
        this.executionContext = stepExecution.getExecutionContext();
        System.out.println("saved step before step");
    }


    @Override
    public AirData process(AirData item) throws Exception {

       // stepExecution.ge




        if (item.getMeasurementLocation() == null){
           // System.out.println("finish");
            executionContext.putInt("readerStop", 1);
        }


        LOGGER.info("Processing student information: {}", item.getMeasurementLocation());
        LOGGER.info("Processing student information: {}", item.getCODailyAverage());
        LOGGER.info("Processing student information: {}", item.getCOMax8hAverage());
        LOGGER.info("Processing student information: {}", item.getFineDustPM10DailyAverage());
        return item;
    }
}
