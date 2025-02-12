import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

import logo from '../assets/logo.png'

export default function SignIn() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    function submitForm() {
        try {
            const formData = {
                username: username,
                password: password
            }

            fetch(`${import.meta.env.VITE_API_BASE_URL}/check-data`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            }).then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    setError("Error: Wrong username or password");
                }
            }).then(data => {
                if (typeof data !== 'string') {
                    if (data.accessToken != null) {
                        document.cookie = data.name + "=" + (data.accessToken || "") + "; path=/";
                        navigate('/');
                    }
                    else {
                        setError("Error: Wrong username or password");
                    }
                }
            })
        }
        catch (error) {
            console.error('Error:', error);
        }
    }

    return (<>
        <main className="form-signin w-100 m-auto">
            <Form>
                <img className="mb-4" src={logo} alt="" width="72" height="72" />
                <h1 className="h3 mb-3 fw-normal">Please sign in</h1>

                <Form.Group className="form-floating">
                    <Form.Control type="text" onChange={(e) => setUsername(e.target.value)} id="floatingInput" />
                    <Form.Label htmlFor="floatingInput">Login</Form.Label>
                </Form.Group>
                <Form.Group className="form-floating">
                    <Form.Control type="password" onChange={(e) => setPassword(e.target.value)} id="floatingPassword" />
                    <Form.Label htmlFor="floatingPassword">Password</Form.Label>
                </Form.Group>

                <Form.Check type="checkbox" label="Remember me" id="flexCheckDefault" />

                <p>Don't have an account? Register <a href="/register">here</a></p>

                <Button onClick={submitForm} className="btn btn-primary w-100 py-2">Sign in</Button>

                <p id="error">{error}</p>

                <p className="mt-5 mb-3 text-body-secondary">&copy; 2020–2024</p>
            </Form>
        </main>
    </>)
}