import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";


export const AthletePage = () => {

    const [athlete, setAthlete] = useState(undefined);
    const {athleteName} = useParams();

    useEffect(() => {
        const fetchAthlete = async () => {
            const response = await fetch(`http://localhost:8080/athlete/${athleteName}`);
            const data = await response.json();
            setAthlete(data);
        }
        fetchAthlete();
    }, [])

    if(athlete === undefined){
        return(
            <h1>Loading Athlete Information</h1>
        )
    }

    return (
        <div>
            <h1>Name: {athlete.name}</h1>
            <h1>Age: {athlete.age}</h1>
        </div>
    )
}