import React from "react";
import { Link } from "react-router-dom";
import image from "../planeDefaultIcon.svg";

export default function Plane({ id, origin_country, icao24, on_ground }) {
  return (
    <article className="plane">
      <div className="img-container">
        <img src={image} alt="Image not found" />
      </div>
      <div className="plane-footer">
        <h3>{icao24 || "Icao24 Missing"}</h3>
        <h4>{origin_country || "Country Missing"}</h4>
        <p>Is Flying: {(!on_ground).toString()}</p>
        <Link to={`/plane/${icao24}`} className="btn btn-primary btn-details">
          Track
        </Link>
      </div>
    </article>
  );
}
