package com.labproject.SkyTracker.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {


    @KafkaListener(topics= "SkyTrackerController",groupId = "groud_id")
    void consume(String SkyTrackerControllerRecord){
        try{
            Double vel = Double.parseDouble(SkyTrackerControllerRecord);
            System.out.println("Velocity: " + SkyTrackerControllerRecord);
            if(vel > 50 || vel < 10){
                System.out.println("WARNING: Out of velocity range");
            }
        }catch (Exception e){
            System.out.println(SkyTrackerControllerRecord);
        }
    }
}