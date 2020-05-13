import React from "react";
import {connect} from "react-redux";

class Sidebar extends React.Component {
    render() {
        const renderBtnsForPage = () => {
            console.log(this.props.sidebarState);
            if (this.props.sidebarState === 'courseCatalog')    {
                return allCursesBtns;
            } else  if (this.props.sidebarState === 'course')  {
                return specificCourseBtns;
            }
            return allCursesBtns;
        }

        const allCursesBtns = [
            <button className="list-group-item list-group-item-action bg-success" key={2}>
                <i className="fa fa-users" aria-hidden="true"/>&nbsp;&nbsp;
                Створити новий курс
            </button>
        ]

        const specificCourseBtns = [
            <button className="list-group-item list-group-item-action bg-success" key={3}>
                <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                Створити нове завдання
            </button>,
            <button className="list-group-item list-group-item-action bg-success" key={4}>
                <i className="fa fa-plus-circle" aria-hidden="true"/>&nbsp;&nbsp;
                 Створити нову групу
            </button>,
            <button className="list-group-item list-group-item-action bg-warning" key={5}>
                <i className="fa fa-pencil-square" aria-hidden="true"/>&nbsp;&nbsp;
                Редагувати даний курс
            </button>,
            <button className="list-group-item list-group-item-action bg-danger" key={6}>
                <i className="fa fa-trash" aria-hidden="true"/>&nbsp;&nbsp;
                Видалити даний курс
            </button>
        ]

        const specificGroupBtns = [
            <button className="list-group-item list-group-item-action" key={3}>
                <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                Створити нове завдання
            </button>,
            <button className="list-group-item list-group-item-action" key={7}>
                <i className="fa fa-id-card" aria-hidden="true"/>&nbsp;&nbsp;
                Додати студента до групи
            </button>,
            <button className="list-group-item list-group-item-action" key={8}>
                <i className="fa fa-thumb-tack" aria-hidden="true"/>&nbsp;&nbsp;
                Назначити завдання
            </button>,
            <button className="list-group-item list-group-item-action bg-warning" key={9}>
                <i className="fa fa-pencil-square" aria-hidden="true"/>&nbsp;&nbsp;
                Редагувати дану групу
            </button>,
            <button className="list-group-item list-group-item-action bg-danger" key={10}>
                <i className="fa fa-trash" aria-hidden="true"/>&nbsp;&nbsp;
                Видалити дану групу
            </button>
        ]

        return (
            <div className="bg-dark" id="sidebar-wrapper">
                <div className="sidebar-sticky">
                    <div className="sidebar-heading">
                        {this.props.sidebarTitle}
                    </div>
                    <div className="dropdown-divider"/>
                    <div className="list-group list-group-flush">
                        <button className="list-group-item list-group-item-action bg-success" key={1}>
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

function mapStateToProps(state) {
    return {
        sidebarState: state.sidebar.sidebarState,
        sidebarTitle: state.sidebar.sidebarTitle,
    };
}

export default connect(mapStateToProps)(Sidebar);
