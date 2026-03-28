import { useState, useEffect, Fragment } from "react";
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { useYear } from "./YearProvider";

export default function SeasonYearChooser() {
    const { year, setYear } = useYear();
    const { setIsLoading } = useYear();
    const [years, setYears] = useState([]);

    useEffect(() => {
        getYears();
    }, []);

    async function getYears() {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/get-season-years`, {
            credentials: 'include'
        });
        const data = await response.json();
        setYears(data);
        setIsLoading(false);
    }

    return (<>
        <Fragment>
            <DropdownButton
                id='season-year'
                title='Choose season'
            >
                {
                    years.map(item => (
                        <Dropdown.Item id={item.id} onClick={() => setYear(item.year)}>{item.year}</Dropdown.Item>
                    ))
                }
            </DropdownButton>
            <p className="mt-2">{year != 0 ? year : null}</p>
        </Fragment>
    </>);
}