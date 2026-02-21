import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement
} from 'chart.js';
import { useEffect, useState } from "react";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement
);

export default function Plot() {
    useEffect(() => {
        getData();
    }, []);

    const [lineChartData, setLineChartData] = useState({
        labels: ['Bahrain', 'Saudi Arabia'],

        datasets: [
            {
                label: 'Points',
                data: [38, 53]
            }
        ]
    });

    async function getData() {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/get-data-plot`, {
            credentials: 'include'
        });
        const data = await response.json();
        debugger;

        const colors = ['orange', 'red'];
        setLineChartData({
            labels: data[0].sessions,
            datasets: data.map((participant, index) => ({
                label: participant.participantName + " " + participant.participantSurname,
                data: participant.scores,
                borderColor: colors[index]
            }))
        });
    }

    const options = {};

    return (<>
        <Line options={options} data={lineChartData} />
    </>);
}