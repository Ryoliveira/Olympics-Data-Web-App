import React from 'react';
import '../css/Card.css'

export const InfoCard = ({topic, cardInfo}) => {

    return (
        <div className="Card">
            <h1>{topic}</h1>
            {cardInfo.map(info => {
                return (
                    <div className={"CardContent"}>
                        <label>{info.label}</label>
                        <p>{info.text}</p>
                    </div>
                )

            })}
        </div>
    )
}