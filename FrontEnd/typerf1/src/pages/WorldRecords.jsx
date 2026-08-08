import { Fragment, useEffect, useState, React } from "react";

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
                <Fragment key={record.id}>
                    <h2>{record.jokerUsed ? "Using Joker" : "Without using Joker"}</h2>
                    <h3>{record.best ? "Best" : "Worst"}</h3>
                    {record.worldRecords.map(worldRecord => (
                        <p key={worldRecord.id}>{worldRecord.name}: {worldRecord.record.points} ({worldRecord.record.participantName} {worldRecord.record.participantSurname}, {worldRecord.record.grandPrixName} {worldRecord.record.year})</p>
                    ))}
                </Fragment>
            )) :
            <p>No results at the moment</p>}
    </>);
}