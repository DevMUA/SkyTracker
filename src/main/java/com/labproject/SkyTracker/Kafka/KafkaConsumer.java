package com.labproject.SkyTracker.Kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {


    @KafkaListener(topics= "SkyTrackerController",groupId = "groud_id")
    void consume(String SkyTrackerControllerRecord){
        System.out.println("received a message " + SkyTrackerControllerRecord);
    }
}
