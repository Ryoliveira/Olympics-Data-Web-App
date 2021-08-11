import React from "react";
import logo from "../assests/svg/Olympic_Rings.svg"
import {PATHS} from "../PathConstants";


export const NavBar = () => {



    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <ul className="navbar-nav">
                <li className="nav-item active">
                    <a className="nav-link" href={"/"}>
                        <img src={logo} height="30px" width="auto" alt={""}/>
                    </a>
                </li>
                <li className="nav-item active">
                    <a className="nav-link" href={PATHS.HOME_PATH}>Home</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href={PATHS.ATHLETES_PATH}>Athletes</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href={PATHS.STANDINGS_PATH}>Medal Standings</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href={PATHS.COUNTRIES_PATH}>Countries</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href={PATHS.SPORTS_PATH}>Sports</a>
                </li>
            </ul>
        </nav>
    )
}