import React, {useEffect, useState} from "react";
import "../css/CountryListPage.css";
import {Link} from "react-router-dom";


export const CountryListPage = () => {

    const [countryList, setCountryList] = useState([]);

    useEffect(() => {
            const fetchCountries = async () => {
                const response = await fetch("http://localhost:8080/countries");
                const data = await response.json();
                setCountryList(data);
            }
            fetchCountries();
        }, []
    )

    if (countryList.length < 1) {
        return (
            <h1>Loading countries</h1>
        )
    }

    return (
        <div className="container">
            <h1 className="pageHeader">Countries</h1>
            <div className="divGrid">
                {countryList.countryList.slice(1).map(country => {
                    return (
                        <div key={country.countryName}>
                            <Link to={`/country/${country.profilePageStrId}/profile`}>
                                <div>
                                    <img className={"countryFlag"} src={country.countryFlagUrl}
                                         alt={country.countryName}/>
                                </div>
                                <div>
                                    {country.countryName}
                                </div>
                            </Link>
                        </div>

                    )
                })}
            </div>
        </div>
    );
}