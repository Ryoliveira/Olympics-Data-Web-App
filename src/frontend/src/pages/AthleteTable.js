import React, {useEffect, useState} from 'react';
import "./AthleteTable.css"
import "../App.scss"
import {SportDropdown} from "../components/SportDropdown";
import {Pagination} from "../components/Pagination";
import {CountryDropdown} from "../components/CountryDropdown";


export const AthleteTable = () => {
    const allSportsStr = "All Sports";
    const [athleteList, setAthleteList] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [sport, setSport] = useState(allSportsStr);
    const [country, setCountry] = useState("All")

    useEffect(() => {
            const fetchTeams = async () => {
                let url;
                if(sport !== allSportsStr){
                    url = `http://localhost:8080/${sport}/athletes?page=${currentPage}&size=${pageSize}`;
                }
                else if(country !== 'All'){
                    url = `http://localhost:8080/team/${country}/athletes?page=${currentPage}&size=${pageSize}`;
                }
                else{
                    url = `http://localhost:8080/athletes?page=${currentPage}&size=${pageSize}`;
                }
                const response = await fetch(url);
                const data = await response.json();
                setAthleteList(data);
                console.log(athleteList);
            };
            fetchTeams();
        }, [pageSize, currentPage, sport, country]
    );

    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    const changePageSize = (pageSize) => setPageSize(pageSize);
    const changeSport = (sport) => setSport(sport);

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
                <SportDropdown changeSport={changeSport} />
                <CountryDropdown />
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
                        return( <tr>
                            <td><img src={athlete.photoUrl} className="AthletePhoto"/>
                                {athlete.name}</td>
                            <td><img src={athlete.countryFlagUrl} className="CountryFlag"/>
                                {athlete.country}</td>
                            <td>{athlete.discipline}</td>
                        </tr> )
                    }
                )}
                </tbody>
            </table>
            <Pagination pageSize={pageSize} totalElements={athleteList.totalElements} paginate={paginate} changePageSize={changePageSize}/>
            <br/>
            <footer>Data provided by <a href={"https://olympics.com"}>olympics.com</a></footer>
        </div>
    );
}