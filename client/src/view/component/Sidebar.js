import React from "react";
import {withRouter} from "react-router-dom";

class Sidebar extends React.Component {
    render() {
        const renderBtnsForPage = () => {
            // if (this.props.location.pathname === '/courses')    {
            //     return allCursesBtns;
            // } else {
            //     return specificCourseBtns;
            // }
            return specificGroupBtns;
        }

        const allCursesBtns = [
            <button className="list-group-item list-group-item-action">
                <i className="fa fa-users" aria-hidden="true"/>&nbsp;&nbsp;
                Створити новий курс
            </button>
        ]

        const specificCourseBtns = [
            <button className="list-group-item list-group-item-action">
                <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                Створити нове завдання
            </button>,
            <button className="list-group-item list-group-item-action">
                <i className="fa fa-plus-circle" aria-hidden="true"/>&nbsp;&nbsp;
                 Створити нову групу
            </button>,
            <button className="list-group-item list-group-item-action bg-danger">
                <i className="fa fa-trash" aria-hidden="true"/>&nbsp;&nbsp;
                Видалити даний курс
            </button>
        ]

        const specificGroupBtns = [
            <button className="list-group-item list-group-item-action">
                <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                Створити нове завдання
            </button>,
            <button className="list-group-item list-group-item-action">
                <i className="fa fa-id-card" aria-hidden="true"/>&nbsp;&nbsp;
                Додати студента до групи
            </button>,
            <button className="list-group-item list-group-item-action">
                <i className="fa fa-thumb-tack" aria-hidden="true"/>&nbsp;&nbsp;
                Назначити завдання
            </button>,
            <button className="list-group-item list-group-item-action bg-danger">
                <i className="fa fa-trash" aria-hidden="true"/>&nbsp;&nbsp;
                Видалити дану групу
            </button>
        ]

        return (
            <div className="bg-dark" id="sidebar-wrapper">
                <div className="sidebar-sticky">
                    <div className="sidebar-heading">Меню керування:</div>
                    <div className="dropdown-divider"/>
                    <div className="list-group list-group-flush">
                        <button className="list-group-item list-group-item-action bg-success">
                            <i className="fa fa-user-plus" aria-hidden="true"/>&nbsp;&nbsp;
                            Додати нового студента
                        </button>
                        {renderBtnsForPage()}
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(Sidebar);
