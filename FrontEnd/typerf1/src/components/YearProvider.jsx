import { createContext, useContext, useState } from "react";

const YearContext = createContext();

export function YearProvider({ children }) {
    const [year, setYear] = useState(0);

    return (
        <YearContext.Provider value={{ year, setYear }}>
            {children}
        </YearContext.Provider>
    );
}

export function useYear() {
    return useContext(YearContext);
}