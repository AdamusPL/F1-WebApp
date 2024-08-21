function submitForm(e) {
    e.preventDefault();
    try {
        debugger;
        const firstName = document.getElementById('floatingInputFirstName').value;
        const surname = document.getElementById('floatingInputSurname').value;
        const username = document.getElementById('floatingInput').value;
        const password = document.getElementById('floatingPassword').value;
        const confirmPassword = document.getElementById('floatingConfirmPassword').value;
        const email = document.getElementById('floatingEmail').value;
        const description = document.getElementById('floatingDescription').value;
        const profilePicture = document.getElementById('floatingImage').files[0];

        if(password !== confirmPassword){
            document.getElementById("error").innerText = "Error: Passwords don't match"
            return;
        }

        const formData = new FormData();
        formData.append('firstName', firstName);
        formData.append('surname', surname);
        formData.append('username', username);
        formData.append('email', email);
        formData.append('password', password);
        formData.append('description', description);
        formData.append('profilePicture', profilePicture);

        fetch('/register-user', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                document.getElementById("error").innerText = "Successfully registered a new user"
            } else {
                document.getElementById("error").innerText = "Error"
            }
        })
    }
    catch(error) {
        console.error('Error:', error);
    }
}