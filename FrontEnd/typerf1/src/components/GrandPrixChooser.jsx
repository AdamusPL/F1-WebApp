import { useState, useEffect } from "react";
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { useGrandPrix } from "./GrandPrixProvider";
import { Fragment } from "react";
import { useSession } from "./SessionProvider";
import { usePredictions } from "./PredictionProvider";

export default function GrandPrixChooser() {
    const { grandPrix, setGrandPrix } = useGrandPrix();
    const { session, setSession } = useSession();
    const { predictions, setPredictions, isAbleToPost, setIsAbleToPost, isDeadlinePassed, setIsDeadlinePassed } = usePredictions();
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
        setPredictions({});
        setIsAbleToPost(false);
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
                        <Dropdown.Item id={item.id} onClick={() => setGrandPrixAndResetSession(item)}>{item.name}</Dropdown.Item>
                    ))
                }
            </DropdownButton>
            <p>{grandPrix.name}</p>
        </Fragment>
    </>);
}