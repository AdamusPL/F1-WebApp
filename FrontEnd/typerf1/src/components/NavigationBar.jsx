import { useEffect, useState } from 'react';
import logo from '../assets/logo.png';
import { useNavigate } from 'react-router-dom';

import { Button, Navbar, Nav, Container, Col } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import '../css/Navbar.css';

export default function NavigationBar({ children }) {
    const navigate = useNavigate();

    const [cookies, setCookie, removeCookie] = useCookies(['token']);
    const [menuOpen, setMenuOpen] = useState(false);

    const toggleMenu = () => {
        setMenuOpen(!menuOpen);
    };

    function logout() {
        removeCookie('token', { path: '/' });
    }

    return (<>
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container fluid>
                <Navbar.Brand href='/'>
                    <svg width="75" height="75" role="img">
                        <image href={logo} width="75" height="75" alt="logo" />
                    </svg>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/rules">Rules</Nav.Link>
                        <Nav.Link href="/about">About</Nav.Link>
                        {cookies.token ?
                            <>
                            <Nav.Link href="/results">Results</Nav.Link>
                            <Nav.Link href="/standings">Standings</Nav.Link>
                            <Nav.Link href="/world-records">WorldRecords</Nav.Link>
                            <Nav.Link href="/personal-best">Personal Best</Nav.Link>
                            <Nav.Link href="/participants">Participants</Nav.Link>
                            <Nav.Link href="/predict">Predict</Nav.Link>
                            </>
                        :
                            null
                        }
                    </Nav>
                    {cookies.token ?
                        <Button id='logout' variant='outline-primary' onClick={logout}>Log-out</Button>
                        :
                        (
                            <>
                                <Button id='login' variant='outline-primary' onClick={() => navigate('/sign-in')}>Log in</Button>
                                <Button id='login' onClick={() => navigate('/register')}>Sign up</Button>
                            </>
                        )
                    }

                </Navbar.Collapse>
            </Container>
        </Navbar>
        <main>{children}</main>
    </>);
}