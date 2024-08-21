window.addEventListener('load', printNavbar);

function printNavbar() {
    if (localStorage.getItem('user') !== null) {
        const navbarElements = document.getElementById("navbar-elements");
        createTag(navbarElements, 'results', 'Results')
        createTag(navbarElements, 'standings', 'Standings')
        createTag(navbarElements, 'world-records', 'WorldRecords')
        createTag(navbarElements, 'personal-best', "Personal Best")
        createTag(navbarElements, 'participants', "Participants")
        createTag(navbarElements, 'predict', "Predict")

        createLogOutButton();
        printParticipant(JSON.parse(localStorage.getItem('user')).username);

    } else {
        createLoginAndRegisterButton();
    }

    checkSubpage();
}

function createTag(navbarElements, id, name) {
    const statistics = document.createElement("li");
    const a = document.createElement("a");
    a.setAttribute('href', id);
    a.classList.add("nav-link");
    a.innerText = name;
    statistics.appendChild(a);
    navbarElements.appendChild(statistics);
}

function checkSubpage() {
    const links = document.getElementsByClassName('nav-link');
    for (let i = 0; i < links.length; i++) {
        if (links[i].href === window.location.href) {
            links[i].classList.add("link-secondary");
        }
    }
}

function createLogOutButton() {
    const navbarHeader = document.getElementById("navbar-container");
    const div = document.createElement("div");
    div.classList.add("col-md-3", "text-end");
    const aButton = document.createElement("a");
    aButton.setAttribute("onclick", "logout()");
    aButton.classList.add("btn", "btn-outline-primary", "me-2");
    aButton.id = "logout";
    aButton.innerText = "Log-out";
    div.appendChild(aButton);
    navbarHeader.appendChild(div);
}

function createLoginAndRegisterButton() {
    const navbarHeader = document.getElementById("navbar-container");
    const div = document.createElement("div");
    div.classList.add("col-md-3", "text-end");
    const a = document.createElement("a");
    a.setAttribute('href', 'sign-in');
    a.classList.add("btn", "btn-outline-primary", "me-2");
    a.id = "login";
    a.innerText = "Login";
    div.appendChild(a);
    const button = document.createElement("a");
    button.classList.add("btn", "btn-primary");
    button.id = "login";
    button.type = "button";
    button.innerText = "Sign-up";
    button.setAttribute('href', 'register');
    div.appendChild(button);
    navbarHeader.appendChild(div);
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = '/';
}

function printParticipant(username) {
    debugger;
    fetch(`/get-full-name?username=${username}`)
        .then(response => {
            debugger;
            if (!response.ok) {
                throw new Error('Error');
            }
            return response.text();
        })
        .then(data => {
            debugger;
            document.getElementById("welcome").innerText = "Welcome, " + data + ", in the place for the real Formula 1 lovers!";
        })

}