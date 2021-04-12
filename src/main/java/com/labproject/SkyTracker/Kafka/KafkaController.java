package com.labproject.SkyTracker.Kafka;

import com.labproject.SkyTracker.OpenSky.PlaneToTrack;
import com.labproject.SkyTracker.SkyTrackerAPI.SkyTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class KafkaController {

    private static final String TOPIC = "SkyTrackerController";

    @Autowired
    private SkyTrackerService skyTrackerService;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaController() {
    }

    @GetMapping("/api/v1/kafkaProducer/track/{id}")
    public String kafkaTrack(@PathVariable String id){
        PlaneToTrack p = skyTrackerService.GetTrackingPlane(id);
        if(p != null){
            kafkaTemplate.send(TOPIC, "100");
        }
        System.out.println("RECEIVED A CALL TO KAFKA-------------------");
        return "Published Sucessfully";
    }

    @GetMapping("/api/v1/kafkaConsumer/track/{id}")
    @KafkaListener(topics = "SkyTrackerController", groupId = "group_1")
    public String underVelLimit(@PathVariable String id){
        PlaneToTrack p = skyTrackerService.GetTrackingPlane(id);
        if(p.getVelocity() > 200) return "true";
        else if(p.getVelocity() < 100 && p.getVelocity() < 0) return "true";
        else return "false";
    }
}
