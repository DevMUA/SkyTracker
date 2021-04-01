import axios from "axios";

const PLANES_REST_API_URL = "http://localhost:8080/api/v1/planes";
const PLANES_TRACK_API_URL = "http://localhost:8080/api/v1/planes/track/";
const PLANES_TRACK_SNAPSHOTS_API_URL = "http://localhost:8080/api/v1/planes/track/snapshots/";

class PlaneService {

    getPlanes(){
        return axios.get(PLANES_REST_API_URL);
    }

    postPlane(planeIdentifier){
      var newURL = PLANES_TRACK_API_URL+planeIdentifier;
      return axios.post(newURL);
    }

    getTrackedPlane(planeIdentifier){
      return axios.get(PLANES_TRACK_API_URL+planeIdentifier);
    }

    getTrackedPlaneSnapShots(planeIdentifier){
      return axios.get(PLANES_TRACK_SNAPSHOTS_API_URL+planeIdentifier);
    }
}

export default new PlaneService();
