import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';


export const MedalStandingTable = () => {

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

    if(teamList.length === 0){
        return(
            <div>
                <h1>Loading Table</h1>
            </div>
        )
    }

    return (
        <div>
            <h1>Teams</h1>
            {teamList.teams.map(team => <h1>{team.teamName}</h1>)}
        </div>
    );
}