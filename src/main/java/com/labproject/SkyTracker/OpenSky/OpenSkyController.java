package com.labproject.SkyTracker.OpenSky;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Controller
public class OpenSkyController {

    private String URL = "https://opensky-network.org/api/states/all?";

    private RestTemplate restTemplate;

    public OpenSkyController(){
        this.restTemplate = new RestTemplate();
    }

    public List<Plane> getPlanes(String parameters){
        SkyResponseObject obj = restTemplate.getForObject(URL+parameters,SkyResponseObject.class);

        List<Plane> planes = new LinkedList<Plane>();
        if(obj.getStates() == null){
            List<Plane> nullList = new LinkedList<Plane>();
            return nullList;
        }
        for(int i = 0; i < obj.getStates().length; i++){
            planes.add(fromSkyResponseOjectToPlane(obj,i));
        }

        return planes;
    }

    private Plane fromSkyResponseOjectToPlane(SkyResponseObject obj, int index){

        String icao24 = null;
        String callsign = null;
        String origin_country = null;
        int time_position = 0;
        int last_contact = 0;
        float longitude = 0;
        float latitude = 0;
        float baro_altitude = 0;
        boolean on_ground = false;
        float velocity = 0;
        float true_track = 0;
        float vertical_rate = 0;
        int[] sensors = null;
        float geo_altitude = 0;
        String squawk = null;
        boolean spi = false;
        int position_source = 0;

        if(obj.getStates()[index][0] != null){
            icao24 = obj.getStates()[index][0];
        }
        if(obj.getStates()[index][1] != null){
            callsign = obj.getStates()[index][1];
        }
        if(obj.getStates()[index][2] != null){
            origin_country = obj.getStates()[index][2];
        }
        if(obj.getStates()[index][3] != null){
            time_position =  Integer.parseInt(obj.getStates()[index][3]);
        }
        if(obj.getStates()[index][4] != null){
            last_contact = Integer.parseInt(obj.getStates()[index][4]);
        }
        if(obj.getStates()[index][5] != null){
            longitude = Float.parseFloat(obj.getStates()[index][5]);
        }
        if(obj.getStates()[index][6] != null){
            latitude = Float.parseFloat(obj.getStates()[index][6]);
        }
        if(obj.getStates()[index][7] != null){
            baro_altitude = Float.parseFloat(obj.getStates()[index][7]);
        }
        if(obj.getStates()[index][8] != null){
            on_ground = Boolean.parseBoolean(obj.getStates()[index][8]);
        }
        if(obj.getStates()[index][9] != null){
            velocity =  Float.parseFloat(obj.getStates()[index][9]);
        }
        if(obj.getStates()[index][10] != null){
            true_track = Float.parseFloat(obj.getStates()[index][10]);
        }
        if(obj.getStates()[index][11] != null){
            vertical_rate = Float.parseFloat(obj.getStates()[index][11]);
        }
        if(obj.getStates()[index][12] != null){
            sensors = null;
        }
        if(obj.getStates()[index][13] != null){
            geo_altitude = Float.parseFloat(obj.getStates()[index][13]);
        }
        if(obj.getStates()[index][14] != null){
            squawk =  obj.getStates()[index][14];
        }
        if(obj.getStates()[index][15] != null){
            spi = Boolean.parseBoolean(obj.getStates()[index][15]);
        }
        if(obj.getStates()[index][16] != null){
            position_source = Integer.parseInt(obj.getStates()[index][16]);
        }

        Plane newPlane = new Plane(icao24,callsign,origin_country,time_position,last_contact,longitude,latitude,
                baro_altitude,on_ground,velocity,true_track,vertical_rate,sensors,geo_altitude,squawk,spi,position_source);
//        Plane newPlane = new Plane(obj.getStates()[index][0],obj.getStates()[index][1],obj.getStates()[index][2],
//                Integer.parseInt(obj.getStates()[index][3]),Integer.parseInt(obj.getStates()[index][4]),
//                Float.parseFloat(obj.getStates()[index][5]),Float.parseFloat(obj.getStates()[index][6]),
//                Float.parseFloat(obj.getStates()[index][7]),Boolean.parseBoolean(obj.getStates()[index][8]),
//                Float.parseFloat(obj.getStates()[index][9]),Float.parseFloat(obj.getStates()[index][10]),
//                Float.parseFloat(obj.getStates()[index][11]),null,Float.parseFloat(obj.getStates()[index][13]),
//                obj.getStates()[index][14],Boolean.parseBoolean(obj.getStates()[index][15]),Integer.parseInt(obj.getStates()[index][16]));
        return newPlane;
    }



}
