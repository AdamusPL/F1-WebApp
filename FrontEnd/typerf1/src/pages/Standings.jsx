import { useEffect, useState } from 'react';
import SeasonYearChooser from '../components/SeasonYearChooser';
import { useYear } from '../components/YearProvider';

export default function Standings() {
    const { year, isLoading } = useYear();
    const [standings, setStandings] = useState([]);

    useEffect(() => {
        if (!isLoading) {
            getStandings();
        }
    }, [year]);

    function getStandings() {
        try {
            fetch(`${import.meta.env.VITE_API_BASE_URL}/get-participant-standings?year=${year}`, {
                credentials: 'include'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error');
                    }
                    return response.json();
                })
                .then(data => {
                    setStandings(data);
                })
        } catch (e) {

        }
    }

    return (<>
        <h1>Standings</h1>

        <SeasonYearChooser />

        {year != 0 ?
            <ol>
                {standings.map(item => (
                    <li>{item.participantName} {item.participantSurname} {item.pointsSum}</li>
                ))}
            </ol>
            : null
        }
    </>);
}