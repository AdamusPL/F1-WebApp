function getSessions(grandPrixId, grandPrixName) {
    try {
        if(document.getElementById("predictions") !== null){
            document.getElementById("predictions").remove();
        }

        if(document.getElementById("participant-choice-weekend") !== null){
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
                debugger;
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
                        li.addEventListener("click", function(){
                            printTextFieldForSprintOrQualifying(item.name);
                        }, false);
                    } else {
                        li.addEventListener("click", function(){
                            printTextFieldForRace(item.name);
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
    if(document.getElementById("predictions") !== null){
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
        divPrediction.appendChild(label);
        divPrediction.appendChild(input);
        div.appendChild(divPrediction);
    }
    const divContainer = document.getElementById("body-container");
    divContainer.appendChild(div);
}

function printTextFieldForRace(sessionName) {
    if(document.getElementById("predictions") !== null){
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
        divPrediction.appendChild(label);
        divPrediction.appendChild(input);
        div.appendChild(divPrediction);
    }
    const divPrediction = document.createElement("div");
    divPrediction.classList.add("prediction");
    const label = document.createElement("label");
    label.innerText = "NO: ";
    const input = document.createElement("input");
    divPrediction.appendChild(label);
    divPrediction.appendChild(input);
    div.appendChild(divPrediction);
    const divContainer = document.getElementById("body-container");
    divContainer.appendChild(div);
}