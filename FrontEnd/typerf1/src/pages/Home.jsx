import { useEffect, useState, React } from "react"

import logo from '../assets/logo.png'
import { Container } from "react-bootstrap";

export default function Home() {
    const [participantFullName, setParticipantFullName] = useState("");

    useEffect(() => {
        checkParticipantFullName();
    }, []);

    function checkParticipantFullName() {
        fetch(`${import.meta.env.VITE_API_BASE_URL}/get-full-name`, {
            credentials: 'include'
        })
            .then(response => {
                return response.text();
            })
            .then(data => {
                if (data !== false) {
                    setParticipantFullName(data);
                }
            })
    }


    return (<>
        <Container>
            <h1 className="mt-4">F1RK88</h1>
            <h2>Season 2024</h2>
            {participantFullName ?
                <h3 id="welcome">Welcome {participantFullName}, in the place for real Formula 1 lovers!</h3>
                :
                <h3 id="welcome">Welcome in the place for real Formula 1 lovers!</h3>
            }
            <svg className="bi" width="500" height="500" role="img">
                <image href={logo} width="500" height="500" />
            </svg>
        </Container>
    </>)
}