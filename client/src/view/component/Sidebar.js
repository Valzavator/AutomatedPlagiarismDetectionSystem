import React from "react";
import {connect} from "react-redux";
import AssignTaskModal from "../container/control/AssignTaskModal";

class Sidebar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {}
        this.handleChangeAssignTaskModal = this.handleChangeAssignTaskModal.bind(this);
    }

    handleChangeAssignTaskModal(isOpen) {
        console.log(isOpen)
        this.setState({
            isOpenAssignTaskModal: isOpen
        })
    }

    render() {
        const renderBtnsForPage = () => {
            if (this.props.sidebarState === 'courseCatalog') {
                return allCursesBtns;
            } else if (this.props.sidebarState === 'course') {
                return specificCourseBtns;
            } else if (this.props.sidebarState === 'group') {
                return specificGroupBtns;
            }
            return allCursesBtns;
        }

        const renderModalsForPage = () => {
            if (this.props.sidebarState === 'courseCatalog') {
                return allCursesModals;
            } else if (this.props.sidebarState === 'course') {
                return specificCourseModals;
            } else if (this.props.sidebarState === 'group') {
                return specificGroupModals;
            }
            return allCursesBtns;
        }

        const allCursesBtns = [
            <button className="list-group-item list-group-item-action bg-success" key={2}>
                <i className="fa fa-users" aria-hidden="true"/>&nbsp;&nbsp;
                Створити новий курс
            </button>
        ]
        const allCursesModals = []

        const specificCourseBtns = [
            <button className="list-group-item list-group-item-action bg-success" key={4}>
                <i className="fa fa-plus-circle" aria-hidden="true"/>&nbsp;&nbsp;
                Створити нову групу
            </button>,
            <button className="list-group-item list-group-item-action bg-primary" key={3}>
                <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                Завдання курсу
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
        const specificCourseModals = []

        const specificGroupBtns = [
            <button className="list-group-item list-group-item-action bg-info" key={3}>
                <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                Створити нове завдання
            </button>,
            <button className="list-group-item list-group-item-action bg-success" key={7}>
                <i className="fa fa-id-card" aria-hidden="true"/>&nbsp;&nbsp;
                Додати студента до групи
            </button>,
            <button className="list-group-item list-group-item-action bg-success"
                    data-toggle="modal"
                    data-target="#assignTaskModal"
                    key={"assignTaskModalBtn"}
                    value="isOpenAssignTaskModal"
                    onClick={() => this.handleChangeAssignTaskModal(true)}>
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

        const specificGroupModals = [
            <AssignTaskModal id="assignTaskModal"
                             key={'assignTaskModal'}
                             isOpen={this.state.isOpenAssignTaskModal}
                             onClose={() => this.handleChangeAssignTaskModal(false)}
            />
        ]


        return (
            <div className="bg-dark" id="sidebar-wrapper">
                <div className="sidebar-sticky">
                    <div className="sidebar-heading">
                        {this.props.sidebarTitle}
                    </div>
                    <div className="dropdown-divider"/>
                    <div className="list-group list-group-flush">
                        <div>
                            <button className="list-group-item list-group-item-action bg-info" key={1}>
                                <i className="fa fa-user-plus" aria-hidden="true"/>&nbsp;&nbsp;
                                Додати нового студента
                            </button>
                        </div>
                        {renderBtnsForPage()}
                    </div>
                </div>
                <div>{renderModalsForPage()}</div>
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
