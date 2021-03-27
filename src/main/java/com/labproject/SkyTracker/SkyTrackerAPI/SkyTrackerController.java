package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.OpenSky.OpenSkyController;
import com.labproject.SkyTracker.OpenSky.Plane;
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

    @GetMapping("/api/v1/planes")
    public List<Plane> getAllPlanesCall(){
        return openSkyController.getPlanes("lamin=45.8389&lomin=5.9962&lamax=47.8229&lomax=10.5226");
    }

    @GetMapping("/api/v1/test")
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
        String Query = "icao24=" + id;
        List<Plane> planeList = openSkyController.getPlanes(Query);

        //Try to add to database
        try {
            skyTrackerService.AddNewPlane(planeList.get(0));
            System.out.println("added to database");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Scheduled(fixedRate = 10000L)
    private void updateAllDatabasePlanes(){
        List<Plane> planesInDatabase = skyTrackerService.GetPlanes();
        System.out.println("Updating database: " + planesInDatabase.size() + " entries to update");
        for(int i = 0; i < planesInDatabase.size(); i ++){
            System.out.print("Calling API to update");
            //Call OpenSkyAPI for plane
            Plane p = getPlane(planesInDatabase.get(i).getIcao24()).get(0);

            //Try to update plane
            try {
                skyTrackerService.updatePlane(p);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            System.out.println("Updated entry");
        }
    }
}
