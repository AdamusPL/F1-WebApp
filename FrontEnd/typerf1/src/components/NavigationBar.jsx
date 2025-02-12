import { useEffect, useState } from 'react';
import logo from '../assets/logo.png';
import { useNavigate } from 'react-router-dom';

import { Button, Navbar, Nav, Container } from 'react-bootstrap';

import '../css/Navbar.css';
import { Link } from 'react-router-dom';

export default function NavigationBar({ children }) {
    const navigate = useNavigate();

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [menuOpen, setMenuOpen] = useState(false);

    const toggleMenu = () => {
        setMenuOpen(!menuOpen);
    };

    function logout() {
        document.cookie = "token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
        window.location.href = '/';
    }

    return (<>
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <a href="/">
                    <svg width="75" height="75" role="img">
                        <image href={logo} width="75" height="75" alt="logo" />
                    </svg>
                </a>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/rules">Rules</Nav.Link>
                        <Nav.Link href="/about">About</Nav.Link>
                        <Nav.Link href="/results">Results</Nav.Link>
                        <Nav.Link href="/standings">Standings</Nav.Link>
                        <Nav.Link href="/standings">WorldRecords</Nav.Link>
                        <Nav.Link href="/personal-best">Personal Best</Nav.Link>
                        <Nav.Link href="/participants">Participants</Nav.Link>
                        <Nav.Link href="/predict">Predict</Nav.Link>

                        <Button id='login' variant='outline-primary' onClick={() => navigate('/sign-in')}>Log in</Button>
                        <Button id='login' onClick={() => navigate('/register')}>Sign up</Button>
                        <Button id='logout' variant='outline-primary' onClick={logout}>Log-out</Button>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
        <main>{children}</main>
    </>);
}