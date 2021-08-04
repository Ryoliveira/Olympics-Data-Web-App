import React from "react";



export const NavBar = () => {



    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <ul className="navbar-nav">
                <li className="nav-item active">
                    <a className="nav-link" href={"/"}>Home</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href={"/athletes"}>Athletes</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href={"/All-Sports/standings"}>Medal Standings</a>
                </li>
            </ul>
        </nav>
    )
}