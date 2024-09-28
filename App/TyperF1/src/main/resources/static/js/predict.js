function getSessions(grandPrixId, grandPrixName) {
    const year = 2024;
    try {
        if (document.getElementById("predictions") !== null) {
            document.getElementById("predictions").remove();
        }

        if (document.getElementById("participant-choice-weekend") !== null) {
            document.getElementById("participant-choice-weekend").remove();
            document.getElementById("dropdown-sessions").remove();
        }

        fetch(`/get-sessions?grandPrixId=${grandPrixId}`).then(response => {
            if (!response.ok) {
                throw new Error('Error');
            }
            return response.json();
        })
            .then(data => {
                document.getElementById("participant-choice").innerText = grandPrixName;
                const div = document.getElementById("body-container");
                const dropdown = document.createElement("div");
                dropdown.classList.add("dropdown");
                const a = document.createElement("a");
                a.id = "participant-choice-weekend";
                a.classList.add("d-block", "link-body-emphasis", "text-decoration-none", "dropdown-toggle");
                a.ariaExpanded = "false";
                a.dataset.bsToggle = "dropdown";
                a.innerText = "Choose session";
                dropdown.appendChild(a);
                const ul = document.createElement("ul");
                ul.id = "dropdown-sessions";
                ul.classList.add("dropdown-menu", "text-small");
                data.forEach(item => {
                    const li = document.createElement("li");
                    li.innerText = item.name;
                    if (item.name === "Sprint" || item.name === "Qualifying") {
                        li.addEventListener("click", function () {
                            printPredictionsQualifyingAndSprint(year, item.name, item.id, grandPrixId);
                        }, false);
                    } else {
                        li.addEventListener("click", function () {
                            printPredictionsRace(year, item.name, item.id, grandPrixId);
                        }, false);
                    }
                    ul.appendChild(li);
                })
                dropdown.appendChild(ul);
                div.appendChild(dropdown);
            })
    } catch (e) {

    }
}

async function printPredictionsQualifyingAndSprint(year, sessionName, sessionId, grandPrixId) {
    if (document.getElementById("predictions") !== null) {
        document.getElementById("predictions").remove();
    }
    const wasPredicted = await checkIfSessionWasAlreadyPredicted(year, sessionId, sessionName, grandPrixId, false);
    document.getElementById("participant-choice-weekend").innerText = sessionName;
    if (wasPredicted === 200 || wasPredicted === 406) {
        return;
    }
    printTextFieldForStandings(sessionName);
    createPredictButton(sessionId, grandPrixId, false);
}

async function printPredictionsRace(year, sessionName, sessionId, grandPrixId) {
    if (document.getElementById("predictions") !== null) {
        document.getElementById("predictions").remove();
    }

    document.getElementById("participant-choice-weekend").innerText = sessionName;

    const wasPredicted = await checkIfSessionWasAlreadyPredicted(year, sessionId, sessionName, grandPrixId, true);
    if (wasPredicted === 200 || wasPredicted === 406) {
        return;
    }
    printTextFieldForStandings(sessionName);
    const divPredictions = document.getElementById("predictions");
    const divPrediction = document.createElement("div");
    divPrediction.classList.add("prediction");
    const label = document.createElement("label");
    label.innerText = "Fastest lap: ";
    const input = document.createElement("input");
    input.type = "text";
    input.id = "fastest-lap";
    input.classList.add("form-control");
    divPrediction.appendChild(label);
    divPrediction.appendChild(input);
    divPredictions.appendChild(divPrediction);
    createPredictButton(sessionId, grandPrixId, true);
}

function printTextFieldForStandings(sessionName) {
    const div = document.createElement("div");
    div.id = "predictions";
    for (var i = 0; i < 20; i++) {
        const divPrediction = document.createElement("div");
        divPrediction.classList.add("prediction");
        const label = document.createElement("label");
        label.innerText = i + 1 + ".";
        const input = document.createElement("input");
        input.type = "text";
        input.classList.add("form-control");
        input.id = "prediction-" + (i + 1).toString();
        divPrediction.appendChild(label);
        divPrediction.appendChild(input);
        div.appendChild(divPrediction);
    }
    const divContainer = document.getElementById("body-container");
    divContainer.appendChild(div);
}

function createPredictButton(sessionId, grandPrixId, isRace) {
    const divContainer = document.getElementById("predictions");
    const div = document.createElement("div");
    div.id = "button-div";
    const button = document.createElement("a");
    button.classList.add("btn", "btn-primary");
    button.id = "post-predictions";
    button.type = "button";
    button.innerText = "Post predictions";
    button.addEventListener("click", function () {
        postPredictions(grandPrixId, sessionId, isRace);
    }, false);
    div.appendChild(button);
    divContainer.appendChild(div);
}

function postPredictions(grandPrixId, sessionId, isRace) {
    const predictions = new FormData();
    for (var i = 1; i <= 20; i++) {
        const id = "prediction-" + i;
        const prediction = document.getElementById(id);
        predictions.append("driver" + i, prediction.value);
    }

    if (isRace) {
        const fastestLap = document.getElementById("fastest-lap");
        predictions.append("fastestLap", fastestLap.value);
    }

    const username = JSON.parse(localStorage.getItem('user')).username;

    fetch(`/post-predictions?grandPrixId=${grandPrixId}&sessionId=${sessionId}&username=${username}`, {
        method: 'POST',
        body: predictions
    }).then(response => {
        if (!response.ok) {
            throw new Error('Error');
        }
        return response.json();
    });
}

async function checkIfSessionWasAlreadyPredicted(year, sessionId, sessionName, grandPrixId, isRace) {
    const username = JSON.parse(localStorage.getItem('user')).username;

    try {
        //check if participant has already predicted
        const response = await fetch(`/check-predictions-existence?year=${year}&grandPrixId=${grandPrixId}
        &sessionId=${sessionId}&username=${username}`);

        if (response.status === 200) {
            const data = await response.json();
            const bodyContainer = document.getElementById("body-container");
            const predictions = document.createElement("div");
            predictions.id = "predictions";

            for (let i = 1; i <= 20; i++) {
                const div = document.createElement("div");
                const label = document.createElement("label");
                div.classList.add("prediction");
                label.textContent = `${i}. ${data['driver' + i]}`;
                div.appendChild(label);
                predictions.appendChild(div);
            }

            if (isRace) {
                const div = document.createElement("div");
                const label = document.createElement("label");
                div.classList.add("prediction");
                label.textContent = `Fastest lap: ${data['fastestLap']}`;
                div.appendChild(label);
                predictions.appendChild(div);
            }
            bodyContainer.appendChild(predictions);

            //here calculate points from predictions
            if (!isRace) {
                fetch(`/calculate-points-qualifying?grandPrixId=${grandPrixId}&sessionId=${sessionId}&username=${username}`)
                    .then(response => {
                        const status = handleResponse(response);
                        if(response.status === 200){
                            return response.json();
                        }
                        if(status){
                            return;
                        }
                        throw new Error('Error');
                    })
                    .then(data => {
                        if (data !== undefined) {
                            const div = document.createElement("div");
                            const label = document.createElement("label");
                            label.innerText = "Points gained by participant: " + data;
                            const predictions = document.getElementById("predictions");
                            div.classList.add("prediction");
                            div.appendChild(label);
                            predictions.appendChild(div);
                        }
                    });
            } else {
                fetch(`/calculate-points-race?grandPrixId=${grandPrixId}&sessionId=${sessionId}&username=${username}`)
                    .then(response => {
                        const status = handleResponse(response);
                        if(response.status === 200){
                            return response.json();
                        }
                        if(status){
                            return;
                        }
                        throw new Error('Error');
                    })
                    .then(data => {
                        if (data !== undefined) {
                            const div = document.createElement("div");
                            const label = document.createElement("label");
                            label.innerText = "Points gained by participant: " + data;
                            const predictions = document.getElementById("predictions");
                            div.classList.add("prediction");
                            div.appendChild(label);
                            predictions.appendChild(div);
                        }
                    });
            }
            return response.status;
        }
        else if(response.status === 406){
            const div = document.createElement("div");
            div.id = "predictions";
            const label = document.createElement("label");
            const divInfo = document.createElement("div");
            divInfo.classList.add("prediction");
            label.innerText = "You cannot post predictions for this session anymore!";
            const bodyContainer = document.getElementById("body-container");
            divInfo.appendChild(label);
            div.appendChild(divInfo);
            bodyContainer.appendChild(div);
            return response.status;
        }
        else if (response.status === 204) {
            return response.status;
        } else {
            throw new Error('Network response was not ok');
        }
    } catch (error) {
        console.error('Error in checking if session was already predicted:', error);
        return false;
    }
}

function handleResponse(response){
    if (response.status === 204) {
        const div = document.createElement("div");
        const label = document.createElement("label");
        label.innerText = "Session hasn't finished yet";
        const predictions = document.getElementById("predictions");
        div.classList.add("prediction");
        div.appendChild(label);
        predictions.appendChild(div);
        return true;
    }
     else if (response.status === 200) {
        return true;
    }
    return false;
}