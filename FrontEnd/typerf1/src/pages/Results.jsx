import { useEffect, useState, React } from 'react';
import SeasonYearChooser from '../components/SeasonYearChooser';
import { useYear } from '../components/YearProvider';
import { Fragment } from 'react';

export default function Results() {
    const { year } = useYear();

    const [scores, setScores] = useState([]);

    useEffect(() => {
        getScores();
    }, [year]);

    function getScores() {
        fetch(`${import.meta.env.VITE_API_BASE_URL}/get-season-scores?year=${year}`, {
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                setScores(data);
            });
    }

    return (<>
        <h1 className='mt-4'>Results</h1>
        <SeasonYearChooser />

        {year != 0 ?
            scores.length !== 0 ?
                scores.map(gp => (
                    <Fragment key={gp.id}>
                        <h2>{gp.name}</h2>
                        {
                            gp.sessionDto.map(session => (
                                <Fragment key={session.id}>
                                    <h3>{session.name}</h3>
                                    {
                                        <ol>
                                            {session.scores.map(item => (
                                                <li key={item.id}>{item.participantName} {item.participantSurname} {item.points} {item.jokerUsed ? 'J' : ''}</li>
                                            ))}
                                        </ol>
                                    }
                                </Fragment>
                            ))
                        }
                    </Fragment>
                ))
                : <p>No results at the moment</p>
            : null}
    </>);
}