package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.OpenSky.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkyTrackerService {

    private final SkyTrackerRepository planeRepository;

    @Autowired
    public SkyTrackerService(SkyTrackerRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    public List<Plane> GetPlanes() {
        return planeRepository.findAll();
    }

    public void AddNewPlane(Plane plane) throws IllegalAccessException {
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
