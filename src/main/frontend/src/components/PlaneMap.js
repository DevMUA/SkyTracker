import {
  withScriptjs,
  withGoogleMap,
  useLoadScript,
  GoogleMap,
  Marker,
  InfoWindow,
} from "@react-google-maps/api";
import React, {useState} from "react";
import planeMapIcon from "../planeMapIcon.png";

const libraries = ["places"];
const mapContainerStyle = {
  width: "80vw",
  height: "80vh",
};

export default function PlaneMap({ id, longitude, latitude }) {
  const center = { lat: parseFloat(latitude), lng: parseFloat(longitude) };
  console.log(center);
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: "",
    libraries,
  });
  const [selected, setSelected] = useState(null);

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
          onClick={() => {
            setSelected(true);
          }}

        />
        {selected && (
            <InfoWindow
                position={{ lat: latitude, lng: longitude }}
                onCloseClick={() => {
                  setSelected(null);
                }}
            >
              <div><p>lat: {latitude}</p><p>lng: {longitude}</p></div>
            </InfoWindow>
        )}
      </GoogleMap>
    </div>
  );
}
