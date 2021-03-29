package com.labproject.SkyTracker.OpenSky;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "snapshots")
@JsonIgnoreProperties
public class SnapShots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private float longitude;
    private float latitude;
    private float velocity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plane_To_Track_Id",nullable=false,unique=false)
    @JsonIgnore
    private PlaneToTrack planeToTrack;

    public SnapShots() {}

    public SnapShots(float longitude, float latitude, float velocity) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.velocity = velocity;
    }

    public SnapShots(Long Id,float longitude, float latitude, float velocity) {
        this.Id = Id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.velocity = velocity;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
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

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public PlaneToTrack getPlaneToTrack() {
        return planeToTrack;
    }

    public void setPlaneToTrack(PlaneToTrack planeToTrack) {
        this.planeToTrack = planeToTrack;
    }
}
