import React from 'react';


export const TableByTopic = ({tableTopic, tableData}) => {

    return (
        <div>
            <h1>Medals By {tableTopic}</h1>
            <table className="table table-sm table-striped">
                <thead className="thead-dark">
                <tr>
                    <th>{tableTopic}</th>
                    <th>Gold</th>
                    <th>Silver</th>
                    <th>Bronze</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                {tableData.map(row => {
                    return (
                        <tr key={row.category}>
                            <td>{row.category}</td>
                            <td>{row.goldMedalCount}</td>
                            <td>{row.silverMedalCount}</td>
                            <td>{row.bronzeMedalCount}</td>
                            <td>{row.totalMedalCount}</td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
        </div>
    )

}