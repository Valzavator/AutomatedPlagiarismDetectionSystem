import React from "react";
import Load from "../component/Load";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import * as sidebarActions from "../../store/action/sidebarActions";
import * as workflowActions from "../../store/action/workflowActions";
import {connect} from "react-redux";
import PagePagination from "../component/PagePagination";
import {LinkContainer} from "react-router-bootstrap";
import {deleteStudentFromSystem, getAllStudents} from "../../api/student";
import {notify} from "reapop";

class StudentsPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            content: [],
            page: 0,
            totalPages: 0
        }

        this.handleDeleteBtn = this.handleDeleteBtn.bind(this);
        this.handlePaginationClick = this.handlePaginationClick.bind(this);
    }

    componentDidMount() {
        this.props.sidebar.changeSidebarState("students", "Меню керування:");
        this.loadStudents();
    }

    async loadStudents(page = 0) {
        try {
            await this.setState({
                isLoading: true
            });
            let res = await getAllStudents(page);
            await this.setState({
                isLoading: false,
                ...res.data
            });
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    async handleDeleteBtn(e) {
        e.preventDefault();
        const studentId = parseInt(e.target.studentId.value);
        try {
            await this.setState({
                isLoading: true
            });
            await deleteStudentFromSystem(studentId);
            const newContent = this.state.content.filter(s => s.id !== studentId);
            if (newContent.length > 0) {
                await this.setState({
                    isLoading: false,
                    content: newContent
                });
            } else {
                await this.loadStudents(this.state.page > 0 ? --this.state.page: 0);
            }
        } catch (err) {
            if (err.status === 409) {
                const {notify} = this.props;
                notify({
                    title: 'Невдача!',
                    message: 'Студент прикріплений до групи!',
                    status: 'error',
                    position: 'tc',
                    dismissible: true,
                    dismissAfter: 5000
                });
                await this.setState({
                    isLoading: false,
                });
            } else {
                this.props.error.throwError(err);
            }
        }
    }

    async handlePaginationClick(page) {
        await this.loadStudents(page);
    }

    render() {

        const renderStudentsTable = (students) => {
            let studentRows = [];
            for (let i = 0; i < students.length; i++) {
                studentRows.push(
                    <tr key={i}>
                        <th scope="row">{i + 1}</th>
                        <td className="overflow-text">
                            <LinkContainer
                                to={"/students"}>
                                <a href="/students">
                                    <i className="fa fa-user fa-lg"
                                       aria-hidden="true">&nbsp;&nbsp;</i>
                                    {students[i].fullName}
                                </a>
                            </LinkContainer>
                        </td>
                        <td className="align-middle">
                            <LinkContainer
                                to={`/students/${students[i].id}`}>
                                <a href={`/students/${students[i].id}`}>
                                    <i className="fa fa-eye fa-lg" aria-hidden="true">&nbsp;&nbsp;</i>
                                </a>
                            </LinkContainer>
                        </td>
                        <td className="align-middle">
                            <form onSubmit={this.handleDeleteBtn}>
                                <input name="studentId" value={students[i].id} hidden readOnly/>
                                <button type="submit" className="rmv-btn-as-link">
                                    <i className="fa fa-times fa-lg"
                                       aria-hidden="true"/>
                                </button>
                            </form>
                        </td>
                    </tr>
                )
            }

            return (
                <div className="table-responsive-lg">
                    <table
                        className="table table-hover table-striped table-dark table-bordered text-center">
                        <thead>
                        <tr className="bg-primary">
                            <th scope="col" style={{width: '47px'}}>№</th>
                            <th scope="col">Повне ім'я студента</th>
                            <th scope="col" style={{width: '120px'}}>Переглянути</th>
                            <th scope="col" style={{width: '97px'}}>Видалити</th>
                        </tr>
                        </thead>
                        <tbody>
                        {studentRows}
                        </tbody>
                    </table>
                </div>
            );
        }

        return (
            <div className="row h-100 justify-content-center">
                {this.state.isLoading
                    ? (<Load/>)
                    : (
                        <div className="container my-3">

                            <div className="row justify-content-center">
                                <div className="container-fluid">
                                    <h2 className="text-center">
                                        Студенти
                                    </h2>
                                    <div className="progress mb-4">
                                        <div className="progress-bar" role="progressbar" aria-valuenow="25"
                                             aria-valuemin="0"
                                             aria-valuemax="100"/>
                                    </div>
                                </div>
                            </div>

                            {
                                this.state.content.length > 0
                                    ? renderStudentsTable(this.state.content)
                                    : (
                                        <div className="row align-items-center h-50 justify-content-center">
                                            <div className="container-fluid">
                                                <div className="alert alert-primary text-center" role="alert">
                                                    <h2>
                                                        Ви не додали жодного студента до системи
                                                    </h2>
                                                </div>
                                            </div>
                                        </div>
                                    )
                            }

                            <PagePagination
                                page={this.state.page}
                                totalPages={this.state.totalPages}
                                onClick={this.handlePaginationClick}
                            />
                        </div>
                    )
                }
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        activeCourse: state.workflow.activeCourse,
        groups: state.workflow.groups
    };
}


function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        sidebar: bindActionCreators(sidebarActions, dispatch),
        workflow: bindActionCreators(workflowActions, dispatch),
        notify: bindActionCreators(notify, dispatch)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(StudentsPage);
