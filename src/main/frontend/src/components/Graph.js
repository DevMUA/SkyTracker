import Loading from "../components/Loading";
import { Line } from "react-chartjs-2";

export default function Graph({ snapshots }) {
  var data = {
    labels: [],
    datasets: [
      {
        label: "Velocity",
        data: [],
        fill: true,
        backgroundColor: "rgba(75,192,192,0.2)",
        borderColor: "rgba(75,192,192,1)",
      },
    ],
  };
  for (var i = 0; i < snapshots.length; i++) {
    data.datasets[0].data.push(snapshots[i].velocity);
    data.labels.push(String(snapshots[i].id));
  }

  if (snapshots.length === 0) {
    return <Loading />;
  } else {
    return <Line data={data} />;
  }
}
