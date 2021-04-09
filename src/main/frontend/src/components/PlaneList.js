import React from "react";
import Plane from "./Plane";
import Loading from "./Loading";
import { useGlobalContext } from "../context";

export default function PlaneList() {
  const { planes, loading } = useGlobalContext();
  if (loading) {
    return <Loading />;
  }
  if (planes.length < 1) {
    return <h2 className="section-title">no planes found</h2>;
  } else {
    return (
      <section className="section">
        <h2 className="section-title">Planes</h2>
        <div className="planes-center">
          {planes.map((item) => {
            return <Plane key={item.id} {...item} />;
          })}
        </div>
      </section>
    );
  }
}
