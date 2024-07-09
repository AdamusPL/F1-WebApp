function printNavbar() {
    debugger;
    if (localStorage.getItem('user') !== null){
        const navbarElements = document.getElementById("navbar-elements");
        const statistics = document.createElement("li");
        const a = document.createElement("a");
        a.setAttribute('href', 'statistics');
        a.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/statistics'){
            a.classList.add("link-secondary");
        }
        a.innerText = "Statistics";
        statistics.appendChild(a);
        navbarElements.appendChild(statistics);

        const season = document.createElement("li");
        const aSeason = document.createElement("a");
        aSeason.setAttribute('href', 'season');
        aSeason.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/season'){
            aSeason.classList.add("link-secondary");
        }
        aSeason.innerText = "Season";
        season.appendChild(aSeason);
        navbarElements.appendChild(season);

        const participants = document.createElement("li");
        const aParticipants = document.createElement("a");
        aParticipants.setAttribute('href', 'participants');
        aParticipants.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/participants'){
            aParticipants.classList.add("link-secondary");
        }
        aParticipants.innerText = "Participants";
        participants.appendChild(aParticipants);
        navbarElements.appendChild(participants);

        const bet = document.createElement("li");
        const aBet = document.createElement("a");
        aBet.setAttribute('href', 'bet');
        aBet.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/bet'){
            aBet.classList.add("link-secondary");
        }
        aBet.innerText = "Bet";
        bet.appendChild(aBet);
        navbarElements.appendChild(bet);

        const navbarHeader = document.getElementById("navbar-header");
        const div = document.createElement("div");
        div.classList.add("col-md-3", "text-end");
        const aButton = document.createElement("a");
        aButton.setAttribute("onclick", "logout()");
        aButton.classList.add("btn", "btn-outline-primary", "me-2");
        aButton.innerText = "Log-out";
        div.appendChild(aButton);
        navbarHeader.appendChild(div);
    }

    else{
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
}

function logout(){
    localStorage.removeItem('user');
    window.location.href = '/';
}