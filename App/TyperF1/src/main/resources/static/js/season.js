window.addEventListener('load', getParticipantStandings);

function getParticipantStandings(){
    debugger;

    try {
        fetch('/get-participant-standings')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                debugger;
                const standings = document.getElementById("standings");
                data.forEach(item => {
                    const li = document.createElement("li");
                    li.innerText = item.participantName + " " + item.participantSurname + " " + item.pointsSum;
                    standings.appendChild(li);
                });
            })
    } catch (e) {

    }
}