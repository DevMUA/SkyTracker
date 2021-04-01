import React from "react";
import PlaneService from "../services/PlaneService";
import { Line } from "react-chartjs-2";

class PlaneTrackerComponent extends React.Component {


    constructor(props){
      super(props);
      this.state = {
        planes: [],
        snapshots: []
      }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
      setInterval(this.loadData, 2000);
    }

    async loadData() {
      try {
        PlaneService.getTrackedPlane(this.props.icao24).then((response) => {
          var tempArray = [];
          tempArray.push(response.data);
          this.setState({
            planes: tempArray
          })
        });
      } catch (e) {
        console.log(e);
      }

      try {
        PlaneService.getTrackedPlaneSnapShots(this.props.icao24).then((response) => {
          this.setState({
            snapshots: response.data
          })
          console.log(this.state.snapshots);
        });
      } catch (e) {
        console.log(e);
      }
    }


    render(){

      var data = {
        labels: [],
        datasets: [{
          label: "Velocity",
          data: [],
          fill: true,
          backgroundColor: "rgba(75,192,192,0.2)",
          borderColor: "rgba(75,192,192,1)"
        }]
      }

      for(var i=0; i< this.state.snapshots.length; i++){
          data.datasets[0].data.push(this.state.snapshots[i].velocity);
          data.labels.push(String(this.state.snapshots[i].id));
      }

      if(this.state.planes.length === 0){
        return "retrieving data";
      }
      return(
        <div>
          <h1 className="text-center"> Tracking Plane {this.props.icao24}</h1>
          <br/>
          <div className="container">
              <div className = "row" >
                  <div className = "col-sm" >
                    Velocity:
                  </div>
                  <div className = "col-sm" >
                    Longitude:
                  </div>
                <div className = "col-sm" >
                    Latitude:
                </div>
                <div className = "col-sm" >
                    Altitude:
                </div>
              </div>
                {
                  this.state.planes.map(
                    plane =>
                    <div className = "row" >
                        <div className = "col-sm" >
                          {plane.velocity}
                        </div>
                        <div className = "col-sm" >
                          {plane.longitude}
                        </div>
                      <div className = "col-sm" >
                          {plane.latitude}
                      </div>
                      <div className = "col-sm" >
                          {plane.baro_altitude}
                      </div>
                    </div>
                  )
                }
                <div className = "row" >
                    <div className = "col-sm" >
                      <Line data={data} />
                    </div>

                </div>

            </div>
          </div>
      )
    }
}

export default PlaneTrackerComponent
