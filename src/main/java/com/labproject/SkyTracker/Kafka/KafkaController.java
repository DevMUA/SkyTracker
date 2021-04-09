package com.labproject.SkyTracker.Kafka;

import com.labproject.SkyTracker.SkyTrackerAPI.SkyTrackerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaController {

    private final SkyTrackerController producer;

    @Autowired
    private static KafkaTemplate kafkaTemplate;

    public KafkaController(SkyTrackerController producer) {
        this.producer = producer;
    }

    public static void sendMessage(String TOPIC,String message){
        kafkaTemplate.send(TOPIC,message);
    }
}
