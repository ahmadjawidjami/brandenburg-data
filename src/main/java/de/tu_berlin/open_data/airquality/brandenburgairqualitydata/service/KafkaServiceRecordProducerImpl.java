package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/13/17.
 */
@Service
public class KafkaServiceRecordProducerImpl implements KafkaServiceRecordProducer {

    @Autowired
    Producer producer;


    @Override
    public void produce(String jsonObject) {

        //  System.out.println(jsonObject);
        // System.out.print(".");

        producer.send(new ProducerRecord("weatherData", jsonObject));
        // producer.close();

    }
}
