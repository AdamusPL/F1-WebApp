window.addEventListener('load', getParticipantStandings);

function getParticipantStandings(){
    try {
        fetch('/get-participant-standings')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                const standings = document.getElementById("standings");
                data.forEach(item => {
                    const li = document.createElement("li");
                    li.innerText = item.participantName + " " + item.participantSurname + " " + item.pointsSum + " ";
                    for(var i = 0; i < item.numberOfJokersUsed; i++){
                        li.innerText += "J";
                    }
                    standings.appendChild(li);
                });
            })
    } catch (e) {

    }
}