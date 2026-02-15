import { createContext, useContext, useState } from "react";

const YearContext = createContext();

export function YearProvider({ children }) {
    const [year, setYear] = useState(0);
    const [isLoading, setIsLoading] = useState(true);

    return (
        <YearContext.Provider value={{ year, setYear, isLoading, setIsLoading }}>
            {children}
        </YearContext.Provider>
    );
}

export function useYear() {
    return useContext(YearContext);
}