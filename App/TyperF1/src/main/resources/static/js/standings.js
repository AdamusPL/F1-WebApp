window.addEventListener('load', getParticipantStandings);

function getParticipantStandings(year){
    if(document.getElementById("standings") !== null){
        document.getElementById("standings").remove();
    }
    try {
        fetch(`/get-participant-standings?year=${year}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                const standings = document.createElement("ol");
                standings.id = "standings";
                data.forEach(item => {
                    const li = document.createElement("li");
                    li.innerText = item.participantName + " " + item.participantSurname + " " + item.pointsSum + " ";
                    for(var i = 0; i < item.numberOfJokersUsed; i++){
                        li.innerText += "J";
                    }
                    standings.appendChild(li);
                });
                document.getElementById("statistics").appendChild(standings);
                document.getElementById("season-year").innerText = year;
            })
    } catch (e) {

    }
}