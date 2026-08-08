import { React } from "react";

export default function Rules() {
    return (<>
        <div className='container'>
            <h4 className="mt-4">Rules:</h4>
            <br />
            <h5>Predicting driver positions:</h5>
            <ul>
                <li className="lead">
                    Participants predict positions of all drivers taking part in specific Formula 1 session,
                    which consists of: Qualifying and Race in standard weekend; and additionally:
                    Sprint Shootout and Sprint in sprint race weekend.
                </li>
            </ul>
            <h5>Points:</h5>
            <ul>
                <li className="lead">
                    Precise prediction: If participant guessed the exact position of driver, he/she receives 2 points
                </li>
                <li className="lead">
                    Prediction missed by one position: If participant&apos;s prediction differentiate by 1 position, he/she receives 1 point
                </li>
            </ul>

            <br />
            <h6>Additional points for podium:</h6>
            <ul>
                <li className="lead">
                    First place: Additional 3 points for guessing exact driver on the first place
                </li>
                <li className="lead">
                    Second place: Additional 2 points for guessing exact driver on the second place
                </li>
                <li className="lead">
                    Third place: Additional 1 point for guessing exact driver on the third place
                </li>
            </ul>
            <p className="lead">Participants have to guess the fastest lap additionally in race session. They can get 1 point
                for that.</p>

            <br />
            <h5>Weights of sessions:</h5>
            <ul>
                <li className="lead">
                    Qualifying: x1
                </li>
                <li className="lead">
                    Sprint Shootout: x0.5
                </li>
                <li className="lead">
                    Sprint: x1
                </li>
                <li className="lead">
                    Race: x2
                </li>
            </ul>
        </div>
    </>)
}