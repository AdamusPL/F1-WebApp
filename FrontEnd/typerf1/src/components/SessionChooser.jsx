import { useState, useEffect, Fragment, React } from "react";
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { useSession } from "./SessionProvider";
import { useGrandPrix } from "./GrandPrixProvider";
import { usePredictions } from "./PredictionProvider";

export default function SessionChooser() {
    const { grandPrix } = useGrandPrix();
    const { session, setSession } = useSession();
    const { setPredictions } = usePredictions();
    const [sessions, setSessions] = useState([]);

    useEffect(() => {
        getSessions();
    }, [grandPrix]);

    async function getSessions() {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/get-sessions?grandPrixId=${grandPrix.id}`, {
            credentials: 'include'
        });
        const data = await response.json();
        setSessions(data);
    }

    function setSessionAndResetPredictions(session) {
        setPredictions({drivers: [], fastestLap: ""});
        setSession(session);
    }

    return (<>
        <Fragment>
            <DropdownButton
                id='season-year'
                title='Choose session'
            >
                {
                    sessions.map(item => (
                        <Dropdown.Item key={item.id} id={item.id} onClick={() => setSessionAndResetPredictions(item)}>{item.name}</Dropdown.Item>
                    ))
                }
            </DropdownButton>
            <p className="mt-2">{session.name}</p>
        </Fragment>
    </>);
}