import { Fragment, useEffect, useState } from "react";

export default function WorldRecords() {

    const [worldRecords, setWorldRecords] = useState([]);

    useEffect(() => {
        getWorldRecords();
    }, []);

    function getWorldRecords() {
        fetch(`${import.meta.env.VITE_API_BASE_URL}/get-records`, {
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                setWorldRecords(data);
            })
    }

    return (<>
        <h1 className="mt-4">World Records</h1>

        {worldRecords.length !== 0 ?
            worldRecords.map(record => (
                <Fragment>
                    {!record.jokerUsed ? (
                        <Fragment>
                            <h2>Without using Joker</h2>
                            {record.best ? (
                                <Fragment>
                                    <h3>Best</h3>
                                    {record.worldRecords.map(worldRecord => (
                                        <Fragment>
                                            <p>{worldRecord.name}: {worldRecord.record.points} ({worldRecord.record.participantName} {worldRecord.record.participantSurname}, {worldRecord.record.grandPrixName} {worldRecord.record.year})</p>
                                        </Fragment>
                                    ))}
                                </Fragment>
                            )
                                :
                                <Fragment>
                                    <h3>Worst</h3>
                                    {record.worldRecords.map(worldRecord => (
                                        <Fragment>
                                            <p>{worldRecord.name}: {worldRecord.record.points} ({worldRecord.record.participantName} {worldRecord.record.participantSurname}, {worldRecord.record.grandPrixName} {worldRecord.record.year})</p>
                                        </Fragment>))}
                                </Fragment>
                            }
                        </Fragment>
                    )
                        :
                        (<Fragment>
                            <h2>Using Joker</h2>
                            {record.best ?
                                <Fragment>
                                    <h3>Best</h3>
                                    {record.worldRecords.map(worldRecord => (<Fragment>
                                        <p>{worldRecord.name}: {worldRecord.record.points} ({worldRecord.record.participantName} {worldRecord.record.participantSurname}, {worldRecord.record.grandPrixName} {worldRecord.record.year})</p>
                                    </Fragment>))}
                                </Fragment>
                                :
                                <Fragment>
                                    <h3>Worst</h3>
                                    {record.worldRecords.map(worldRecord => (
                                        <Fragment>

                                            <p>{worldRecord.name}: {worldRecord.record.points} ({worldRecord.record.participantName} {worldRecord.record.participantSurname}, {worldRecord.record.grandPrixName} {worldRecord.record.year})</p>
                                        </Fragment>))}
                                </Fragment>
                            }
                        </Fragment>)
                    }
                </Fragment>
            )) :
            <p>No results at the moment</p>}
    </>);
}