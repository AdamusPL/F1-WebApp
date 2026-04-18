import { Fragment, useEffect, useState } from "react";
import ParticipantChooser from "../components/ParticipantChooser";
import { useParticipant } from "../components/ParticipantProvider";

export default function PersonalBest() {
    const { participant } = useParticipant();
    const [personalBest, setPersonalBest] = useState([]);

    useEffect(() => {
        fetchPersonalBest();
    }, [participant]);

    function fetchPersonalBest() {
        try {
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
                })
        } catch (e) {

        }
    }

    return (
        <>
            <h1 className="mt-4">Personal Best</h1>

            <ParticipantChooser />

            {personalBest.length !== 0 ?
                personalBest.map(record => (
                    <Fragment>
                        <p>{record.name}: {record.record.points} ({record.record.participantName} {record.record.participantSurname}, {record.record.grandPrixName} {record.record.year})</p>
                    </Fragment>
                ))
                : <p>No results at the moment</p>}
        </>
    );
}