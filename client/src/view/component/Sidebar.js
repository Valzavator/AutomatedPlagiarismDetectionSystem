import React from "react";
import {connect} from "react-redux";
import AssignTaskModal from "../container/control/AssignTaskModal";
import {LinkContainer} from "react-router-bootstrap";
import {matchPath, withRouter} from "react-router-dom";
import AddStudentToGroupModal from "../container/control/AddStudentToGroupModal";
import AddTaskToCourseModal from "../container/control/AddTaskToCourseModal";
import AddStudentToSystemModal from "../container/control/AddStudentToSystemModal";
import AddCourseToSystemModal from "../container/control/AddCourseToSystemModal";
import AddGroupToCourseModal from "../container/control/AddGroupToCourseModal";

class Sidebar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {}
        this.handleBackBtn = this.handleBackBtn.bind(this);
        this.handleChangeModal = this.handleChangeModal.bind(this);
    }

    handleChangeModal(taskModal, isOpen) {
        this.setState({
            [taskModal]: isOpen
        })
    }

    handleBackBtn() {
        this.props.history.goBack();
    }

    getParams() {
        const match = matchPath(this.props.location.pathname, {
            path: "/courses/:courseId",
            exact: false,
            strict: false
        });
        return match ? match.params : undefined;
    }

    render() {
        const renderBtnsForPage = () => {
            if (this.props.sidebarState === 'courseCatalog') {
                return allCursesBtns;
            } else if (this.props.sidebarState === 'course') {
                return renderSpecificCourseBtns(this.getParams());
            } else if (this.props.sidebarState === 'group') {
                return specificGroupBtns;
            } else if (this.props.sidebarState === 'courseTasks') {
                return specificCourseTasksBtns;
            } else if (this.props.sidebarState === 'taskGroup') {
                return taskGroupDetailBtns;
            } else if (this.props.sidebarState === 'students') {
                return studentsBtns;
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
            } else if (this.props.sidebarState === 'courseTasks') {
                return specificCourseTasksModals;
            } else if (this.props.sidebarState === 'taskGroup') {
                return taskGroupDetailModals;
            } else if (this.props.sidebarState === 'students') {
                return studentsModals;
            }
            return allCursesBtns;
        }

        const allCursesBtns = [
            <button className="list-group-item list-group-item-action bg-success" key={'createNewCourse'}
                    data-toggle="modal"
                    data-target="#addCourseToSystemModal"
                    onClick={() => this.handleChangeModal('addCourseToSystemModal', true)}>
                <i className="fa fa-users" aria-hidden="true"/>&nbsp;&nbsp;
                Створити новий курс
            </button>
        ]
        const allCursesModals = [
            <AddCourseToSystemModal
                id="addCourseToSystemModal"
                key={'addCourseToSystemModal'}
                isOpen={this.state.addTaskToCourseModal}
                onClose={() => this.handleChangeModal('addCourseToSystemModal', false)}
            />
        ]

        const renderSpecificCourseBtns = (params) => {
            return [
                <LinkContainer to={`/courses`} key={'backToAllCourses'}>
                    <button className="list-group-item list-group-item-action bg-primary">
                        <i className="fa fa-chevron-circle-left" aria-hidden="true"/>&nbsp;&nbsp;
                        Всі курси
                    </button>
                </LinkContainer>,
                <LinkContainer to={`/courses/${params ? params.courseId : -1}/tasks`} key={'courseTasks'}>
                    <button className="list-group-item list-group-item-action bg-info">
                        <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                        Завдання курсу
                    </button>
                </LinkContainer>,
                <button className="list-group-item list-group-item-action bg-success" key={'createNewGroup'}
                        data-toggle="modal"
                        data-target="#addGroupToCourseModal"
                        onClick={() => this.handleChangeModal('addGroupToCourseModal', true)}>
                    <i className="fa fa-plus-circle" aria-hidden="true"/>&nbsp;&nbsp;
                    Створити нову групу
                </button>,
                <button className="list-group-item list-group-item-action bg-warning" key={'editCourse'}>
                    <i className="fa fa-pencil-square" aria-hidden="true"/>&nbsp;&nbsp;
                    Редагувати даний курс
                </button>,
                <button className="list-group-item list-group-item-action bg-danger" key={'removeCourse'}>
                    <i className="fa fa-trash" aria-hidden="true"/>&nbsp;&nbsp;
                    Видалити даний курс
                </button>
            ]
        }
        const specificCourseModals = [
            <AddGroupToCourseModal
                id="addGroupToCourseModal"
                key={'addGroupToCourseModal'}
                isOpen={this.state.addTaskToCourseModal}
                onClose={() => this.handleChangeModal('addGroupToCourseModal', false)}
            />
        ]

        const specificCourseTasksBtns = [
            <button className="list-group-item list-group-item-action bg-primary" key={'backToCourse'}
                    onClick={this.handleBackBtn}>
                <i className="fa fa-chevron-circle-left" aria-hidden="true"/>&nbsp;&nbsp;
                Повернутися до курсу
            </button>,
            <button className="list-group-item list-group-item-action bg-success"
                    key={'createNewTask'}
                    data-toggle="modal"
                    data-target="#addTaskToCourseModal"
                    onClick={() => this.handleChangeModal('addTaskToCourseModal', true)}>
                <i className="fa fa-book" aria-hidden="true"/>&nbsp;&nbsp;
                Створити нове завдання
            </button>,
        ]
        const specificCourseTasksModals = [
            <AddTaskToCourseModal
                id="addTaskToCourseModal"
                key={'addTaskToCourseModal'}
                isOpen={this.state.addTaskToCourseModal}
                onClose={() => this.handleChangeModal('addTaskToCourseModal', false)}
            />
        ]

        const specificGroupBtns = [
            <button className="list-group-item list-group-item-action bg-primary" key={'backToAllCourses'}
                    onClick={this.handleBackBtn}>
                <i className="fa fa-chevron-circle-left" aria-hidden="true"/>&nbsp;&nbsp;
                Повернутися до курсу
            </button>,
            <button className="list-group-item list-group-item-action bg-success"
                    data-toggle="modal"
                    data-target="#addStudentToGroupModal"
                    key={'addStudentToGroup'}
                    onClick={() => this.handleChangeModal('addStudentToGroupModal', true)}>
                <i className="fa fa-id-card" aria-hidden="true"/>&nbsp;&nbsp;
                Додати студента до групи
            </button>,
            <button className="list-group-item list-group-item-action bg-success"
                    data-toggle="modal"
                    data-target="#assignTaskModal"
                    key={"assignTaskModalBtn"}
                    onClick={() => this.handleChangeModal('assignTaskModal', true)}>
                <i className="fa fa-thumb-tack" aria-hidden="true"/>&nbsp;&nbsp;
                Назначити завдання
            </button>,
            <button className="list-group-item list-group-item-action bg-warning" key={'editGroup'}>
                <i className="fa fa-pencil-square" aria-hidden="true"/>&nbsp;&nbsp;
                Редагувати дану групу
            </button>,
            <button className="list-group-item list-group-item-action bg-danger" key={'removeGroup'}>
                <i className="fa fa-trash" aria-hidden="true"/>&nbsp;&nbsp;
                Видалити дану групу
            </button>
        ]

        const specificGroupModals = [
            <AssignTaskModal id="assignTaskModal"
                             key={'assignTaskModal'}
                             isOpen={this.state.assignTaskModal}
                             onClose={() => this.handleChangeModal('assignTaskModal', false)}
            />,
            <AddStudentToGroupModal
                id="addStudentToGroupModal"
                key={'addStudentToGroupModal'}
                isOpen={this.state.addStudentToGroupModal}
                onClose={() => this.handleChangeModal('addStudentToGroupModal', false)}
            />
        ]

        const taskGroupDetailBtns = [
            <button className="list-group-item list-group-item-action bg-primary" key={'backToAllCourses'}
                    onClick={this.handleBackBtn}>
                <i className="fa fa-chevron-circle-left" aria-hidden="true"/>&nbsp;&nbsp;
                Повернутися до групи
            </button>,
            <button className="list-group-item list-group-item-action bg-warning" key={'editTaskGroup'}>
                <i className="fa fa-pencil-square" aria-hidden="true"/>&nbsp;&nbsp;
                Редагувати завдання
            </button>,
            <button className="list-group-item list-group-item-action bg-danger" key={'removeTaskGroup'}>
                <i className="fa fa-trash" aria-hidden="true"/>&nbsp;&nbsp;
                Видалити завдання групи
            </button>
        ]

        const taskGroupDetailModals = []

        const studentsBtns = [
            <button className="list-group-item list-group-item-action bg-info"
                    key={'addNewStudent'}
                    data-toggle="modal"
                    data-target="#addStudentToSystemModal"
                    onClick={() => this.handleChangeModal('addStudentToSystemModal', true)}>
                <i className="fa fa-user-plus" aria-hidden="true"/>&nbsp;&nbsp;
                Додати нового студента
            </button>
        ]
        const studentsModals = [
            <AddStudentToSystemModal
                id="addStudentToSystemModal"
                key={'addStudentToSystemModal'}
                isOpen={this.state.addTaskToCourseModal}
                onClose={() => this.handleChangeModal('addStudentToSystemModal', false)}
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

export default withRouter(connect(mapStateToProps)(Sidebar));
