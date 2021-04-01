import React from "react";
import './App.css';
import PlaneComponent from "./components/PlaneComponent";
import PlaneTrackerComponent from "./components/PlaneTrackerComponent"

class App extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      showPlanesComponent: true,
      showPlaneTrackerComponent: false,
      icao24ID: ""
    }
  }

  hidePlaneList(){
      this.setState({
        showPlanesComponent: false,
        showPlaneTrackerComponent: true
      });
  }

  setIcao24ID(value){
    this.setState({
      icao24ID: value
    });
  }


  render(){
    var partial;

    if(this.state.showPlanesComponent === true){
      partial = <PlaneComponent
                  hidePlaneList={this.hidePlaneList.bind(this)}
                  setIcao24ID={this.setIcao24ID.bind(this)}
                  />;
    }
    else {
      partial = <PlaneTrackerComponent icao24={this.state.icao24ID}/>
    }

      return (
          <div className="App">
            {partial}
          </div>
      );
    }

}

export default App;
