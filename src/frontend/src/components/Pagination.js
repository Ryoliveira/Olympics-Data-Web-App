import React, {createRef} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';

export const Pagination = ({pageSize, totalPages, paginate, changePageSize}) => {
    const pageNumbers = [];
    const selectPageSize = createRef();

    const changePageSizeRef = () => {
        changePageSize(selectPageSize.current.value);
    }

    for(let i = 1; i <= Math.ceil(totalPages / pageSize); i++){
        pageNumbers.push(i);
    }

    return (
        <nav>
            <ul className="pagination">
                {pageNumbers.map(number => (
                    <li key={number} className="page-item">
                        <a onClick={() => paginate(number)} href="#" className="page-link">
                            {number}
                        </a>
                    </li>
                ))}
            </ul>
            <select ref={selectPageSize}>
                <option selected value={5}>5</option>
                <option value={10}>10</option>
                <option value={25}>25</option>
                <option value={50}>50</option>
            </select>
            <button onClick={changePageSizeRef}>Change</button>
        </nav>
    )

}