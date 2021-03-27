package com.labproject.SkyTracker.OpenSky;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table
@JsonIgnoreProperties
public class Plane {

    @Id
    @SequenceGenerator(
            name = "plane_sequence",
            sequenceName = "plane_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plane_sequence"
    )

    private Long Id;
    private String icao24;
    private String callsign;
    private String origin_country;
    private int time_position;
    private int last_contact;
    private float longitude;
    private float latitude;
    private float baro_altitude;
    private boolean on_ground;
    private float velocity;
    private float true_track;
    private float vertical_rate;
    private int[] sensors;
    private float geo_altitude;
    private String squawk;
    private boolean spi;
    private int position_source;

    public Plane(){}

    public Plane(String icao24, String callsign, String origin_country, int time_position,
                 int last_contact, float longitude, float latitude, float baro_altitude,
                 boolean on_ground, float velocity, float true_track, float vertical_rate,
                 int[] sensors, float geo_altitude, String squawk, boolean spi, int position_source) {

        this.icao24 = icao24;
        this.callsign = callsign;
        this.origin_country = origin_country;
        this.time_position = time_position;
        this.last_contact = last_contact;
        this.longitude = longitude;
        this.latitude = latitude;
        this.baro_altitude = baro_altitude;
        this.on_ground = on_ground;
        this.velocity = velocity;
        this.true_track = true_track;
        this.vertical_rate = vertical_rate;
        this.sensors = sensors;
        this.geo_altitude = geo_altitude;
        this.squawk = squawk;
        this.spi = spi;
        this.position_source = position_source;
    }

    public Plane(Long id,String icao24, String callsign, String origin_country, int time_position,
                 int last_contact, float longitude, float latitude, float baro_altitude,
                 boolean on_ground, float velocity, float true_track, float vertical_rate,
                 int[] sensors, float geo_altitude, String squawk, boolean spi, int position_source) {

        this.Id = id;
        this.icao24 = icao24;
        this.callsign = callsign;
        this.origin_country = origin_country;
        this.time_position = time_position;
        this.last_contact = last_contact;
        this.longitude = longitude;
        this.latitude = latitude;
        this.baro_altitude = baro_altitude;
        this.on_ground = on_ground;
        this.velocity = velocity;
        this.true_track = true_track;
        this.vertical_rate = vertical_rate;
        this.sensors = sensors;
        this.geo_altitude = geo_altitude;
        this.squawk = squawk;
        this.spi = spi;
        this.position_source = position_source;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }

    public int getTime_position() {
        return time_position;
    }

    public void setTime_position(int time_position) {
        this.time_position = time_position;
    }

    public int getLast_contact() {
        return last_contact;
    }

    public void setLast_contact(int last_contact) {
        this.last_contact = last_contact;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getBaro_altitude() {
        return baro_altitude;
    }

    public void setBaro_altitude(float baro_altitude) {
        this.baro_altitude = baro_altitude;
    }

    public boolean isOn_ground() {
        return on_ground;
    }

    public void setOn_ground(boolean on_ground) {
        this.on_ground = on_ground;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getTrue_track() {
        return true_track;
    }

    public void setTrue_track(float true_track) {
        this.true_track = true_track;
    }

    public float getVertical_rate() {
        return vertical_rate;
    }

    public void setVertical_rate(float vertical_rate) {
        this.vertical_rate = vertical_rate;
    }

    public int[] getSensors() {
        return sensors;
    }

    public void setSensors(int[] sensors) {
        this.sensors = sensors;
    }

    public float getGeo_altitude() {
        return geo_altitude;
    }

    public void setGeo_altitude(float geo_altitude) {
        this.geo_altitude = geo_altitude;
    }

    public String getSquawk() {
        return squawk;
    }

    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }

    public boolean isSpi() {
        return spi;
    }

    public void setSpi(boolean spi) {
        this.spi = spi;
    }

    public int getPosition_source() {
        return position_source;
    }

    public void setPosition_source(int position_source) {
        this.position_source = position_source;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "icao24='" + icao24 + '\'' +
                ", callsign='" + callsign + '\'' +
                ", origin_country='" + origin_country + '\'' +
                ", time_position=" + time_position +
                ", last_contact=" + last_contact +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", baro_altitude=" + baro_altitude +
                ", on_ground=" + on_ground +
                ", velocity=" + velocity +
                ", true_track=" + true_track +
                ", vertical_rate=" + vertical_rate +
                ", sensors=" + Arrays.toString(sensors) +
                ", geo_altitude=" + geo_altitude +
                ", squawk='" + squawk + '\'' +
                ", spi=" + spi +
                ", position_source=" + position_source +
                '}';
    }
}
