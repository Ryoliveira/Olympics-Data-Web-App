import React, {createRef, useEffect, useState} from "react";


export const CountryDropdown = ({changeCountry}) => {
    const [countryList, setCountryList] = useState([]);
    const countrySelectRef = createRef();


    useEffect(() => {
            const fetchCountries = async () => {
                const response = await fetch("http://localhost:8080/countries");
                const data = await response.json();
                setCountryList(data);
            }
            fetchCountries();
        }, []
    );

    const handleChangeCountry = () => {
        changeCountry(countrySelectRef.current.value);
    }

    if (countryList.length < 1) {
        return (
            <div>
                Loading Countries
            </div>
        )
    }

    return (
        <div>
            <select className="btn btn-light btn-outline-dark" ref={countrySelectRef} onChange={handleChangeCountry}>
                {countryList.countryList.map(country => {
                    return (
                        <option key={country.countryName} value={country.countryName}>{country.countryName}</option>
                    )
                })}
            </select>
        </div>
    );

}