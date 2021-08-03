import React, {useEffect, useState} from "react";


export const CountryDropdown = () => {
    const [countryList, setCountryList] = useState([]);


    useEffect(() => {
            const fetchCountries = async () => {
                //Using team standing endpoint. Need to create a endpoint that gathers just the team/country names
                const response = await fetch("http://localhost:8080/all-sports/standings");
                const data = await response.json();
                setCountryList(data);
            }
            fetchCountries();
        }, []
    );

    if (countryList.length < 1) {
        return (
            <div>
                Loading Countries
            </div>
        )
    }

    return (
        <div>
            <select>
                {countryList.teams.map(team => {
                    return (
                        <option value={team.teamName}>{team.teamName}</option>
                    )
                })}
            </select>
            <button>Change Country</button>
        </div>
    );

}