import { useEffect, useState } from 'react';
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
        try {
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
        } catch (e) {

        }

    }

    return (<>
        <h1 className='mt-4'>Results</h1>
        <SeasonYearChooser />

        {year != 0 ?
            scores.length !== 0 ?
                scores.map(gp => (
                    <Fragment>
                        <h2>{gp.name}</h2>
                        {
                            gp.sessionDto.map(session => (
                                <Fragment>
                                    <h3>{session.name}</h3>
                                    {
                                        <ol>
                                            {session.scores.map(item => (
                                                <li>{item.participantName} {item.participantSurname} {item.points} {item.jokerUsed ? 'J' : ''}</li>
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