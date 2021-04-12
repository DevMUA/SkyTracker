import React, { useState, useContext, useEffect } from "react";
import { useCallback } from "react";

const url = "http://localhost:8082/api/v1/planes";
const AppContext = React.createContext();

const AppProvider = ({ children }) => {
  const [loading, setLoading] = useState(true);
  const [planes, setPlanes] = useState([]);

  React.useEffect(() => {
    const interval = setInterval(async () => {
      console.log("Called API!");
      //localhost:8080/api/v1/planes/track/snapshots/
      fetchPlanes();
    }, 30000);
    return () => clearInterval(interval);
  }, []);

  const fetchPlanes = useCallback(async () => {
    setLoading(true);
    try {
      const response = await fetch(`${url}`);
      const data = await response.json();
      const planeStructure = {
        planes: [],
      };
      for (var i = 0; i < data.length; i++) {
        planeStructure.planes.push(data[i]);
      }
      const planeData = planeStructure.planes;

      if (planeData) {
        const newPlanes = planeData.map((item) => {
          const {
            id,
            icao24,
            callsign,
            origin_country,
            time_position,
            last_contact,
            longitude,
            latitude,
            baro_altitude,
            on_ground,
            velocity,
            true_track,
            vertical_rate,
            sensors,
            geo_altitude,
            squawk,
            spi,
            position_source,
          } = item;

          return {
            id: id,
            icao24: icao24,
            callsign: callsign,
            origin_country: origin_country,
            time_position: time_position,
            last_contact: last_contact,
            longitude: longitude,
            latitude: latitude,
            baro_altitude: baro_altitude,
            on_ground: on_ground,
            velocity: velocity,
            true_track: true_track,
            vertical_rate: vertical_rate,
            sensors: sensors,
            geo_altitude: geo_altitude,
            squawk: squawk,
            spi: spi,
            position_source: position_source,
          };
        });
        setPlanes(newPlanes);
      } else {
        setPlanes([]);
      }
      setLoading(false);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchPlanes();
  }, [fetchPlanes]);
  return (
    <AppContext.Provider value={{ loading, planes }}>
      {children}
    </AppContext.Provider>
  );
};
// make sure use
export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
