import React, {createRef, useEffect, useState} from 'react';
import { useHistory } from 'react-router-dom';

export const SportDropdown = ({changeSport}) => {

    const [sportsList, setSportList] = useState([]);
    const selectedSport = createRef();

    function handleChangeSport(){
        let sport = selectedSport.current.value;
        changeSport(sport);
    }

    useEffect(() => {
            const fetchSportList = async () => {
                const response = await fetch(`http://localhost:8080/sports`);
                const data = await response.json();
                setSportList(data);
            };
            fetchSportList();
        }, []
    );

    let sports = sportsList.sports;

    if(sportsList.length === 0){
        return (
            <select><option>Loading</option></select>
        )
    }

    return (
        <div>
            <select ref={selectedSport}>
                {sports.map(sport => {
                    return (
                        <option value={sport}>{sport}</option>
                    )
                })}
            </select>
            <button onClick={handleChangeSport}>Go</button>
        </div>
    );
}