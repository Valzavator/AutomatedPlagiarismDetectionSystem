import React from "react";

class PagePagination extends React.Component {
    constructor(props) {
        super(props);

        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(e) {
        this.props.onClick(e.target.value);
    }

    render() {
        const renderItems = (page, totalPages) => {
            if (totalPages <= 1) {
                return null;
            }
            // const leftItem = page > 0 ? page - 1 : 0;
            // const rightItem = (leftItem + 2) <= totalPages ? leftItem + 2 : totalPages;
            let items = [];

            for (let i = 0; i < totalPages; i++) {
                items.push(
                    <li className={i === page ? "page-item disabled" : "page-item"} key={i}>
                        <button className="page-link" value={i} onClick={this.handleClick}>{i + 1}</button>
                    </li>
                );
            }
            return items;
        }

        return (
            <nav aria-label="Page navigation example">
                <ul className="pagination justify-content-center">
                    {renderItems(this.props.page, this.props.totalPages)}
                </ul>
            </nav>
        )
    }
}

export default PagePagination;
