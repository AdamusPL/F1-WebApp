import { Fragment, useEffect, useState } from "react";
import { Button, Dropdown, DropdownButton, Form } from "react-bootstrap";
import { useGrandPrix } from "./GrandPrixProvider";
import { useSession } from "./SessionProvider";
import { usePredictions } from "./PredictionProvider";

export default function PredictionFields() {

    const { predictions, setPredictions, arePredictionsPosted, setArePredictionsPosted, isDeadlinePassed, setIsDeadlinePassed } = usePredictions();
    const { grandPrix } = useGrandPrix();
    const { session } = useSession();
    const [joker, setJoker] = useState(false);

    useEffect(() => {
        if (session?.id) {
            checkPredictionExistence();
        }
    }, [session]);

    const handleChange = (id, value) => {
        console.log(predictions);
        setPredictions(prevState => {
            const newDrivers = [...prevState.drivers];
            newDrivers[id - 1] = value;

            return {
                ...prevState,
                drivers: newDrivers
            };
        });
    };

    const handleFLChange = (value) => {

        console.log(predictions);
        setPredictions(prevState => {
            return {
                ...prevState,
                fastestLap: value
            };
        });
    };

    const renderInputsPredictions = () => {
        return predictions?.drivers?.map((prediction, index) => (
            <p key={index}>{index + 1}. {prediction}</p>
        ));
    };

    const renderInputs = () => {
        const inputs = [];
        for (let i = 1; i <= 20; i++) {
            inputs.push(
                <div key={i} className="d-flex align-items-center mb-2">
                    <span className="me-2">{i}.</span>
                    <Form.Group className="flex-grow-1">
                        <Form.Control onChange={(e) => handleChange(i, e.target.value)} id={`f-${i}`} />
                    </Form.Group>
                </div>
            );
        }
        return inputs;
    };

    async function checkPredictionExistence() {
        const year = 2024;
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/check-predictions-existence?sessionType=${session.name}&year=${year}&grandPrixId=${grandPrix.id}&sessionId=${session.id}`, {
            credentials: 'include'
        });
        //predictions exist, deadline passed
        if (response.status === 200) {
            setArePredictionsPosted(true);
            setIsDeadlinePassed(true);
            const data = await response.json();
            setPredictions(data);
            console.log(data);
        }
        //predictions exist, deadline not passed
        else if (response.status === 204) {
            setArePredictionsPosted(true);
            setIsDeadlinePassed(false);
            const data = await response.json();
            setPredictions(data);
        }
        //predictions don't exist, deadline not passed
        else if (response.status === 202) {
            setArePredictionsPosted(false);
            setIsDeadlinePassed(false);
        }
        //406, predictions do not exist, deadline passed
        else {
            setArePredictionsPosted(false);
            setIsDeadlinePassed(true);
        }
    }

    async function submitPredictions() {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/post-predictions?grandPrixId=${grandPrix.id}&sessionId=${session.id}&joker=${joker}`, {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(predictions)
        });

        if (response.ok) {
            setArePredictionsPosted(true);
        }
    }

    return (
        <>
            {!arePredictionsPosted && !isDeadlinePassed ?
                (<Fragment>
                    {renderInputs()}
                    <DropdownButton
                        id='season-year'
                        title='Joker'
                    >
                        <Dropdown.Item onClick={() => setJoker(true)}>Yes</Dropdown.Item>
                        <Dropdown.Item onClick={() => setJoker(false)}>No</Dropdown.Item>
                    </DropdownButton>
                    <p>{joker ? "Yes" : "No"}</p>
                    {session.name === 'Race' ?
                        <div className="d-flex align-items-center mb-2">
                            <span className="me-2">Fastest Lap:</span>
                            <Form.Group className="flex-grow-1">
                                <Form.Control onChange={(e) => handleFLChange(e.target.value)} type="fastestLap" />
                            </Form.Group>
                        </div>
                        :
                        null
                    }
                    <Button type="button" onClick={submitPredictions}>Send predictions</Button>
                </Fragment>)
                :
                (arePredictionsPosted ? <Fragment>
                    {renderInputsPredictions()}
                    {predictions?.jokerUsed ? <p>Joker used: Yes</p> : <p>Joker used: No</p>}
                    {predictions?.fastestLap ?
                        <p>Fastest Lap: {predictions.fastestLap}</p>
                        : null}
                    {isDeadlinePassed ? <p>Points gained by participant: {predictions.points}</p>
                        : <p>Session hasn't finished yet</p>}
                </Fragment>
                    :
                    (session.id ? <p>You cannot post predictions for this session anymore!</p>
                        : null
                    )
                )
            }
        </>)
}