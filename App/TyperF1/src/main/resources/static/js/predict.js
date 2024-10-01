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
                    if (item.name === "Qualifying") {
                        li.addEventListener("click", function () {
                            printPredictionsQualifyingAndSprint(year, item.name, item.id, grandPrixId, "qualifying", grandPrixName);
                        }, false);
                    }
                    else if (item.name === "Sprint") {
                            li.addEventListener("click", function () {
                                printPredictionsQualifyingAndSprint(year, item.name, item.id, grandPrixId, "sprint", grandPrixName);
                            }, false);
                    }
                    else {
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

async function printPredictionsQualifyingAndSprint(year, sessionName, sessionId, grandPrixId, sessionType, grandPrixName) {
    if (document.getElementById("predictions") !== null) {
        document.getElementById("predictions").remove();
    }

    let wasPredicted;
    if(sessionType === "qualifying"){
        wasPredicted = await checkIfSessionWasAlreadyPredicted(year, sessionId, sessionName, grandPrixId, "qualifying", grandPrixName);
    }
    else{
        wasPredicted = await checkIfSessionWasAlreadyPredicted(year, sessionId, sessionName, grandPrixId, "sprint", grandPrixName);
    }

    document.getElementById("participant-choice-weekend").innerText = sessionName;
    if (wasPredicted === 200 || wasPredicted === 406) {
        return;
    }
    printTextFieldForStandings(sessionName);
    createJokerOption();
    createPredictButton(sessionId, grandPrixId, false);
}

function createJokerOption() {
    const divPredictions = document.getElementById("predictions");
    const jokerDiv = document.createElement("div");
    jokerDiv.id = "joker";
    const jokerLabel = document.createElement("label");
    jokerLabel.innerText = "Joker usage: ";
    const select = document.createElement("select");
    select.classList.add("form-select");
    select.id = "state";
    select.required = true;
    const optionNo = document.createElement("option");
    optionNo.innerText = "No";
    const optionYes = document.createElement("option");
    optionYes.innerText = "Yes";
    jokerDiv.appendChild(jokerLabel);
    select.appendChild(optionNo);
    select.appendChild(optionYes);
    jokerDiv.appendChild(select);
    divPredictions.appendChild(jokerDiv);
}

async function printPredictionsRace(year, sessionName, sessionId, grandPrixId) {
    if (document.getElementById("predictions") !== null) {
        document.getElementById("predictions").remove();
    }

    document.getElementById("participant-choice-weekend").innerText = sessionName;

    const wasPredicted = await checkIfSessionWasAlreadyPredicted(year, sessionId, sessionName, grandPrixId, "race");
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
    createJokerOption();
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

    const selection = document.getElementById("state");
    const selectedText = selection.options[selection.selectedIndex].innerText;
    let jokerChoice = false;
    if (selectedText === "Yes") {
        jokerChoice = true;
    }

    const username = JSON.parse(localStorage.getItem('user')).username;

    fetch(`/post-predictions?grandPrixId=${grandPrixId}&sessionId=${sessionId}&username=${username}&joker=${jokerChoice}`, {
        method: 'POST',
        body: predictions
    }).then(response => {
        if (!response.ok) {
            throw new Error('Error');
        }
        return response.json();
    });
}

async function checkIfSessionWasAlreadyPredicted(year, sessionId, sessionName, grandPrixId, sessionType, grandPrixName) {
    debugger;
    const username = JSON.parse(localStorage.getItem('user')).username;

    try {
        //check if participant has already predicted
        let shortcut = "";
        if (sessionType === "race") {
            shortcut = "R";
        } else if (sessionType === "qualifying") {
            shortcut = "Q";
        } else {
            shortcut = "S";
        }

        const response = await fetch(`/check-predictions-existence?sessionType=${shortcut}&year=${year}&grandPrixId=${grandPrixId}
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

            if (sessionType === "race") {
                const div = document.createElement("div");
                const label = document.createElement("label");
                div.classList.add("prediction");
                label.textContent = `Fastest lap: ${data['fastestLap']}`;
                div.appendChild(label);
                predictions.appendChild(div);
            }
            bodyContainer.appendChild(predictions);

            //here calculate points from predictions
            if (sessionType === "qualifying") {
                fetch(`/calculate-points-qualifying?grandPrixId=${grandPrixId}&sessionId=${sessionId}&username=${username}`)
                    .then(response => {
                        const status = handleResponse(response);
                        if (response.status === 200) {
                            return response.json();
                        }
                        if (status) {
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
            } else if (sessionType === "race") {
                fetch(`/calculate-points-race?grandPrixId=${grandPrixId}&sessionId=${sessionId}&username=${username}`)
                    .then(response => {
                        const status = handleResponse(response);
                        if (response.status === 200) {
                            return response.json();
                        }
                        if (status) {
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
                fetch(`/calculate-points-sprint?grandPrixId=${grandPrixId}&sessionId=${sessionId}&username=${username}&grandPrixName=${grandPrixName}`)
                    .then(response => {
                        const status = handleResponse(response);
                        if (response.status === 200) {
                            return response.json();
                        }
                        if (status) {
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
        } else if (response.status === 406) {
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
        } else if (response.status === 204) {
            return response.status;
        } else {
            throw new Error('Network response was not ok');
        }
    } catch (error) {
        console.error('Error in checking if session was already predicted:', error);
        return false;
    }
}

function handleResponse(response) {
    if (response.status === 204) {
        const div = document.createElement("div");
        const label = document.createElement("label");
        label.innerText = "Session hasn't finished yet";
        const predictions = document.getElementById("predictions");
        div.classList.add("prediction");
        div.appendChild(label);
        predictions.appendChild(div);
        return true;
    } else if (response.status === 200) {
        return true;
    }
    return false;
}