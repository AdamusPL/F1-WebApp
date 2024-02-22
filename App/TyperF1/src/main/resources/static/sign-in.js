function submitForm() {
    const username = document.getElementById('floatingInput').value;
    const password = document.getElementById('floatingPassword').value;

    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    fetch('/sign-in', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                // Redirect to another page after successful login
                window.location.href = '/'; // Assuming you have a /home endpoint mapped
            } else {
                // Handle error response
                console.error('Login failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}