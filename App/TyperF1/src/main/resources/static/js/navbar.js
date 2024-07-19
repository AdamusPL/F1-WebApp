window.addEventListener('load', printNavbar);

function printNavbar() {
    if (localStorage.getItem('user') !== null){
        const navbarElements = document.getElementById("navbar-elements");
        const statistics = document.createElement("li");
        const a = document.createElement("a");
        a.setAttribute('href', 'results');
        a.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/results'){
            a.classList.add("link-secondary");
        }
        a.innerText = "Results";
        statistics.appendChild(a);
        navbarElements.appendChild(statistics);

        const season = document.createElement("li");
        const aSeason = document.createElement("a");
        aSeason.setAttribute('href', 'standings');
        aSeason.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/standings'){
            aSeason.classList.add("link-secondary");
        }
        aSeason.innerText = "Standings";
        season.appendChild(aSeason);
        navbarElements.appendChild(season);

        const worldRecords = document.createElement("li");
        const aWR = document.createElement("a");
        aWR.setAttribute('href', 'world-records');
        aWR.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/world-records'){
            aSeason.classList.add("link-secondary");
        }
        aWR.innerText = "WorldRecords";
        worldRecords.appendChild(aWR);
        navbarElements.appendChild(worldRecords);

        const personalBest = document.createElement("li");
        const aPB = document.createElement("a");
        aPB.setAttribute('href', 'personal-best');
        aPB.classList.add("nav-link", "px-2");
        if(window.location.pathname === '/personal-best'){
            aSeason.classList.add("link-secondary");
        }
        aPB.innerText = "Personal Best";
        personalBest.appendChild(aPB);
        navbarElements.appendChild(personalBest);

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