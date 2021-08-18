import React, {useEffect, useState} from 'react';
import "../css/AthleteTable.css"
import "../css/App.css"
import {SportDropdown} from "../components/SportDropdown";
import {Pagination} from "../components/Pagination";
import {CountryDropdown} from "../components/CountryDropdown";
import {Link} from "react-router-dom";


export const AthleteTable = () => {
    const ALL_SPORTS = "All Sports";
    const ALL_COUNTRIES = "All Countries";
    const [athleteList, setAthleteList] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [sport, setSport] = useState(ALL_SPORTS);
    const [country, setCountry] = useState(ALL_COUNTRIES)

    useEffect(() => {
            const fetchTeams = async () => {
                let url = `http://localhost:8080`;
                if (sport !== ALL_SPORTS && country !== ALL_COUNTRIES) {
                    url += `/${sport}/${country}/athletes?page=${currentPage}&size=${pageSize}`;
                } else if (sport !== ALL_SPORTS) {
                    url += `/${sport}/athletes?page=${currentPage}&size=${pageSize}`;
                } else if (country !== ALL_COUNTRIES) {
                    url += `/team/${country}/athletes?page=${currentPage}&size=${pageSize}`;
                } else {
                    url += `/athletes?page=${currentPage}&size=${pageSize}`;
                }
                const response = await fetch(url);
                const data = await response.json();
                setAthleteList(data);
            };
            fetchTeams();
        }, [pageSize, currentPage, sport, country]
    );

    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    const changePageSize = (pageSize) => {
        setPageSize(pageSize);
        setCurrentPage(0);
    }
    const changeSport = (sport) => {
        setSport(sport);
        setCurrentPage(0);
    }
    const changeCountry = (country) => {
        setCountry(country);
        setCurrentPage(0);
    }

    if (athleteList.length === 0) {
        return (
            <div>
                <h1>Loading Table</h1>
            </div>
        )
    }

    let athletes = athleteList.content;

    return (
        <div>
            <div>
                <SportDropdown changeSport={changeSport}/>
                <CountryDropdown changeCountry={changeCountry}/>
                <h1>Athletes</h1>
                <h1>Sport: {sport}</h1>
                <h1>Country: {country}</h1>
            </div>
            <table className="table table-striped">
                <tbody>
                <tr>
                    <th>Name</th>
                    <th>Country</th>
                    <th>Sport</th>
                </tr>
                {athletes.map(athlete => {
                        return (<tr key={athlete.id}>
                            <td>
                                <Link to={`/athlete/${athlete.name}`}>
                                    <img src={athlete.photoUrl} className="AthletePhoto"
                                         alt={athlete.name}/>
                                    {athlete.name}
                                </Link>
                            </td>
                            <td>
                                <img src={athlete.countryFlagUrl} className="CountryFlag" alt={athlete.country}/>
                                {athlete.country}
                            </td>
                            <td>
                                <Link to={`/${athlete.discipline}/information`}>
                                    {athlete.discipline}
                                </Link>
                            </td>

                        </tr>)
                    }
                )}
                </tbody>
            </table>
            <Pagination pageSize={pageSize} totalElements={athleteList.totalElements} paginate={paginate}
                        changePageSize={changePageSize}/>
            <br/>
        </div>
    );
}