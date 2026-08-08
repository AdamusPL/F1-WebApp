import { Fragment, useEffect, useState, React } from "react";
import ParticipantChooser from "../components/ParticipantChooser";
import { useParticipant } from "../components/ParticipantProvider";

export default function PersonalBest() {
    const { participant } = useParticipant();
    const [personalBest, setPersonalBest] = useState([]);

    useEffect(() => {
        if (participant.id) {
            fetchPersonalBest();
        }
    }, [participant]);

    function fetchPersonalBest() {
        fetch(`${import.meta.env.VITE_API_BASE_URL}/get-personal-best?id=${participant.id}`, {
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                setPersonalBest(data);
            });
    }

    return (
        <>
            <h1 className="mt-4">Personal Best</h1>

            <ParticipantChooser />

            {personalBest.length !== 0 ?
                personalBest.map(record => (
                    <Fragment key={record.id}>
                        <p>{record.name}: {record.record.points} ({record.record.participantName} {record.record.participantSurname}, {record.record.grandPrixName} {record.record.year})</p>
                    </Fragment>
                ))
                : <p>No results at the moment</p>}
        </>
    );
}