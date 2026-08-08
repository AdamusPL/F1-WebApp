import { Fragment, useEffect, useState, React } from "react";
import { Button, Dropdown, DropdownButton, Form } from "react-bootstrap";
import { useGrandPrix } from "./GrandPrixProvider";
import { useSession } from "./SessionProvider";
import { usePredictions } from "./PredictionProvider";
import { Reorder } from "motion/react";

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

    const renderInputsPredictions = () => {
        return predictions?.drivers?.map((prediction, index) => (
            <p key={prediction.id}>{index + 1}. {prediction.name}</p>
        ));
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
            const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/get-current-driver-list`, {
                credentials: 'include'
            });
            const data = await response.json();
            setPredictions({ ...predictions, drivers: data });
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
            setPredictions(prevState => {
                return {
                    ...prevState,
                    jokerUsed: joker
                };
            })
        }
    }

    const handleFLChange = (value) => {
        setPredictions(prevState => {
            return {
                ...prevState,
                fastestLap: value
            };
        });
    };

    return (
        <>
            {!arePredictionsPosted && !isDeadlinePassed ?
                (<Fragment>
                    <div className="mb-2">
                        <p>Drag & Drop to change order</p>
                        <Reorder.Group axis="y" values={predictions.drivers} onReorder={(newDrivers) => setPredictions({ ...predictions, drivers: newDrivers })}>
                            {predictions.drivers.map((driver, i) => (
                                <Reorder.Item key={driver.id} value={driver} style={{ listStyleType: "none" }}>
                                    <div>
                                        <span>{i + 1}. </span>
                                        <span>{driver.name}</span>
                                    </div>
                                </Reorder.Item>
                            ))}
                        </Reorder.Group>
                    </div>
                    <DropdownButton
                        id='season-year'
                        title='Joker'
                    >
                        <Dropdown.Item onClick={() => setJoker(true)}>Yes</Dropdown.Item>
                        <Dropdown.Item onClick={() => setJoker(false)}>No</Dropdown.Item>
                    </DropdownButton>
                    <p className="mt-2">{joker ? "Yes" : "No"}</p>
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
                    {predictions.drivers.length !== 0 ?
                        predictions?.jokerUsed ?
                            <p>Joker used: Yes</p>
                            : <p>Joker used: No</p>
                        : null}
                    {predictions?.fastestLap ?
                        <p>Fastest Lap: {predictions.fastestLap}</p>
                        : null}
                    {predictions.drivers.length !== 0 ?
                        isDeadlinePassed ?
                            <p>Points gained by participant: {predictions.points}</p>
                            : <p>Session hasn&apos;t finished yet</p>
                        : null}
                </Fragment>
                    :
                    (session.id ? <p>You cannot post predictions for this session anymore!</p>
                        : null
                    )
                )
            }
        </>)
}