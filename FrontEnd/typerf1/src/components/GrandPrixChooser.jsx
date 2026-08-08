import { useState, useEffect } from "react";
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { useGrandPrix } from "./GrandPrixProvider";
import { Fragment } from "react";
import { useSession } from "./SessionProvider";
import { usePredictions } from "./PredictionProvider";
import { React } from "react";

export default function GrandPrixChooser() {
    const { grandPrix, setGrandPrix } = useGrandPrix();
    const { setSession } = useSession();
    const { setPredictions, setArePredictionsPosted, setIsDeadlinePassed } = usePredictions();
    const [grandPrixs, setGrandPrixs] = useState([]);

    useEffect(() => {
        getGrandPrix();
    }, []);

    async function getGrandPrix() {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/get-grand-prix`, {
            credentials: 'include'
        });
        const data = await response.json();
        setGrandPrixs(data);
    }

    function setGrandPrixAndResetSession(grandPrix) {
        setSession({});
        setPredictions({drivers: [], fastestLap: ""});
        setArePredictionsPosted(true);
        setIsDeadlinePassed(true);
        setGrandPrix(grandPrix);
    }

    return (<>
        <Fragment>
            <DropdownButton
                id='season-year'
                title='Choose race weekend'
            >
                {
                    grandPrixs.map(item => (
                        <Dropdown.Item key={item.id} id={item.id} onClick={() => setGrandPrixAndResetSession(item)}>{item.name}</Dropdown.Item>
                    ))
                }
            </DropdownButton>
            <p className="mt-2">{grandPrix.name}</p>
        </Fragment>
    </>);
}