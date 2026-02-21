import { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';

export default function Participants() {

    const [participantsData, setParticipantsData] = useState([]);

    useEffect(() => {
        getParticipantsData();
    }, []);

    function getParticipantsData() {
        try {
            fetch(`${import.meta.env.VITE_API_BASE_URL}/get-participants-for-subpage`, {
                credentials: 'include'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error');
                    }
                    return response.json();
                })
                .then(data => {
                    
                    setParticipantsData(data);
                })
        } catch (e) {

        }
    }

    return (<>
        <h1>Participants</h1>

        <Container>
            <Row>
                {participantsData.map((p) => (
                    <Col key={p.id} xs={12} md={6} lg={4} className="mb-4">
                        <Card>
                            <Card.Img variant="top" src={`data:image/jpeg;base64,${p.profilePicture}`} />
                            <Card.Body>
                                <Card.Text>{p.description}</Card.Text>
                            </Card.Body>
                            <Card.Footer className="d-flex justify-content-between align-items-center bg-white border-top-0">
                                <div>
                                    <Button variant="outline-secondary" size="sm" className="me-1">About</Button>
                                    <Button variant="outline-secondary" size="sm">Report</Button>
                                </div>
                                <small className="text-muted">{p.name}</small>
                            </Card.Footer>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    </>);
}