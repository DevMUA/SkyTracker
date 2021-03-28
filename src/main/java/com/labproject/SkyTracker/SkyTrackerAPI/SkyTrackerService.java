package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.OpenSky.Plane;
import com.labproject.SkyTracker.OpenSky.PlaneToTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkyTrackerService {

    private final SkyTrackerRepository planeRepository;

    private final SkyTrackerPlanesToTrackRepository planesToTrackRepository;

    @Autowired
    public SkyTrackerService(SkyTrackerRepository planeRepository, SkyTrackerPlanesToTrackRepository planesToTrackRepository) {
        this.planeRepository = planeRepository;
        this.planesToTrackRepository = planesToTrackRepository;
    }

    public List<Plane> GetPlanes() {
        return planeRepository.findAll();
    }

    public List<PlaneToTrack> GetTrackingPlanes() {
        return planesToTrackRepository.findAll();
    }

    public PlaneToTrack GetTrackingPlane(String icao24) {
        return planesToTrackRepository.findPlaneById(icao24).get();
    }

    public Plane GetPlane(String icao24) {
        return planeRepository.findPlaneById(icao24).get();
    }

    public boolean isPlaneInDatabase(Plane plane){
        Optional<Plane> planeById = planeRepository.findPlaneById(plane.getIcao24());
        if(planeById.isPresent()){
            return true;
        }
        return false;
    }

    public boolean isPlaneInTrackingDatabase(PlaneToTrack plane){
        Optional<PlaneToTrack> planeById = planesToTrackRepository.findPlaneById(plane.getIcao24());
        if(planeById.isPresent()){
            return true;
        }
        return false;
    }

    public void addNewTrackingPlane(PlaneToTrack p) throws IllegalAccessException {
        Optional<PlaneToTrack> planeById = planesToTrackRepository.findPlaneById(p.getIcao24());
        if(planeById.isPresent()){
            throw new IllegalAccessException("plane already in repository");
        }
        planesToTrackRepository.save(p);
        System.out.println(p);
    }

    public void updateTrackingPlane(PlaneToTrack plane) throws IllegalAccessException {
        Optional<PlaneToTrack> planeById = planesToTrackRepository.findPlaneById(plane.getIcao24());
        if(!planeById.isPresent()){
            throw new IllegalAccessException("plane not in repository");
        }
        PlaneToTrack newPlane = new PlaneToTrack(planeById.get().getId(),plane.getIcao24()
                ,plane.getCallsign(),plane.getOrigin_country(),plane.getTime_position(),plane.getLast_contact()
                ,plane.getLongitude(),plane.getLatitude(),plane.getBaro_altitude(),plane.isOn_ground(),plane.getVelocity()
                ,plane.getTrue_track(),plane.getVertical_rate(),plane.getSensors(),plane.getGeo_altitude(),plane.getSquawk()
                ,plane.isSpi(),plane.getPosition_source());
        planesToTrackRepository.save(newPlane);
    }

    public void addNewPlane(Plane plane) throws IllegalAccessException {
        Optional<Plane> planeById = planeRepository.findPlaneById(plane.getIcao24());
        if(planeById.isPresent()){
            throw new IllegalAccessException("plane already in repository");
        }
        planeRepository.save(plane);
        System.out.println(plane);
    }

    public void updatePlane(Plane plane) throws IllegalAccessException {
        Optional<Plane> planeById = planeRepository.findPlaneById(plane.getIcao24());
        if(!planeById.isPresent()){
            throw new IllegalAccessException("plane not in repository");
        }
        Plane newPlane = new Plane(planeById.get().getId(),plane.getIcao24()
        ,plane.getCallsign(),plane.getOrigin_country(),plane.getTime_position(),plane.getLast_contact()
        ,plane.getLongitude(),plane.getLatitude(),plane.getBaro_altitude(),plane.isOn_ground(),plane.getVelocity()
                ,plane.getTrue_track(),plane.getVertical_rate(),plane.getSensors(),plane.getGeo_altitude(),plane.getSquawk()
        ,plane.isSpi(),plane.getPosition_source());

        planeRepository.save(newPlane);
        System.out.println(plane);
    }
}
