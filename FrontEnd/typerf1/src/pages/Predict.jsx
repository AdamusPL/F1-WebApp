export default function Predict() {

    function getSessions(grandPrixId, grandPrixName) {
    const year = 2024;
    try {
        fetch(`/get-sessions?grandPrixId=${grandPrixId}`).then(response => {
            if (!response.ok) {
                throw new Error('Error');
            }
            return response.json();
        })
            .then(data => {
                data.forEach(item => {
                    
                })
            })
    } catch (e) {

    }
}

    return (<>
        <h1>Predict</h1>
    </>);
}