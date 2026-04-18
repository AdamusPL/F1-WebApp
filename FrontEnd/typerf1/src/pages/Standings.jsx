import { Fragment, useEffect, useState } from 'react';
import SeasonYearChooser from '../components/SeasonYearChooser';
import { useYear } from '../components/YearProvider';
import Plot from '../components/Plot';

export default function Standings() {
    const { year } = useYear();
    const [standings, setStandings] = useState([]);

    useEffect(() => {
        getStandings();
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

    const printJokers = (item) => {
        let jokers = "";
        for (let i = 0; i < item.numberOfJokersUsed; i++) {
            jokers += "J";
        }

        return (<>
            {jokers}
        </>)
    }

    return (<>
        <h1 className='mt-4'>Standings</h1>

        <SeasonYearChooser />

        {year != 0 ?
            standings.length !== 0 ?
                <Fragment>
                    <ol>
                        {standings.map(item => (
                            <li>{item.participantName} {item.participantSurname} {item.pointsSum} {printJokers(item)}</li>
                        ))}
                    </ol>
                    <Plot />
                </Fragment>
                : <p>No results at the moment</p>
            : null
        }
    </>);
}