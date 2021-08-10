import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {SportInfoTabs} from "../components/SportInfoTabs";


export const SportInformationPage = () => {
    const {sport} = useParams();
    const [sportInformation, setSportInformation] = useState(null);

    useEffect(() => {
        const fetchSportInformation = async () => {
            const response = await fetch(`http://localhost:8080/${sport}/information`);
            const data = await response.json();
            setSportInformation(data);
        }
        fetchSportInformation();
    }, [sport])

    if (sportInformation == null) {
        return (
            <h1>Loading Information</h1>
        )
    }

    return (
        <div className="container">
            <h1 className="display-1">{sportInformation.sportName}</h1>
            <SportInfoTabs tabs={sportInformation.tabs}/>
        </div>
    )


}