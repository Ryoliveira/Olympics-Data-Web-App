import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {TableByTopic} from "../components/TableByTopic";
import {TableByDivision} from "../components/TableByDivision";
import {AthleteCard} from "../components/AthleteCard";
import {InfoCard} from "../components/InfoCard";
import '../css/CountryInfoPage.css';
import {LinkCard} from "../components/LinkCard";


export const CountryInfoPage = () => {
    const {countryProfileId} = useParams();
    const [countryInfo, setCountryInfo] = useState(undefined);

    useEffect(() => {
            const fetchCountryProfile = async () => {
                const response = await fetch(`http://localhost:8080/information/${countryProfileId}`)
                const data = await response.json();
                setCountryInfo(data);
            }
            fetchCountryProfile()
        }, [countryProfileId]
    );


    if (countryInfo === undefined) {
        return (
            <div>
                Loading Country Profile...
            </div>
        )
    }


    return (
        <div className={"container"}>
            <div className={"ProfileHeader"}>
                <h1 className={"CountryName"}>{countryInfo.countryName}</h1>
                <img src={countryInfo.countryFlagUrl}/>
            </div>

            <div className={"MainContent"}>
                <div className={"Highlights"}>
                    <h1>{countryInfo.highlights.label}</h1>
                    <p dangerouslySetInnerHTML={{__html: countryInfo.highlights.text}}/>
                </div>

                <div className={"InfoCards"}>
                    <div>
                        {
                            countryInfo.links.length > 1 &&
                            <LinkCard topic={"Additional Links"} links={countryInfo.links}/>
                        }
                    </div>
                    <div>
                        {countryInfo.flagbearerInfo[0] !== null &&
                        <div>
                            <h1>Country Flagbearers</h1>
                            <div className={"FlagbearerInfo"}>
                                {countryInfo.flagbearerInfo.map(flagbearer => {
                                    return <AthleteCard athlete={flagbearer}/>
                                })}
                            </div>
                        </div>
                        }

                    </div>
                    <div>
                        {
                            countryInfo.anthemInfo.length > 0 &&
                            <InfoCard topic={"Anthem"} cardInfo={countryInfo.anthemInfo}/>
                        }
                    </div>
                    <div>
                        {
                            countryInfo.membershipInfo.length > 0 &&
                            <InfoCard topic={"Membership Info"} cardInfo={countryInfo.membershipInfo}/>
                        }
                    </div>
                    <div>
                        {
                            countryInfo.officialsInfo.length > 0 &&
                            <InfoCard topic={"Officials Info"} cardInfo={countryInfo.officialsInfo}/>
                        }
                    </div>
                    <div>
                        {
                            countryInfo.participationInfo.length > 0 &&
                            <InfoCard topic={"Participation Info"} cardInfo={countryInfo.participationInfo}/>
                        }
                    </div>
                </div>
                <div className={"MedalsByDivision"}>
                    {
                        countryInfo.medalsByDivision &&
                        <TableByDivision tableData={countryInfo.medalsByDivision} rank={countryInfo.rank}
                                         totalRank={countryInfo.totalRank}/>
                    }
                </div>
                <div className={"MedalBySportTable"}>
                    {
                        countryInfo.medalsBySport &&
                        <TableByTopic tableTopic={"Sport"} tableData={countryInfo.medalsBySport}/>
                    }
                </div>
                <div className={"MedalsByYearTable"}>
                    {
                        countryInfo.medalsByYear &&
                        <TableByTopic tableTopic={"Year"} tableData={countryInfo.medalsByYear}/>
                    }
                </div>
            </div>
        </div>
    )
}