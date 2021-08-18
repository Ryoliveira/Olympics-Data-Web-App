import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import "../css/AthletePage.css"


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

    if (athlete === undefined) {
        return (
            <h1>Loading Athlete Information</h1>
        )
    }

    return (
        <div className="container">
            <div className="playerBio">
                <div>
                    <img className="image" src={athlete.photoUrl} alt={""}/>
                </div>
                <div className="athleteDetails">
                    {athlete.name && <p><label>Name:</label>{athlete.name}</p>}
                    {athlete.age && <p><label>Age:</label> {athlete.age}</p>}
                    {athlete.gender && <p><label>Gender:</label> {athlete.gender}</p>}
                    {athlete.dob && <p><label>DOB:</label> {athlete.dob}</p>}
                    {athlete.height && <p><label>Height:</label> {athlete.height}</p>}

                </div>
                <div className="athleteDetails">
                    {athlete.discipline && <p><label>Sport:</label> {athlete.discipline}</p>}
                    {athlete.country && <p><label>Country:</label> {athlete.country}</p>}
                    {athlete.placeOfResidence && <p><label>Place Of Residence:</label> {athlete.placeOfResidence} </p>}
                    {athlete.birthCountry && <p><label>Birth Country:</label> {athlete.birthCountry}</p>}
                    {athlete.residenceCountry && <p><label>Current Residence</label> {athlete.residenceCountry}</p>}
                </div>
                <div>
                    <img className="image" src={athlete.countryFlagUrl} alt={""}/>
                </div>
            </div>
            <div className="additionalInfo">
                {athlete.additionalInfo.map((info, index) => {
                    return (
                        <div>
                            <h5><label>{info.label}</label></h5>
                            <p>{info.text}</p>
                            {index !== athlete.additionalInfo.length - 1 && <hr/>}
                        </div>
                    )
                })}
            </div>
        </div>
    )
}