import React, {useEffect, useState} from 'react';
import "../css/AthleteTable.css"
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
        <div className="container">
            <div>
                <div id="athleteTableTitle">
                    <h1>Athletes</h1>
                </div>
                <div className="dropdownList">
                    <div className="sportDropdown">
                        <SportDropdown changeSport={changeSport}/>
                    </div>
                    <div className="countryDropdown">
                        <CountryDropdown changeCountry={changeCountry}/>
                    </div>
                </div>
            </div>
            <div>
                <table className="table table-striped athleteTable">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Country</th>
                        <th>Sport</th>
                    </tr>
                    </thead>
                    <tbody>

                    {athletes.length === 0 && <td className="noAthletes" colSpan={3}>No Athletes to display</td>}

                    {athletes.length > 0 && athletes.map(athlete => {
                            return (<tr key={athlete.id}>
                                <td>
                                    <Link to={`/athlete/${athlete.name}`} className="tableLink">
                                        <img src={athlete.photoUrl} className="athletePhoto"
                                             alt={athlete.name}/>
                                        {athlete.name}
                                    </Link>
                                </td>
                                <td>
                                    <img src={athlete.countryFlagUrl} className="countryFlag" alt={athlete.country}/>
                                    <p className="countryNameAthleteTable">{athlete.country}</p>
                                </td>
                                <td>
                                    <Link to={`/${athlete.discipline}/information`} className="tableLink">
                                       {athlete.discipline}
                                    </Link>
                                </td>

                            </tr>)
                        }
                    )}
                    </tbody>
                </table>
            </div>
            <Pagination pageSize={pageSize} totalElements={athleteList.totalElements} paginate={paginate}
                        changePageSize={changePageSize}/>
            <br/>
        </div>
    );
}