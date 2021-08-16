import React from "react";
import "../css/Card.css"


export const LinkCard = ({topic, links}) => {


    return (
        <div className={"Card"}>
            <h1>{topic}</h1>
            {links.map(link => {
                return (
                    <div className={"CardContent"}>
                        <label>{link.label}: </label>
                        <a href={link.text.includes("http") ? link.text : "https://" + link.text}>{link.text}</a>
                    </div>
                )
            })}
        </div>
    )

}