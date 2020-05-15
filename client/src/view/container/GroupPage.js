import React from "react";
import Load from "../component/Load";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import * as sidebarActions from "../../store/action/sidebarActions";
import * as workflowActions from "../../store/action/workflowActions";
import {connect} from "react-redux";
import moment from "moment";
import {LinkContainer} from "react-router-bootstrap";
import {getTaskGroup} from "../../api/taskGroup";

class GroupPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
        }

        this.handleDetailTaskGroupBtn = this.handleDetailTaskGroupBtn.bind(this);
    }

    async componentDidMount() {
        await this.props.workflow.loadSpecificGroup(this.props.match.params.groupId, this.props.match.params.courseId);
        await this.props.sidebar.changeSidebarState("group", "Керування групою: ");
        await this.setState({
            isLoading: false
        });
    }

    componentWillUnmount() {
        this.props.sidebar.changeSidebarState("courseCatalog")
    }

    async handleDetailTaskGroupBtn(e) {
        e.preventDefault();
        e.persist()
        try {
            await this.setState({
                isLoading: true
            });
            const courseId = this.props.match.params.courseId;
            const groupId = this.props.match.params.groupId;
            const taskId = e.target.taskId.value;
            let res = await getTaskGroup(courseId, groupId, taskId);
            await this.setState({
                isLoading: false,
                showDetailTaskGroup: true,
                activeTaskGroup: res.data
            });
        } catch (err) {
            this.props.error.throwError(err);
        }
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
                                to={"/students/" + students[i].studentId}>
                                <a href="/students/">
                                    <i className="fa fa-user fa-lg"
                                       aria-hidden="true">&nbsp;&nbsp;</i>
                                    {students[i].studentFullName}
                                </a>
                            </LinkContainer>
                        </td>
                        <td style={{width: '200px'}}>
                            <a href={students[i].vcsRepositoryUrl} target="noopener">
                                <i className="fa fa-external-link fa-lg"
                                   aria-hidden="true">&nbsp;&nbsp;</i>
                            </a>
                        </td>
                        <td className="align-middle">
                            <form>
                                <input name="delete" value={students[i].id}
                                       hidden readOnly/>
                                <button type="submit"
                                        className="rmv-btn-as-link" disabled>
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
                            <th scope="col">№</th>
                            <th scope="col">Повне ім'я студента</th>
                            <th scope="col">Репозиторій студента</th>
                            <th scope="col"/>
                        </tr>
                        </thead>
                        <tbody>
                        {studentRows}
                        </tbody>
                    </table>
                </div>
            );
        }

        const renderTasksTable = (tasks) => {
            let taskRows = [];
            for (let i = 0; i < tasks.length; i++) {
                taskRows.push(
                    <tr key={i}>
                        <th scope="row" className="align-middle">{i + 1}</th>
                        <td className="overflow-text align-middle">
                            {tasks[i].taskName}
                        </td>
                        <td className="align-middle">
                            {moment(tasks[i].creationDate).format('HH:mm / DD.MM.YYYY')}
                        </td>
                        <td className="align-middle">
                            {moment(tasks[i].expiryDate).format('HH:mm / DD.MM.YYYY')}
                        </td>
                        <td className="align-middle">
                            {renderTaskStatus(tasks[i].plagDetectionStatus)}
                        </td>
                        <td className="align-middle">
                            <LinkContainer
                                to={this.props.location.pathname + '/tasks/' + tasks[i].taskId}>
                                <a href={this.props.location.pathname + '/tasks/' + tasks[i].taskId}>
                                    <i className="fa fa-eye fa-lg" aria-hidden="true">&nbsp;&nbsp;</i>
                                </a>
                            </LinkContainer>
                        </td>
                        <td>
                            <button className="btn btn-link" id="updateLinkBtn" >
                                <i className="fa fa-pencil-square-o fa-lg" aria-hidden="true">&nbsp;&nbsp;</i>
                            </button>
                        </td>
                        <td className="align-middle">
                            <form>
                                <input name="delete" value={tasks[i].id}
                                       hidden readOnly/>
                                <button type="submit"
                                        className="btn btn-link" id="deleteLinkBtn" disabled>
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
                            <th scope="col">№</th>
                            <th scope="col">Назва завдання</th>
                            <th scope="col">Назначено</th>
                            <th scope="col">Дата перевірки</th>
                            <th scope="col">Статус</th>
                            <th scope="col">Переглянути</th>
                            <th scope="col">Редагувати</th>
                            <th scope="col"/>
                        </tr>
                        </thead>
                        <tbody>
                        {taskRows}
                        </tbody>
                    </table>
                </div>
            );
        }

        const renderTaskStatus = (status) => {
            if (status === "DONE") {
                return (
                    <span className="badge badge-success">
                        Успішно
                    </span>
                );
            } else if (status === "FAILED") {
                return (
                    <span className="badge badge-danger">
                        Невдача
                    </span>
                );
            } else if (status === "PENDING") {
                return (
                    <span className="badge badge-warning">
                         В очікуванні
                    </span>
                );
            } else if (status === "IN_PROCESS") {
                return (
                    <span className="badge badge-info">
                        В процесі
                    </span>
                );
            }
        };

        const groupInfo = (
            <div className="container my-3">
                {/*<AssignTaskButton/>*/}
                <div className="row justify-content-center">
                    <div className="container-fluid">
                        <h2 className="text-center">
                            Група "{this.props.activeGroup.name}"
                        </h2>
                        <div className="jumbotron pt-2">
                            <div className="row">
                                <div className="col-md-6">
                                    <h5 className="my-3 text-center">
                                        <span className="fa fa-info-circle fa-lg"/>
                                        &nbsp;&nbsp;Головна інформація
                                    </h5>
                                    <table
                                        className="table table-borderless">
                                        <tbody>
                                        <tr>
                                            <td className="w-25">Назва курсу:</td>
                                            <td>
                                                <strong>
                                                    <LinkContainer
                                                        to={"/courses/" + this.props.activeGroup.courseId}>
                                                        <a href="#1">
                                                            {this.props.activeGroup.courseName}
                                                        </a>
                                                    </LinkContainer>
                                                </strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td className="w-25">Назва групи:</td>
                                            <td>
                                                <strong>
                                                    {this.props.activeGroup.name}
                                                </strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style={{width: '30%'}}>Дата створення:</td>
                                            <td>
                                                <strong>
                                                    {moment(this.props.activeGroup.creationDate).format('DD.MM.YYYY HH:mm')}
                                                </strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td className="w-25">Студентів:</td>
                                            <td>
                                                <strong>
                                                    {this.props.activeGroup.studentGroups.length}
                                                </strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td className="w-25">Завдань:</td>
                                            <td>
                                                <strong>
                                                    {this.props.activeGroup.taskGroups.length}
                                                </strong>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div className="col-md-6">
                                    <h5 className="my-3 text-center">
                                        <span className="fa fa-bar-chart fa-lg"/>
                                        &nbsp;&nbsp;Статисика завдань групи
                                    </h5>
                                    <table
                                        className="table table-hover table-striped table-dark table-bordered">
                                        <tbody>
                                        <tr>
                                            <td className="w-25">В очікуванні:</td>
                                            <td>
                                                <strong>
                                                    {this.props.activeGroup.taskGroups
                                                        .filter(t => t.plagDetectionStatus === 'PENDING')
                                                        .length}
                                                </strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style={{width: '35%'}}>Успішно виконаних:</td>
                                            <td>
                                                <strong>
                                                    {this.props.activeGroup.taskGroups
                                                        .filter(t => t.plagDetectionStatus === 'DONE')
                                                        .length}
                                                </strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td className="w-25">Помилка виконання:</td>
                                            <td>
                                                <strong>
                                                    {this.props.activeGroup.taskGroups
                                                        .filter(t => t.plagDetectionStatus === 'FAILED')
                                                        .length}
                                                </strong>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <div className="row justify-content-center">
                                <ul className="nav nav-tabs nav-fill w-100 bg-secondary">
                                    <li className="nav-item">
                                        <a className="nav-link active" data-toggle="tab"
                                           href="#nav-students">
                                            Студенти
                                        </a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link" data-toggle="tab" href="#nav-tasks">
                                            Завдання
                                        </a>
                                    </li>
                                </ul>
                                <div className="tab-content w-100" id="nav-tabContent">
                                    <div className="tab-pane fade show active" id="nav-students">
                                        {this.props.activeGroup.studentGroups.length > 0
                                            ? (
                                                renderStudentsTable(this.props.activeGroup.studentGroups)
                                            )
                                            : (
                                                <div className="alert alert-primary text-center mt-3"
                                                     role="alert">
                                                    Ви ще не додали студентів до даної групи
                                                </div>
                                            )
                                        }
                                    </div>
                                    <div className="tab-pane fade" id="nav-tasks">
                                        {this.props.activeGroup.taskGroups.length > 0
                                            ? (
                                                renderTasksTable(this.props.activeGroup.taskGroups)
                                            )
                                            : (
                                                <div className="alert alert-primary text-center mt-3"
                                                     role="alert">
                                                    Ви ще не призначили завдання для даної групи
                                                </div>
                                            )
                                        }
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>

            </div>
        );

        return (
            <div className="row h-100 justify-content-center">
                {this.state.isLoading
                    ? (<Load/>)
                    : (groupInfo)
                }
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        activeGroup: state.workflow.activeGroup,
    };
}


function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        sidebar: bindActionCreators(sidebarActions, dispatch),
        workflow: bindActionCreators(workflowActions, dispatch),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(GroupPage);
