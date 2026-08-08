import { createContext, useContext, useState, React } from "react";

const ParticipantContext = createContext();

export function ParticipantProvider({ children }) {
    const [participant, setParticipant] = useState({});

    return (
        <ParticipantContext.Provider value={{ participant, setParticipant }}>
            {children}
        </ParticipantContext.Provider>
    );
}

export function useParticipant() {
    return useContext(ParticipantContext);
}