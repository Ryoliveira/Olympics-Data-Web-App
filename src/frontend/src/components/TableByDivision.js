import React from 'react';
import "../css/TableByDivision.css"


export const TableByDivision = ({tableData, rank, totalRank}) => {

    let divisions = [];
    let tableMedalData = [];


    const splitData = () => {
        for (let i = 0; i < tableData.length; i++) {
            divisions.push(tableData[i].category)
            tableMedalData.push(tableData[i].goldMedalCount);
            tableMedalData.push(tableData[i].silverMedalCount);
            tableMedalData.push(tableData[i].bronzeMedalCount);
            tableMedalData.push(tableData[i].totalMedalCount);
        }
    }
    splitData();


    const setMedalTypeHeaderRow = () => {
        let medalTypes = ["Gold", "Silver", "Bronze", "Total"];
        let tableRow = [];

        for (let i = 0; i < (divisions.length * (divisions.length - 1)); i++) {
            tableRow.push(
                <th>{medalTypes[i % (divisions.length - 1)]}</th>
            )
        }
        console.log(tableRow);
        return tableRow;
    }


    return (
        <div>
            <h1>Medals By Division</h1>
            <table className="table table-bordered">
                <thead>
                <tr>
                    <th className="RankCol" rowSpan={2}>Rank</th>
                    {divisions.map(division => {
                        return (
                            <th className="CenterData" colSpan={4}>
                                {division}
                            </th>
                        )
                    })}
                    <th className="RankCol" rowSpan={2}>Total Rank</th>
                </tr>
                <tr>
                    {setMedalTypeHeaderRow()}
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td className="CenterData">{rank}</td>
                    {tableMedalData.map(medal => {
                        return (
                            <td className="CenterData">{medal}</td>
                        )
                    })}
                    <td className="CenterData">{totalRank}</td>
                </tr>
                </tbody>

            </table>

        </div>
    )
}