import { createContext, useContext, useState } from "react";

const GrandPrixContext = createContext();

export function GrandPrixProvider({ children }) {
    const [grandPrix, setGrandPrix] = useState("");

    return (
        <GrandPrixContext.Provider value={{ grandPrix, setGrandPrix, }}>
            {children}
        </GrandPrixContext.Provider>
    );
}

export function useGrandPrix() {
    return useContext(GrandPrixContext);
}