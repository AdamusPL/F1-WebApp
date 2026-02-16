import { createContext, useContext, useState } from "react";

const ParticipantContext = createContext();

export function ParticipantProvider({ children }) {
    const [participant, setParticipant] = useState("");
    const [isLoading, setIsLoading] = useState(true);

    return (
        <ParticipantContext.Provider value={{ participant, setParticipant, isLoading, setIsLoading }}>
            {children}
        </ParticipantContext.Provider>
    );
}

export function useParticipant() {
    return useContext(ParticipantContext);
}