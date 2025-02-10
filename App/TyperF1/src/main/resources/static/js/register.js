function submitForm(e) {
    e.preventDefault();
    try {
        const firstName = document.getElementById('floatingInputFirstName').value;
        if(firstName === ''){
            document.getElementById("error").innerText = "First name field is empty!";
            return;
        }
        const surname = document.getElementById('floatingInputSurname').value;
        if(surname === ''){
            document.getElementById("error").innerText = "Surname field is empty!";
            return;
        }
        const username = document.getElementById('floatingInput').value;
        if(username === ''){
            document.getElementById("error").innerText = "Username field is empty!";
            return;
        }
        const password = document.getElementById('floatingPassword').value;
        if(password === ''){
            document.getElementById("error").innerText = "Password field is empty!";
            return;
        }
        const confirmPassword = document.getElementById('floatingConfirmPassword').value;
        if(confirmPassword === ''){
            document.getElementById("error").innerText = "Confirm password field is empty!";
            return;
        }
        const email = document.getElementById('floatingEmail').value;
        if(email === ''){
            document.getElementById("error").innerText = "E-mail field is empty!";
            return;
        }
        const description = document.getElementById('floatingDescription').value;
        const profilePicture = document.getElementById('floatingImage').files[0];

        if(profilePicture === undefined){
            document.getElementById("error").innerText = "You have to upload a profile picture";
            return;
        }

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
                document.getElementById("success").innerText = "Successfully registered a new user"
            } else {
                response.text().then(data => {
                    document.getElementById("error").innerText = data;
                })
            }
        })
    }
    catch(error) {
        console.error('Error:', error);
    }
}