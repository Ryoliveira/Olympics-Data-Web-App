import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import "./MedalStandingsPage.css";
import {SportDropdown} from "../components/SportDropdown";
import {Pagination} from "../components/Pagination";


export const AthleteTable = () => {

    const [athleteList, setAthleteList] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const {sport} = useParams();

    useEffect(() => {
            const fetchTeams = async () => {
                const response = await fetch(`http://localhost:8080/athletes?page=${currentPage}&size=${pageSize}`);
                const data = await response.json();
                setAthleteList(data);
                console.log(athleteList);
            };
            fetchTeams();
        }, [pageSize, currentPage]
    );

    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    const changePageSize = (pageSize) => setPageSize(pageSize);

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
            <SportDropdown />
            <table className="MedalTable">
                <caption>Athletes</caption>
                <tbody>
                <tr>
                    <th>Name</th>
                    <th>Country</th>
                    <th>Sport</th>
                </tr>
                {athletes.map(athlete => {
                        return( <tr>
                            <td>{athlete.name}</td>
                            <td>{athlete.country}</td>
                            <td>{athlete.discipline}</td>
                        </tr> )
                    }
                )}
                </tbody>
            </table>
            <Pagination pageSize={pageSize} totalPages={athleteList.totalPages} paginate={paginate} changePageSize={changePageSize}/>
            <footer className="FooterInfo">Data provided by <a href={"https://olympics.com"}>olympics.com</a></footer>
        </div>
    );
}