package de.tu_berlin.open_data.airquality.brandenburgairqualitydata;

import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * This custom {@code ItemProcessor} simply writes the information of the
 * processed student to the log and returns the processed object.
 *
 * @author Petri Kainulainen
 */
public class LoggingStudentProcessor implements ItemProcessor<AirData, AirData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentProcessor.class);

    @Override
    public AirData process(AirData item) throws Exception {
        LOGGER.info("Processing student information: {}", item.getMeasurementLocation());
        return item;
    }
}
