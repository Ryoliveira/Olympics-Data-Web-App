import React from "react";
import logo from '../assests/svg/2020_Summer_Olympics_logo.svg';
import "../css/HomePage.css"
import {PATHS} from "../PathConstants";

export const HomePage = () => {

    return (
        <div className="center-content">
            <div>
                <div className="center-content" >
                    <img src={logo} alt={""}/>
                </div>
                <div className="center-content">
                    <a href={PATHS.ATHLETES_PATH}>Athletes</a>
                    <a href={PATHS.STANDINGS_PATH}>Standings</a>
                    <a href={PATHS.COUNTRIES_PATH}>Countries</a>
                    <a href={PATHS.SPORTS_PATH}>Sports</a>
                </div>
            </div>
        </div>

    )

}