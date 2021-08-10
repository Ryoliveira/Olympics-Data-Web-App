import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";


export const SportInformationPage = () => {
    const {sport} = useParams();
    const [sportInformation, setSportInformation] = useState(null);

    useEffect(() => {
        const fetchSportInformation = async () => {
            const response = await fetch(`http://localhost:8080/${sport}/information`);
            const data = await response.json();
            setSportInformation(data);
        }
        fetchSportInformation();
    }, [sport])

    if (sportInformation == null) {
        return (
            <h1>Loading Information</h1>
        )
    }



    //Code the html here first, then later lets extract it into a component (Tab Component)
    if (sportInformation.tabs.length > 1) {
        return (
            <div>
                <h1>{sportInformation.sportName}</h1>
                <div>
                    {sportInformation.tabs.map(tab => {
                        return (
                            <div key={tab.tabName}>
                                <h1>{tab.tabName}</h1>
                                <div>
                                    {tab.articleList.map((article, i) => {
                                        return (
                                            <div key={i}>
                                                <h3>{article.title}</h3>
                                                <div>
                                                    {article.contents.map((content, i) => {
                                                        return (
                                                            <div>
                                                                <p key={i}>{content}</p>
                                                            </div>
                                                        )
                                                    })}
                                                </div>
                                            </div>
                                        )
                                    })}
                                </div>
                            </div>
                        )
                    })}
                </div>
            </div>
        )

    } else {
        return (
            <div>
                <div>
                    <h1>{sportInformation.sportName}</h1>
                    <div>
                        {sportInformation.tabs[0].articleList.map((article, i) => {
                            return(
                                <div key={i}>
                                    <h3>{article.title}</h3>
                                    {article.contents.map((content, i) => {
                                        return (
                                            <p key={i}>{content}</p>
                                        )
                                    })}
                                </div>
                            )
                        })}
                    </div>
                </div>


            </div>
        )
    }


}