package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.batch;


import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.BMESensor;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.service.ApplicationService;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.service.JsonSchemaCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by ahmadjawid on 5/21/17.
 */

public class BMESensorItemProcessor implements ItemProcessor<BMESensor, String> {

    private static final Logger log = LoggerFactory.getLogger(BMESensorItemProcessor.class);

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    @Qualifier("BMESensorJsonSchemaCreator")
    private JsonSchemaCreator jsonSchemaCreator;

    @Override
    public String process(BMESensor item) throws Exception {
        item.setTimestamp(applicationService.toISODateFormat(item.getTimestamp()));
        return jsonSchemaCreator.create(item);
    }

//    @Override
//    public BMESensor process(final BMESensor weatherData) throws Exception {
//
//
//        weatherData.setTimestamp(applicationService.toISODateFormat(weatherData.getTimestamp()));
//
//        return weatherData;
//    }

}
