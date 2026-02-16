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
        <h1>World Records</h1>

        {worldRecords.map(record => (
            <Fragment>
                <p>{record.name}: {record.record.points} ({record.record.participantName} {record.record.participantSurname}, {record.record.grandPrixName} {record.record.year})</p>
            </Fragment>
        ))}
    </>);
}