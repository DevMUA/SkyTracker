package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.OpenSky.OpenSkyController;
import com.labproject.SkyTracker.OpenSky.Plane;
import com.labproject.SkyTracker.OpenSky.PlaneToTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@EnableScheduling
public class SkyTrackerController {

    @Autowired
    private final OpenSkyController openSkyController;

    private final SkyTrackerService skyTrackerService;

    @Autowired
    public SkyTrackerController( SkyTrackerService skyTrackerService, OpenSkyController openSkyController){ this.skyTrackerService = skyTrackerService; this.openSkyController = openSkyController; }

    private List<Plane> getAllPlanesCall(){
        return openSkyController.getPlanes("lamin=45.8389&lomin=5.9962&lamax=47.8229&lomax=10.5226");
    }


    @GetMapping("/api/v1/planes")
    public List<Plane> getAllPlanesFromDatabase(){
        return skyTrackerService.GetPlanes();
    }

    @GetMapping("/api/v1/planes/{id}")
    public List<Plane> getPlane(@PathVariable String id){
        String Query = "icao24=" + id;
        List<Plane> planeList = openSkyController.getPlanes(Query);
        return planeList;
    }

    @PostMapping("/api/v1/planes/track/{id}")
    public void trackPlane(@PathVariable String id){
        PlaneToTrack newPlane = new PlaneToTrack();
        newPlane.convertPlane(skyTrackerService.GetPlane(id));
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
        return skyTrackerService.GetTrackingPlane(id);
    }

    @Scheduled(fixedRate = 30000L)
    private void updateAllDatabasePlanes(){
        System.out.println("Calling API");
        List<Plane> planeList = getAllPlanesCall();
        System.out.println("Updating Database");
        for(int i = 0; i< planeList.size(); i++){
            //Try to add to database
            try {
                if(skyTrackerService.isPlaneInDatabase(planeList.get(i))){
                    skyTrackerService.updatePlane(planeList.get(i));
                    System.out.println("update database entry : " + i);
                }
                else {
                    skyTrackerService.addNewPlane(planeList.get(i));
                    System.out.println("added to database");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    @Scheduled(fixedRate = 5000L)
    private void updateAllTrackingDatabasePlanes(){

        //Get Current Planes that are being tracked in the database
        List<PlaneToTrack> planeToTrackList = skyTrackerService.GetTrackingPlanes();

        System.out.println("Updating Database");
        System.out.println("Size is " + planeToTrackList.size());

        //Iterate through every plane and do an API call for it
        for(int i = 0; i< planeToTrackList.size(); i++){
            System.out.println("Calling API");

            //Create PlaneToTrack obj with all NULL values
            PlaneToTrack tmp = new PlaneToTrack();
            //Call openSkyAPI for new plane information and convert it to PlaneToTrack
            tmp.convertPlane(getPlane(planeToTrackList.get(i).getIcao24()).get(0));
            //Copy ID over so it updates the right entry on the database
            tmp.setId(planeToTrackList.get(i).getId());

            //Try to add to database
            try {
                if(skyTrackerService.isPlaneInTrackingDatabase(tmp)){
                    skyTrackerService.updateTrackingPlane(tmp);
                    System.out.println("update database entry : " + i);
                }
                else {
                    skyTrackerService.addNewTrackingPlane(tmp);
                    System.out.println("added to database");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
