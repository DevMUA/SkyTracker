package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.Kafka.KafkaController;
import com.labproject.SkyTracker.OpenSky.OpenSkyController;
import com.labproject.SkyTracker.OpenSky.Plane;
import com.labproject.SkyTracker.OpenSky.PlaneToTrack;
import com.labproject.SkyTracker.OpenSky.SnapShots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@EnableScheduling
public class SkyTrackerController {

    private static final Logger logger  = LoggerFactory.getLogger(SkyTrackerController.class);
    private static final String TOPIC = "SkyTrackerController";

    @Autowired
    private KafkaController kafkaController;

    @Autowired
    private final OpenSkyController openSkyController;

    private final SkyTrackerService skyTrackerService;

    @Autowired
    private KafkaTemplate<String, Float> kafkaTemplate;

    @Autowired
    public SkyTrackerController( SkyTrackerService skyTrackerService, OpenSkyController openSkyController,KafkaController kafkaController){
        this.skyTrackerService = skyTrackerService;
        this.openSkyController = openSkyController;
        this.kafkaController = kafkaController;
    }

    @GetMapping("/api/v1/test")
    public List<Plane> getAllPlanesCall(){
        return openSkyController.getPlanes("lamin=45.8389&lomin=5.9962&lamax=47.8229&lomax=10.5226");
    }

    @GetMapping("/api/v1/planes")
    public List<Plane> getAllPlanesFromDatabase(){
        logAndSendMessage("Received call to get planes list from DATABASE");
        return skyTrackerService.GetPlanes();
    }

    @GetMapping("/api/v1/planes/{id}")
    public List<Plane> getPlane(@PathVariable String id){
        String Query = "icao24=" + id;
        List<Plane> planeList = openSkyController.getPlanes(Query);
//        for(Plane p: planeList){
//               kafkaTemplate.send(p.getIcao24(), p.getVelocity());
//        }
        return planeList;
    }

    @PostMapping("/api/v1/planes/track/{id}")
    public void trackPlane(@PathVariable String id){
        logAndSendMessage("Got a request to track one plane with id: " + id);
        PlaneToTrack newPlane = new PlaneToTrack();
        newPlane.convertPlane(skyTrackerService.GetPlane(id));
        logAndSendMessage("Adding Track plane with id " + id + " to Tracking Plane Database");
        //Try to add to database
        try {
            skyTrackerService.addNewTrackingPlane(newPlane);
            System.out.println("added to database");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/api/v1/planes/track/{id}")
    public PlaneToTrack getTrackedPlane(@PathVariable String id){
        if(skyTrackerService.isTrackingPlanePresent(id))
            return skyTrackerService.GetTrackingPlane(id);
        else
            return null;
    }

//    @GetMapping("/api/v1/kafka/track/{id}")
//    @KafkaListener(topics = "SkyTrackerController", groupId = "group_1")
//    public String underVelLimit(@PathVariable String id){
//        PlaneToTrack p = skyTrackerService.GetTrackingPlane(id);
//        if(p.getVelocity() > 200) return "true";
//        else if(p.getVelocity() < 100 && p.getVelocity() < 0) return "true";
//        else return "false";
//    }


    @GetMapping("/api/v1/planes/track/snapshots/{id}")
    public List<SnapShots> getPlaneToTrackSnapShots(@PathVariable String id){
        if(skyTrackerService.isTrackingPlanePresent(id)) {
            Long idToUse = skyTrackerService.GetTrackingPlane(id).getId();

            //Try fetching the list
            List<SnapShots> snapShots = null;
            try {
                snapShots = skyTrackerService.getSnapShotsByPlaneID(idToUse);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return snapShots;
        }
        else{
            List<SnapShots> s = new LinkedList<SnapShots>();
            return s;
        }
    }


    @Scheduled(fixedRate = 30000L)
    private void updateAllDatabasePlanes(){
        logAndSendMessage("Updating General Planes Database");
        logAndSendMessage("Calling API");
        List<Plane> planeList = getAllPlanesCall();
        logAndSendMessage("API call finished");
        for(int i = 0; i< planeList.size(); i++){
            //Try to add to database
            try {
                if(skyTrackerService.isPlaneInDatabase(planeList.get(i))){
                    skyTrackerService.updatePlane(planeList.get(i));
                }
                else {
                    skyTrackerService.addNewPlane(planeList.get(i));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        logAndSendMessage("Finished updating General Planes Database " + planeList.size() + " from API call " + skyTrackerService.GetPlanes().size() + " database size");
    }
    @Scheduled(fixedRate = 10000L)
    private void updateAllTrackingDatabasePlanes(){

        //Get Current Planes that are being tracked in the database
        List<PlaneToTrack> planeToTrackList = skyTrackerService.GetTrackingPlanes();

        logAndSendMessage("Updating Tracking Planes Database with size " + planeToTrackList.size());

        //Iterate through every plane and do an API call for it
        for(int i = 0; i< planeToTrackList.size(); i++){

            //Create PlaneToTrack obj with all NULL values
            PlaneToTrack tmp = new PlaneToTrack();
            //Call openSkyAPI for new plane information and convert it to PlaneToTrack
            tmp.convertPlane(getPlane(planeToTrackList.get(i).getIcao24()).get(0));
            //Copy ID over so it updates the right entry on the database
            tmp.setId(planeToTrackList.get(i).getId());

            SnapShots newSnapShot = new SnapShots(tmp.getLongitude(),tmp.getLatitude(),tmp.getVelocity());
            logAndSendMessage("Adding new snapshot for plane with id: " + tmp.getIcao24());
            //Try to add to SnapShot database
            try {
                skyTrackerService.addSnapShotEntry(newSnapShot, tmp.getId());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            logAndSendMessage("Successfully added snapshot for plane with id: " + tmp.getIcao24());

            //Try to add or update if already exists to PlaneToTrack database
            try {
                if(skyTrackerService.isPlaneInTrackingDatabase(tmp)){
                    skyTrackerService.updateTrackingPlane(tmp);
                }
                else {
                    skyTrackerService.addNewTrackingPlane(tmp);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        logAndSendMessage("Finished updating Tracking Planes Database ");
    }

    private void logAndSendMessage(String message){
        logger.info(String.format("************* SkyTrackerController : %s",message));
        kafkaController.sendMessage(TOPIC,message);
    }
}
