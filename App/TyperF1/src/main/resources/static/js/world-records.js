window.addEventListener('load', getWorldRecords);

function getWorldRecords() {
    debugger;
    fetch('/get-highest')
        .then(response => {
            debugger;
            if (!response.ok) {
                throw new Error('Error');
            }
            return response.json();
        })
        .then(data => {
            debugger;
            for (const key in data) {
                if (data.hasOwnProperty(key)) {
                    const record = data[key]
                    const highest = document.getElementById(key);
                    highest.innerText = record.points + " (" + record.participantName + " " + record.participantSurname + ", " + record.grandPrixName + " " + record.year + ")";
                }
            }
        })
}