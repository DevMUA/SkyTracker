package com.labproject.SkyTracker.SkyTrackerAPI;

import com.labproject.SkyTracker.OpenSky.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkyTrackerRepository extends JpaRepository<Plane,Long> {

    @Query("SELECT p FROM Plane p WHERE p.icao24 = ?1")
    Optional<Plane> findPlaneById(String id);

}
