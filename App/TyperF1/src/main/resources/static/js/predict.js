function getSessions(grandPrixId, grandPrixName) {
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
                            printTextFieldForSprintOrQualifying(item.name, item.id, grandPrixId);
                        }, false);
                    } else {
                        li.addEventListener("click", function () {
                            printTextFieldForRace(item.name, item.id, grandPrixId);
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

function printTextFieldForSprintOrQualifying(sessionName) {
    printTextFieldForStandings(sessionName);
    createPredictButton();
}

function printTextFieldForRace(sessionName, sessionId, grandPrixId) {
    printTextFieldForStandings(sessionName);
    const divPredictions = document.getElementById("predictions");
    const divPrediction = document.createElement("div");
    divPrediction.classList.add("prediction");
    const label = document.createElement("label");
    label.innerText = "Fastest lap: ";
    const input = document.createElement("input");
    input.type = "text";
    input.classList.add("form-control");
    divPrediction.appendChild(label);
    divPrediction.appendChild(input);
    divPredictions.appendChild(divPrediction);
    createPredictButton(sessionId, grandPrixId);
}

function printTextFieldForStandings(sessionName) {
    if (document.getElementById("predictions") !== null) {
        document.getElementById("predictions").remove();
    }
    document.getElementById("participant-choice-weekend").innerText = sessionName;
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

function createPredictButton(sessionId, grandPrixId) {
    const divContainer = document.getElementById("predictions");
    const div = document.createElement("div");
    div.id = "button-div";
    const button = document.createElement("a");
    button.classList.add("btn", "btn-primary");
    button.id = "post-predictions";
    button.type = "button";
    button.innerText = "Post predictions";
    button.addEventListener("click", function () {
        postPredictions(grandPrixId, sessionId);
    }, false);
    div.appendChild(button);
    divContainer.appendChild(div);
}

function postPredictions(grandPrixId, sessionId) {
    const predictions = new FormData();
    for (var i = 1; i <= 20; i++) {
        const id = "prediction-" + i;
        const prediction = document.getElementById(id);
        predictions.append("driver" + i, prediction.value);
    }

    const username = JSON.parse(localStorage.getItem('user')).username;

    debugger;
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