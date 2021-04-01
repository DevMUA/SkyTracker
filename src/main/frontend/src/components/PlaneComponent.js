import React from "react";
import PlaneService from "../services/PlaneService";

class PlaneComponent extends React.Component {


    constructor(props){
      super(props)
        this.state = {
          planes:[]
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
      setInterval(this.loadData, 10000);
    }

      async loadData() {
        try {
          PlaneService.getPlanes().then((response) => {
            this.setState({
              planes: response.data
            })
          });
        } catch (e) {
          console.log(e);
        }
      }

      async postData(planeIdentifier) {
        try {
          PlaneService.postPlane(planeIdentifier);
        } catch (e) {
          console.log(e);
        }
      }

      buttonOnClick(planeIdentifier) {
        this.props.setIcao24ID(planeIdentifier);
        this.props.hidePlaneList();
        this.postData(planeIdentifier);
      }

    render(){
      return(
        <div>
          <h1 className="text-center"> Plane List </h1>
          <table className = "table table-striped">
            <thead>
              <tr>
                <td>Icao24</td>
                <td>Callsign</td>
                <td>Origin Country</td>
                <td>Time position</td>
                <td>Longitude</td>
                <td>Latitude</td>
                <td>Altitude</td>
                <td>Is On Ground</td>
                <td>Velocity</td>
                <td>Track</td>
              </tr>
                        </thead>
              <tbody>
                {
                  this.state.planes.map(
                    plane =>
                    <tr key = {plane.id}>
                      <td>{plane.icao24}</td>
                      <td>{plane.callsign}</td>
                      <td>{plane.origin_country}</td>
                      <td>{plane.time_position}</td>
                      <td>{plane.longitude}</td>
                      <td>{plane.latitude}</td>
                      <td>{plane.baro_altitude}</td>
                      <td>{plane.on_ground.toString()}</td>
                      <td>{plane.velocity}</td>
                      <td> <button variant="primary" onClick={ () => this.buttonOnClick(plane.icao24)}>Track</button></td>
                    </tr>
                  )
                }
              </tbody>
          </table>
        </div>
      )
    }
}

export default PlaneComponent
