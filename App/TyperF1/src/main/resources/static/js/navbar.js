window.addEventListener('load', printNavbar);

function printNavbar() {
    validateParticipant()
        .then(isValid => {
            if (isValid) {
                const navbarElements = document.getElementById("navbar-elements");
                createTag(navbarElements, 'results', 'Results')
                createTag(navbarElements, 'standings', 'Standings')
                createTag(navbarElements, 'world-records', 'WorldRecords')
                createTag(navbarElements, 'personal-best', "Personal Best")
                createTag(navbarElements, 'participants', "Participants")
                createTag(navbarElements, 'predict', "Predict")

                createLogOutButton();

            } else {
                createLoginAndRegisterButton();
            }
            checkSubpage();
        });
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
    document.cookie = "token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
    window.location.href = '/';
}

function validateParticipant() {
    return fetch(`/get-full-name`, {
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                return false;
            }
            return response.text();
        })
        .then(data => {
            if(data !== false) {
                if(document.getElementById("welcome") !== null) {
                    document.getElementById("welcome").innerText = "Welcome, " + data + ", in the place for the real Formula 1 lovers!";
                }
                return true;
            }
        })
}