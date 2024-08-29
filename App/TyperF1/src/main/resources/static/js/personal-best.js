function getRecords(firstName, surname) {
    const div = document.getElementById("statistics");
    if(div !== null){
        div.remove()
    }
    const statistics = document.createElement("statistics");
    statistics.id = "statistics";
    document.getElementById("statistics-container").appendChild(statistics);

    try {
        fetch(`/get-personal-best?firstName=${firstName}&surname=${surname}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error');
                }
                return response.json();
            })
            .then(data => {
                const statistics = document.getElementById("statistics");

                const h3 = document.createElement("h3");
                h3.innerText = "Best";
                statistics.appendChild(h3);

                createRecord("The highest number of points gained with prediction on Race: ", "highest-race", statistics)
                createRecord("The highest number of points gained with prediction on Qualifying: ", "highest-qualifying", statistics)
                createRecord("The highest number of points gained with prediction on Sprint: ", "highest-sprint", statistics)

                const h3Worst = document.createElement("h3");
                h3Worst.innerText = "Worst";
                statistics.appendChild(h3Worst);

                createRecord("The lowest number of points gained with prediction on Race: ", "lowest-race", statistics)
                createRecord("The lowest number of points gained with prediction on Qualifying: ", "lowest-qualifying", statistics)
                createRecord("The lowest number of points gained with prediction on Sprint: ", "lowest-sprint", statistics)

                for (const key in data) {
                    if (data.hasOwnProperty(key)) {
                        const record = data[key];
                        const highest = document.getElementById(key);
                        highest.innerText = record.points + " (" + record.grandPrixName + " " + record.year + ")";
                    }
                }
                document.getElementById("participant-choice").innerText = firstName + ' ' + surname;
            })
    } catch (e) {

    }

}

function createRecord(text, id, statistics){
    const li = document.createElement("li");
    const a = document.createElement("a");

    li.innerText = text;
    a.id = id;

    li.appendChild(a);
    statistics.appendChild(li);
}