package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.batch;


import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.service.KafkaServiceRecordProducer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ahmadjawid on 5/28/17.
 */
public class BMESensorJsonItemWriter implements ItemWriter<String> {

    @Autowired
    KafkaServiceRecordProducer kafkaServiceRecordProducer;


    @Override
    public void write(List<? extends String> items) throws Exception {

        for (String jsonObject : items){
            kafkaServiceRecordProducer.produce(jsonObject);
        }

    }
}
