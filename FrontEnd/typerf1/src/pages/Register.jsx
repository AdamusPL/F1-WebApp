import { useState } from "react";
import { Button, Form } from "react-bootstrap";

import logo from '../assets/logo.png'
import { useNavigate } from "react-router-dom";

export default function Register() {
    const [user, setUser] = useState({
        firstName: '',
        surname: '',
        username: '',
        email: '',
        password: '',
        description: '',
        profilePicture: null
    });

    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;

        setUser(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleFileChange = (e) => {
        setUser(prev => ({ ...prev, profilePicture: e.target.files[0] }));
    };

    const navigate = useNavigate();

    function submitForm() {
        
        const formData = new FormData();

        formData.append('firstName', user.firstName);
        formData.append('surname', user.surname);
        formData.append('username', user.username);
        formData.append('email', user.email);
        formData.append('password', user.password);
        formData.append('description', user.description);

        if (user.profilePicture) {
            formData.append('profilePicture', user.profilePicture);
        }
        fetch(`${import.meta.env.VITE_API_BASE_URL}/register-user`, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to register user');
                }
                setError("Account created successfully! Redirecting to sign in page...");
            })
            .then(data => {
                navigate('/sign-in');
            })
            .catch(error => {
                console.error('Error registering user:', error);
            });
    }

    return (
        <>
            <main className="form-signin w-100 m-auto">
                <Form>
                    <img className="mb-4" src={logo} alt="" width="72" height="72" />
                    <h1 className="h3 mb-3 fw-normal">Register your account</h1>

                    <Form.Group className="form-floating">
                        <Form.Control type="text" name="firstName" onChange={handleChange} id="floatingName" />
                        <Form.Label htmlFor="floatingName">First Name*</Form.Label>
                    </Form.Group>
                    <Form.Group className="form-floating">
                        <Form.Control type="text" name="surname" onChange={handleChange} id="floatingSurname" />
                        <Form.Label htmlFor="floatingSurname">Surname*</Form.Label>
                    </Form.Group>
                    <Form.Group className="form-floating">
                        <Form.Control type="text" name="username" onChange={handleChange} id="floatingUsername" />
                        <Form.Label htmlFor="floatingUsername">Login*</Form.Label>
                    </Form.Group>
                    <Form.Group className="form-floating">
                        <Form.Control type="email" name="email" onChange={handleChange} id="floatingEmail" />
                        <Form.Label htmlFor="floatingEmail">E-mail Address*</Form.Label>
                    </Form.Group>
                    <Form.Group className="form-floating">
                        <Form.Control type="password" name="password" onChange={handleChange} id="floatingPassword" />
                        <Form.Label htmlFor="floatingPassword">Password*</Form.Label>
                    </Form.Group>
                    {/* <Form.Group className="form-floating">
                        <Form.Control type="password" name="confirmPassword" onChange={handleChange} id="floatingConfirmPassword" />
                        <Form.Label htmlFor="floatingPassword">Confirm password*</Form.Label>
                    </Form.Group> */}
                    <Form.Group className="form-floating">
                        <Form.Control type="text" name="description" onChange={handleChange} id="floatingDescription" />
                        <Form.Label htmlFor="floatingDescription">Profile description</Form.Label>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Profile Picture (.jpg format)*</Form.Label>
                        <Form.Control onChange={handleFileChange} name="profilePicture" type="file" accept="image/jpeg" id="floatingImage" />
                    </Form.Group>

                    <Button onClick={submitForm} type="button" className="btn btn-primary w-100 py-2">Create an account</Button>

                    <p>* - required fields</p>

                    <p id="error">{error}</p>

                    <p className="mt-5 mb-3 text-body-secondary">&copy; 2020–2024</p>
                </Form>
            </main>
        </>
    );
}