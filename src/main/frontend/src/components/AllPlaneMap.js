import {
    GoogleMap,
    Marker,
    InfoWindow,
} from "@react-google-maps/api";
import React, {useState} from "react";
import planeMapIcon from "../planeMapIcon.png";
import {withGoogleMap, withScriptjs} from "react-google-maps";

const libraries = ["places"];
const mapContainerStyle = {
    width: "80vw",
    height: "80vh",
};

async function Map() {
    const response = await fetch(
        `http://localhost:8081/api/v1/planes`
    );
    const planes = await response.json();
    //const [selectedPlane, setSelectedPlane] = useState(null);
    return (
        <GoogleMap
            defaultZoom={10}
            defaultCenter={{lat: 53.0000, lng: 9.0000}}
        >
            {planes.map((plane) => (
                <Marker
                    key={plane.icao24}
                    position={{
                        lat: plane.latitude,
                        lng: plane.longitude
                    }}
                    icon={{
                        url: planeMapIcon,
                        scaledSize: new window.google.maps.Size(30, 30),
                    }}
                    /*onClick={() => {
                        setSelectedPlane(true);
                    }}*/
                />
            ))}
            {/*{selectedPlane && (
                <InfoWindow
                    position={{lat: selectedPlane.latitude, lng: selectedPlane.longitude}}
                    onCloseClick={() => {
                        setSelectedPlane(null);
                    }}
                >
                    <div>
                        <p>origin: {selectedPlane.origin_country}</p>
                        <p>ground: {selectedPlane.on_ground}</p>
                        <p>vel: {selectedPlane.velocity}</p>
                        <p>lat: {selectedPlane.latitude}</p>
                        <p>lng: {selectedPlane.longitude}</p>
                    </div>
                </InfoWindow>
            )}*/}
        </GoogleMap>
    );
}

const WrappedMap = withScriptjs(withGoogleMap(Map))

export default function AllPlaneMap() {
    return (
        <div style={{width: "80vw", height: "80vh"}}>
            <WrappedMap
                googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&
                key=AIzaSyAUg3b1KxFjMA56NkT2TYyqpm4cFOssCoA`}
                loadingElement={<div style={{height: "100%"}} />}
                containerElement={<div style={{height: "100%"}} />}
                mapElement={<div style={{height: "100%"}} />}
            />
        </div>
    );
}

/*[
    {
        "icao24":"4b390b","callsign":"HBVPR   ",
        "origin_country":"Switzerland","time_position":1618077283,"last_contact":1618077287,
        "longitude":6.0959,"latitude":46.2236,"baro_altitude":0.0,"on_ground":true,"velocity":0.0,
        "true_track":216.56,"vertical_rate":0.0,"sensors":null,"geo_altitude":0.0,"squawk":"1000",
        "spi":false,"position_source":0,"id":10
    }
]*/
