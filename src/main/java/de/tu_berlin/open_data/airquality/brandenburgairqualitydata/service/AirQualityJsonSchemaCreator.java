package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirQuality;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/9/17.
 */
@Service
public class AirQualityJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    ApplicationService applicationService;
    @Override
    public String create(Schema schema) {
        AirQuality airQualityItem = (AirQuality) schema;

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        ObjectNode mainObject = nodeFactory.objectNode();

        mainObject.put("source_id", "brandenburg_air_quality_data");
        mainObject.put("device", "Brandenburg Station");
        mainObject.put("timestamp", airQualityItem.getTimestamp().toString());


        ObjectNode firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("lat", applicationService.parseToFloat("0"));
        firstLevelChild.put("lon", applicationService.parseToFloat("0"));

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Nitrogen dioxide (NO₂)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getNO2DailyAverage()));
        firstLevelChild.set("NO2DailyAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "sensor type here");
        secondLevelChild.put("Nitrogen dioxide (NO₂)", applicationService.parseToFloat(airQualityItem.getNO2Max1hAverage()));
        firstLevelChild.set("NO2Max1hAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Nitric oxide (NO)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getNODailyAverage()));
        firstLevelChild.set("NODailyAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Nitric oxide (NO)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getNOMax1hAverage()));
        firstLevelChild.set("NO2Max1hAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Carbon monoxide (CO)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getCODailyAverage()));
        firstLevelChild.set("CODailyAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Carbon monoxide (CO)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getCOMax8hAverage()));
        firstLevelChild.set("COMax8hAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Fine dust (PM10)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getFineDustPM10DailyAverage()));
        firstLevelChild.set("FineDustPM10DailyAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Fine dust (PM10)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getFineDustPM10Max1hAverage()));
        firstLevelChild.set("FineDustPM10Max1hAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Fine dust (PM2.5)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getFineDustPM25DailyAverage()));
        firstLevelChild.set("FineDustPM25DailyAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Fine dust (PM2.5)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getFineDustPM25Max1hAverage()));
        firstLevelChild.set("FineDustPM25Max1hAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Sulfur dioxide (SO₂)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getSO2DailyAverage()));
        firstLevelChild.set("SO2DailyAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Sulfur dioxide (SO₂)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getSO2Max1hAverage()));
        firstLevelChild.set("SO2Max1hAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Ozone (O₃)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getO3DailyAverage()));
        firstLevelChild.set("O3DailyAverage", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", "Ozone (O₃)");
        secondLevelChild.put("observation_value", applicationService.parseToFloat(airQualityItem.getO3Max8hAverage()));
        firstLevelChild.set("O3Max8hAverage", secondLevelChild);


        mainObject.set("sensors", firstLevelChild);


        return mainObject.toString();
    }
}
