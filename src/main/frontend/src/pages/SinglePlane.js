import React from "react";
import Loading from "../components/Loading";
import Graph from "../components/Graph";
import PlaneMap from "../components/PlaneMap";
import { useParams, Link } from "react-router-dom";
import image from "../planeDefaultIcon.svg";
import axios from "axios";

export default function SinglePlane() {
  const { icao24 } = useParams();
  const [loading, setLoading] = React.useState(false);
  const [plane, setPlane] = React.useState(null);
  const [snapshots, setSnapshots] = React.useState([]);

  React.useEffect(() => {
    const interval = setInterval(async () => {
      console.log("Called API!");
      //localhost:8080/api/v1/planes/track/snapshots/

      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/planes/track/snapshots/${icao24}`
        );
        const data = await response.json();
        const planeSnapshots = {
          snapshots: [],
        };
        for (var i = 0; i < data.length; i++) {
          planeSnapshots.snapshots.push(data[i]);
        }
        setSnapshots(planeSnapshots.snapshots);

        if (data) {
          const item = {
            id: data.id,
            icao24: data.icao24,
            callsign: data.callsign,
            origin_country: data.origin_country,
            time_position: data.time_position,
            last_contact: data.last_contact,
            longitude: data.longitude,
            latitude: data.latitude,
            baro_altitude: data.baro_altitude,
            on_ground: data.on_ground,
            velocity: data.velocity,
            true_track: data.true_track,
            vertical_rate: data.vertical_rate,
            sensors: data.sensors,
            geo_altitude: data.geo_altitude,
            squawk: data.squawk,
            spi: data.spi,
            position_source: data.position_source,
          };
          setPlane(item);
        } else {
          setPlane(null);
        }
      } catch (error) {
        console.log(error);
      }
      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/planes/track/${icao24}`
        );
        const data = await response.json();
        if (data) {
          const item = {
            id: data.id,
            icao24: data.icao24,
            callsign: data.callsign,
            origin_country: data.origin_country,
            time_position: data.time_position,
            last_contact: data.last_contact,
            longitude: data.longitude,
            latitude: data.latitude,
            baro_altitude: data.baro_altitude,
            on_ground: data.on_ground,
            velocity: data.velocity,
            true_track: data.true_track,
            vertical_rate: data.vertical_rate,
            sensors: data.sensors,
            geo_altitude: data.geo_altitude,
            squawk: data.squawk,
            spi: data.spi,
            position_source: data.position_source,
          };
          setPlane(item);
        } else {
          setPlane(null);
        }
      } catch (error) {
        console.log(error);
      }
    }, 10000);
    return () => clearInterval(interval);
  }, []);

  React.useEffect(() => {
    setLoading(true);
    async function getPlane() {
      //POST TO ADD TRACK PLANE TO DATABASE
      try {
        const resp = await axios.post(
          `http://localhost:8080/api/v1/planes/track/${icao24}`
        );
      } catch (error) {
        console.log(error);
      }

      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/planes/track/${icao24}`
        );
        const data = await response.json();
        if (data) {
          const item = {
            id: data.id,
            icao24: data.icao24,
            callsign: data.callsign,
            origin_country: data.origin_country,
            time_position: data.time_position,
            last_contact: data.last_contact,
            longitude: data.longitude,
            latitude: data.latitude,
            baro_altitude: data.baro_altitude,
            on_ground: data.on_ground,
            velocity: data.velocity,
            true_track: data.true_track,
            vertical_rate: data.vertical_rate,
            sensors: data.sensors,
            geo_altitude: data.geo_altitude,
            squawk: data.squawk,
            spi: data.spi,
            position_source: data.position_source,
          };
          setPlane(item);
        } else {
          setPlane(null);
        }
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    }
    getPlane();
  }, [icao24]);
  if (loading) {
    return <Loading />;
  }
  if (!plane) {
    return <h2 className="section-title">no plane to display</h2>;
  } else {
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
    } = plane;

    return (
      <section className="section planetrack-section">
        <Link to="/" className="btn btn-primary">
          back home
        </Link>
        <h2 className="section-title">{icao24}</h2>
        <div className="planetrack">
          <img src={image} alt="No image"></img>
          <div className="planetrack-info">
            <p>
              <span className="planetrack-data">Icao24 :</span> {icao24}
            </p>
            <p>
              <span className="planetrack-data">Callsign :</span> {callsign}
            </p>
            <p>
              <span className="planetrack-data">longitude :</span> {longitude}
            </p>
            <p>
              <span className="planetrack-data">latitude :</span> {latitude}
            </p>
            <p>
              <span className="planetrack-data">velocity :</span> {velocity}
            </p>
            <p>
              <span className="planetrack-data">isFlying :</span>{" "}
              {(!on_ground).toString()}
            </p>
          </div>
        </div>
        <h2 className="section-title">Velocity Graph</h2>
        <div className="planetrack-graph">
          <Graph snapshots={snapshots} />
        </div>
        <h2 className="section-title">Map tracking</h2>
        <div className="planetrack-graph">
          <PlaneMap {...plane} />
        </div>
      </section>
    );
  }
}
