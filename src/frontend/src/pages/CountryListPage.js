import React, {useEffect, useState} from "react";
import "./CountryListPage.css";


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
        <div>
            <h1 className="pageHeader">Countries</h1>
            <div className="divGrid">
                {countryList.countryList.slice(1).map(country => {
                    return (
                        <div>
                            <div>
                                <img className={"countryFlag"} src={country.countryFlagUrl}/>
                            </div>
                            <div>
                                {country.countryName}
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    );
}