function submitForm(e) {
    e.preventDefault();
    try {
        const username = document.getElementById('floatingInput').value;
        const password = document.getElementById('floatingPassword').value;

        const formData = {
            username: username,
            password: password
        }

        fetch('/check-data', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }).then(response => {
            if (response.ok) {
                localStorage.setItem('user', JSON.stringify(formData));
                window.location.href = '/';
            } else {
                document.getElementById("error").innerText = "Error: Wrong username or password"
            }
        })
    }
    catch(error) {
        console.error('Error:', error);
    }
}