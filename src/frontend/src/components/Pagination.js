import React, {createRef} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';

export const Pagination = ({pageSize, totalElements, paginate, changePageSize}) => {
    const pageNumbers = [];
    const selectPageSize = createRef();

    const changePageSizeRef = () => {
        changePageSize(selectPageSize.current.value);
        paginate(0); // Result back to first page
    }

    for(let i = 1; i <= Math.ceil(totalElements / pageSize); i++){
        pageNumbers.push(i);
    }

    return (
        <nav>
            <ul className="pagination">
                <li className="pagination.first" />
                {pageNumbers.map(number => (
                    <li key={number} className="page-item">
                        <a onClick={() => paginate(number-1)} href="#" className="page-link">
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