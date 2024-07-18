function getScores(year) {
    debugger;
    try {
        fetch(`/get-season-scores?year=${year}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                debugger;
                const statistics = document.getElementById("statistics");
                var grandPrix = "";
                var session = "";
                data.forEach(item => {
                    if (item.grandPrixName !== grandPrix) {
                        grandPrix = item.grandPrixName;
                        const h1 = document.createElement("h1");
                        h1.innerText = item.grandPrixName;
                        statistics.appendChild(h1);
                    }
                    if (item.sessionName !== session) {
                        session = item.sessionName;
                        const h2 = document.createElement("h2");
                        h2.innerText = item.sessionName;
                        statistics.appendChild(h2);
                        const ol = document.createElement("ol");
                        ol.id = item.grandPrixName + " " + item.sessionName;
                        statistics.appendChild(ol);
                    }
                    const li = document.createElement("li");
                    li.innerText = item.participantName + " " + item.participantSurname + " " + item.points;
                    document.getElementById(grandPrix + " " + session).appendChild(li);
                });
            })
    } catch (e) {

    }
}