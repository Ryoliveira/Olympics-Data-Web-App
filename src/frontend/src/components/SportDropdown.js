import React, {createRef, useEffect, useState} from 'react';

export const SportDropdown = ({changeSport}) => {

    const [sportsList, setSportList] = useState([]);
    const selectedSport = createRef();

    function handleChangeSport(){
        changeSport(selectedSport.current.value);
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

    const sports = sportsList.sports;

    if(sportsList.length === 0){
        return (
            <select><option>Loading</option></select>
        )
    }

    return (
        <div>
            <select className="btn btn-light btn-outline-dark" ref={selectedSport} onChange={handleChangeSport}>
                {sports.map(sport => {
                    return (
                        <option key={sport.sportName} value={sport.sportName}>{sport.sportName}</option>
                    )
                })}
            </select>
        </div>
    );
}