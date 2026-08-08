import { useState, useEffect, Fragment, React } from "react";
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { useParticipant } from "./ParticipantProvider";

export default function ParticipantChooser() {
    const { participant, setParticipant } = useParticipant();
    const [participants, setParticipants] = useState([]);

    useEffect(() => {
        getParticipants();
    }, []);

    async function getParticipants() {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/get-participants`, {
            credentials: 'include'
        });
        const data = await response.json();
        setParticipants(data);
    }

    return (<>
        <Fragment>
            <DropdownButton
                id='season-year'
                title='Choose participant'
            >
                {
                    participants.map(item => (
                        <Dropdown.Item key={item.id} id={item.id} onClick={() => setParticipant(item)}>{item.firstName} {item.surname}</Dropdown.Item>
                    ))
                }
            </DropdownButton>
            <p className="mt-2">{participant.firstName} {participant.surname}</p>
        </Fragment>
    </>);
}