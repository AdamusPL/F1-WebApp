import { createContext, useContext, useState } from "react";

const PredictionContext = createContext();

export function PredictionProvider({ children }) {
    const [predictions, setPredictions] = useState({});
    const [isAbleToPost, setIsAbleToPost] = useState(false);
    const [isDeadlinePassed, setIsDeadlinePassed] = useState(true);

    return (
        <PredictionContext.Provider value={{ predictions, setPredictions, isAbleToPost, setIsAbleToPost, isDeadlinePassed, setIsDeadlinePassed }}>
            {children}
        </PredictionContext.Provider>
    );
}

export const usePredictions = () => useContext(PredictionContext);