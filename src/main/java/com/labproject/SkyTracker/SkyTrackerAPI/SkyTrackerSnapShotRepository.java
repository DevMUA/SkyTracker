package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.OpenSky.SnapShots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkyTrackerSnapShotRepository extends JpaRepository<SnapShots,Long> {

    @Query("SELECT s FROM SnapShots s")
    List findByPlaneToTrackId(Long planeToTrackId);
}
