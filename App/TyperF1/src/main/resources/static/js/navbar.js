window.addEventListener('load', printNavbar);

function printNavbar() {
    if (localStorage.getItem('user') !== null) {
        const navbarElements = document.getElementById("navbar-elements");
        createTag(navbarElements, 'results', 'Results')
        createTag(navbarElements, 'standings', 'Standings')
        createTag(navbarElements, 'world-records', 'WorldRecords')
        createTag(navbarElements, 'personal-best', "Personal Best")
        createTag(navbarElements, 'participants', "Participants")
        createTag(navbarElements, 'bet', "Bet")

        createLogOutButton();
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
    debugger;
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
    aButton.style.marginTop = '20px';
    aButton.innerText = "Log-out";
    div.appendChild(aButton);
    navbarHeader.appendChild(div);
}

function createLoginAndRegisterButton() {
    const navbarHeader = document.getElementById("navbar-header");
    const div = document.createElement("div");
    div.classList.add("col-md-3", "text-end");
    const a = document.createElement("a");
    a.setAttribute('href', 'sign-in');
    a.classList.add("btn", "btn-outline-primary", "me-2");
    a.innerText = "Login";
    div.appendChild(a);
    const button = document.createElement("button");
    button.classList.add("btn", "btn-primary");
    button.type = "button";
    button.innerText = "Sign-up";
    div.appendChild(button);
    navbarHeader.appendChild(div);
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = '/';
}