import {
  withScriptjs,
  withGoogleMap,
  useLoadScript,
  GoogleMap,
  Marker,
} from "@react-google-maps/api";
import React from "react";
import planeMapIcon from "../planeMapIcon.png";

const libraries = ["places"];
const mapContainerStyle = {
  width: "50vw",
  height: "50vh",
};

export default function PlaneMap({ id, longitude, latitude }) {
  const center = { lat: parseFloat(latitude), lng: parseFloat(longitude) };
  console.log(center);
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: "AIzaSyAUg3b1KxFjMA56NkT2TYyqpm4cFOssCoA",
    libraries,
  });

  if (loadError) return "error loading maps";
  if (!isLoaded) return "loading maps";
  return (
    <div>
      <GoogleMap mapContainerStyle={mapContainerStyle} zoom={6} center={center}>
        <Marker
          key={id}
          position={{ lat: latitude, lng: longitude }}
          icon={{
            url: planeMapIcon,
            scaledSize: new window.google.maps.Size(30, 30),
          }}
        />
      </GoogleMap>
    </div>
  );
}
