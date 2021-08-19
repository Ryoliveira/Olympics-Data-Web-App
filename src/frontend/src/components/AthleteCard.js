import React from 'react'
import '../css/AthleteCard.css'
import {Link} from "react-router-dom";

export const AthleteCard = ({athlete}) => {


    return (
        <div>
            <Link to={`/athlete/${athlete.name}`}>
                <div className="athletePhoto">
                    <img src={athlete.photoUrl} alt={""}/>
                </div>
                <div className="FlagAndName">
                    <img src={athlete.countryFlagUrl} alt={""}/>
                    <h5>{athlete.name}</h5>
                </div>
            </Link>
        </div>
    )
}