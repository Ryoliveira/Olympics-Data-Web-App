import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from 'react-router-dom';
import "./MedalStandingsPage.css";
import {SportDropdown} from "../components/SportDropdown";

export const MedalStandingTable = () => {

    const history = useHistory();
    const [teamList, setTeamList] = useState([]);
    const {sport} = useParams();

    useEffect(() => {
            const fetchTeams = async () => {
                const response = await fetch(`http://localhost:8080/${sport}/standings`);
                const data = await response.json();
                setTeamList(data);
                console.log(teamList);
            };
            fetchTeams();
        }, [sport]
    );

    const changeSport = (sport) => {
        history.push(`/${sport}/standings`);
    }

    if (teamList.length === 0) {
        return (
            <div>
                <h1>Loading Table</h1>
            </div>
        )
    }

    let teams = teamList.teams;

    return (
        <div>
            <SportDropdown changeSport={changeSport} />
            <h1>{sport}</h1>
            <table className="table table-striped">
                <tbody>
                <tr>
                    <th>Rank</th>
                    <th>Country</th>
                    <th>Team</th>
                    <th>Gold</th>
                    <th>Silver</th>
                    <th>Bronze</th>
                    <th>Total</th>
                </tr>
                {teams.map(team => {
                       return( <tr>
                            <td>{team.rank}</td>
                           <td>{team.country}</td>
                            <td>{team.teamName}</td>
                            <td>{team.goldMedalCount}</td>
                            <td>{team.silverMedalCount}</td>
                           <td>{team.bronzeMedalCount}</td>
                            <td>{team.totalMedals}</td>
                        </tr> )
                    }
                )}
                </tbody>
            </table>
            <footer className="FooterInfo">Standings data provided by <a href={"https://olympics.com"}>olympics.com</a></footer>
        </div>
    );
}