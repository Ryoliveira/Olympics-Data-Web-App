import React, {useEffect, useState} from 'react';
import "./SportSelectionPage.css";

export const SportSelectionPage = () => {

    const [sportList, setSportsList] = useState([]);


    useEffect(() => {
        const fetchSports = async () => {
            const response = await fetch("http://localhost:8080/sports");
            const data = await response.json();
            setSportsList(data);
        }
        fetchSports();
    },[])

    if(sportList.length < 1){
        return(
          <div>
              <h1>Loading Sports</h1>
          </div>
        );
    }

    let sports = sportList.sports;

    return (
      <div className="SportGrid">
          {sports.slice(1).map(sport => {
              return(
                  <div className="SportItem">
                      <img src={sport.sportIconUrl} className="SportIcon"/>
                      <h3 className="SportName">{sport.sportName}</h3>
                  </div>
              );
          })}
      </div>
    );
}