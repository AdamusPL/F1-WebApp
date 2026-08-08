import { createContext, useContext, useState, React } from "react";

const PredictionContext = createContext();

export function PredictionProvider({ children }) {
    const [predictions, setPredictions] = useState({drivers: [], fastestLap: ""});
    const [arePredictionsPosted, setArePredictionsPosted] = useState(false);
    const [isDeadlinePassed, setIsDeadlinePassed] = useState(true);

    return (
        <PredictionContext.Provider value={{ predictions, setPredictions, arePredictionsPosted, setArePredictionsPosted, isDeadlinePassed, setIsDeadlinePassed }}>
            {children}
        </PredictionContext.Provider>
    );
}

export const usePredictions = () => useContext(PredictionContext);