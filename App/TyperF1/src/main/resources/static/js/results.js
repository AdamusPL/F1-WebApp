function getScores(year) {
    const div = document.getElementById("statistics");
    if(div !== null){
        div.remove()
    }
    const statistics = document.createElement("statistics");
    statistics.id = "statistics";
    document.getElementById("statistics-container").appendChild(statistics);

    try {
        fetch(`/get-season-scores?year=${year}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                const statistics = document.getElementById("statistics");
                var grandPrix = "";
                var session = "";
                data.forEach(item => {
                    if (item.grandPrixName !== grandPrix) {
                        grandPrix = item.grandPrixName;
                        const h1 = document.createElement("h1");
                        h1.innerText = item.grandPrixName;
                        statistics.appendChild(h1);
                        const h2 = document.createElement("h2");
                        h2.innerText = "Summary";
                        statistics.appendChild(h2);
                        const ol = document.createElement("ol");
                        fetch(`/get-grandprix-summary?year=${year}&grandPrixName=${item.grandPrixName}`)
                            .then(insideResponse => {
                                if (!insideResponse.ok) {
                                    throw new Error('Error');
                                }
                                return insideResponse.json();
                            })
                            .then(sumData => {
                                sumData.forEach(sumItem => {
                                    const li = document.createElement("li");
                                    li.innerText = sumItem.participantName + " " + sumItem.participantSurname + " " + sumItem.pointsSum;
                                    if(sumItem.numberOfJokersUsed > 0){
                                        li.innerText += " J";
                                    }
                                    ol.appendChild(li);
                                });
                            })
                        statistics.appendChild(ol);
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
                    if(item.numberOfJokersUsed > 0){
                        li.innerText += " J";
                    }
                    document.getElementById(grandPrix + " " + session).appendChild(li);
                });
                document.getElementById("season-year").innerText = year;
            })
    } catch (e) {

    }

}