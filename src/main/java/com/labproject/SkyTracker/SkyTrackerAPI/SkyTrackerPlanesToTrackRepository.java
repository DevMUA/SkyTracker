package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.OpenSky.PlaneToTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkyTrackerPlanesToTrackRepository extends JpaRepository<PlaneToTrack,Long> {

    @Query("SELECT p FROM PlaneToTrack p WHERE p.icao24 = ?1")
    Optional<PlaneToTrack> findPlaneById(String id);

}