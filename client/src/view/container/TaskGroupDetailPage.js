import React from "react";
import Load from "../component/Load";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import * as sidebarActions from "../../store/action/sidebarActions";
import {connect} from "react-redux";
import moment from "moment";
import {LinkContainer} from "react-router-bootstrap";
import {getTaskGroup} from "../../api/taskGroup";

class GroupPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            updateTaskGroupInfo: true,
            activeTaskGroup: {
                taskId: -1,
                taskName: 'NOT_LOADED',
                creationDate: 'NOT_LOADED',
                expiryDate: 'NOT_LOADED',
                plagDetectionStatus: 'NOT_LOADED',
                plagDetectionSettings: {
                    comparisonSensitivity: -1,
                    minimumSimilarityPercent: -1,
                    detectionType: 'NOT_LOADED',
                    saveLog: false,
                    programmingLanguageName: 'NOT_LOADED',
                    baseCodePresent: false
                },
                plagDetectionResult: {
                    resultMessage: 'NOT_LOADED',
                    log: '',
                    isSuccessful: false,
                    resultPath: '',
                    resultStudents: []
                },
            }
        }

        this.handleResultBtn = this.handleResultBtn.bind(this);
        this.handleBackBtn = this.handleBackBtn.bind(this);
    }

    componentDidMount() {
        this.props.sidebar.changeSidebarState("taskGroup", "Деталі завдання: ");
        this.loadTaskGroupInfo();
        this.timerID = setInterval(
            () => this.loadTaskGroupInfo(),
            5000
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    async loadTaskGroupInfo() {
        if (!this.state.updateTaskGroupInfo) {
            clearInterval(this.timerID);
            return;
        }
        try {
            const courseId = this.props.match.params.courseId;
            const groupId = this.props.match.params.groupId;
            const taskId = this.props.match.params.taskId;
            let res = await getTaskGroup(courseId, groupId, taskId);
            await this.setState({
                isLoading: false,
                activeTaskGroup: res.data,
                updateTaskGroupInfo: ['PENDING', 'IN_PROCESS'].includes(res.data.plagDetectionStatus)
            });
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleResultBtn() {
        window.open(this.state.activeTaskGroup.plagDetectionResult.resultPath, "_blank")
    }

    handleBackBtn() {
        this.props.history.push(
            '/courses/' + this.props.match.params.courseId + '/groups/' + this.props.match.params.groupId);
    }

    render() {
        const renderStudentsTable = (students) => {
            let studentRows = [];
            for (let i = 0; i < students.length; i++) {
                studentRows.push(
                    <tr key={i}>
                        <th scope="row" className="align-middle">{i + 1}</th>
                        <td className="overflow-text align-middle">
                            <LinkContainer
                                to={"/students/" + students[i].studentId}>
                                <a href="/students/">
                                    <i className="fa fa-user fa-lg"
                                       aria-hidden="true">&nbsp;&nbsp;</i>
                                    {students[i].studentFullName}
                                </a>
                            </LinkContainer>
                        </td>
                        <td style={{maxWidth: '500px', wordBreak: 'break-word'}}>
                            {students[i].logMessage}
                        </td>
                    </tr>
                )
            }

            return (
                <div className="col-md-12">
                    <div className="alert alert-danger text-center" role="alert">
                        <h5>Невдалося завантажити репозиторії студентів</h5>
                    </div>
                    <div className="table-responsive-lg">
                        <table
                            className="table table-hover table-striped table-dark table-bordered text-center">
                            <thead>
                            <tr className="bg-primary">
                                <th scope="col">№</th>
                                <th scope="col">Повне ім'я студента</th>
                                <th scope="col">Причина</th>
                            </tr>
                            </thead>
                            <tbody>
                            {studentRows}
                            </tbody>
                        </table>
                    </div>
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

        const renderDetectionType = (detectionType) => {
            if (detectionType === "COURSE") {
                return (
                    <span className="badge badge-info">
                        Курс
                    </span>
                );
            } else if (detectionType === "GROUP") {
                return (
                    <span className="badge badge-info">
                        Група
                    </span>
                );
            }
        };

        const renderPlagDetectionResult = (result) => {
            return (
                <div className="row justify-content-center">
                    <div className="d-flex justify-content-center align-items-center mb-3">
                        <h1 className="text-center">
                            Результати
                        </h1>
                    </div>
                    {result.isSuccessful
                        ? (
                            <div className="col-md-12">
                                <div className="alert alert-success" role="alert">
                                    <h4 className="alert-heading text-center">Перевірка виконана успішно</h4>
                                    <hr/>
                                    <p className="mb-0" style={{wordWrap: 'break-word'}}>
                                        {result.resultMessage}
                                    </p>
                                </div>
                            </div>
                        )
                        : (
                            <div className="col-md-12 col-sm-12">
                                <div className="alert alert-danger" role="alert">
                                    <h4 className="alert-heading text-center">Перевірка не виконана</h4>
                                    <hr/>
                                    <p className="mb-0" style={{wordWrap: 'break-word'}}>
                                        {result.resultMessage}
                                    </p>
                                </div>
                            </div>
                        )
                    }

                    {result.log && result.log.length > 0
                        ? (
                            <div className="col-md-12 my-3">
                                <div className="card bg-light">
                                    <div className="card-header bg-light text-center h3">Журнал виконання</div>
                                    <div className="card-body bg-dark overflow-auto" style={{maxHeight: '500px'}}>
                                        <p className="card-text" style={{whiteSpace: 'pre-line'}}>
                                            <samp>
                                                {result.log}
                                            </samp>
                                        </p>
                                    </div>
                                    <div className="card-footer bg-light"/>
                                </div>
                            </div>
                        )
                        : null
                    }

                    {result.resultStudents.length > 0
                        ? renderStudentsTable(result.resultStudents)
                        : null
                    }

                    {result.isSuccessful
                        ? (
                            <div className="col-md-12 text-center mb-4">
                                <button type="button" className="btn btn-success btn-lg mt-3"
                                        onClick={this.handleResultBtn}>
                                    Переглянути результати&nbsp;&nbsp;
                                    <i className="fa fa-external-link fa-lg" aria-hidden="true"/>
                                </button>
                            </div>
                        )
                        : null
                    }
                </div>
            )
        }

        const taskGroupInfo = (
            <div className="jumbotron jumbotron-fluid w-75 my-5 py-3">
                <div className="container">

                    <div className="d-flex justify-content-center align-items-center">
                        <h1 className="text-center">
                            Виявлення плагіату&nbsp;&nbsp;&nbsp;
                        </h1>
                    </div>

                    <div className="progress my-3">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-12 col-lg-6">
                            <h5 className="mt-3 text-center">
                                <span className="fa fa-info-circle fa-lg"/>
                                &nbsp;&nbsp;Інформація про завдання
                            </h5>
                            <div className="table-responsive mb-3">
                                <table className="table table-bordered table-active">
                                    <tbody>
                                    <tr>
                                        <td className="w-25">Назва:</td>
                                        <td>
                                            <strong>
                                                {this.state.activeTaskGroup.taskName}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-25">Назначено:</td>
                                        <td>
                                            <strong>
                                                {moment(this.state.activeTaskGroup.creationDate).format('HH:mm / DD.MM.YYYY')}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-25">Дата перевірки:</td>
                                        <td>
                                            <strong>
                                                {moment(this.state.activeTaskGroup.expiryDate).format('HH:mm / DD.MM.YYYY')}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-50">Статус:</td>
                                        <td>
                                            <strong>
                                                {renderTaskStatus(this.state.activeTaskGroup.plagDetectionStatus)}
                                            </strong>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div className="col-md-12 col-lg-6">
                            <h5 className="mt-3 text-center">
                                <span className="fa fa-sliders fa-lg"/>
                                &nbsp;&nbsp;Задані налаштування
                            </h5>
                            <div className="table-responsive mb-3">
                                <table className="table table-bordered table-active">
                                    <tbody>
                                    <tr>
                                        <td className="w-25">Тип порівняння:</td>
                                        <td>
                                            <strong>
                                                {renderDetectionType(this.state.activeTaskGroup.plagDetectionSettings.detectionType)}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-25">Обрана мова програмування:</td>
                                        <td>
                                            <strong>
                                                {this.state.activeTaskGroup.plagDetectionSettings.programmingLanguageName}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-25">Чутливість порівняння:</td>
                                        <td>
                                            <strong>
                                                {this.state.activeTaskGroup.plagDetectionSettings.comparisonSensitivity}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-50">Мінімальний відсоток співпадіння:</td>
                                        <td>
                                            <strong>
                                                {this.state.activeTaskGroup.plagDetectionSettings.minimumSimilarityPercent}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-25">Зберігати журнал виконання:</td>
                                        <td>
                                            <strong>
                                                {this.state.activeTaskGroup.plagDetectionSettings.saveLog ? 'Так' : 'Ні'}
                                            </strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="w-25">Шаблонний код:</td>
                                        <td>
                                            <strong>
                                                {this.state.activeTaskGroup.plagDetectionSettings.baseCodePresent
                                                    ? 'Завантажено'
                                                    : 'Не завантажено'
                                                }
                                            </strong>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    {this.state.activeTaskGroup.plagDetectionResult &&
                    (this.state.activeTaskGroup.plagDetectionStatus === "FAILED" || this.state.activeTaskGroup.plagDetectionStatus === "DONE")
                        ? (
                            <div>
                                <div className="progress mb-3">
                                    <div className="progress-bar" role="progressbar" aria-valuenow="25"
                                         aria-valuemin="0"
                                         aria-valuemax="100"/>
                                </div>
                                {renderPlagDetectionResult(this.state.activeTaskGroup.plagDetectionResult)}
                            </div>
                        )
                        : null
                    }

                    <div className="progress mb-3">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-12 text-center">
                            <button type="button" className="btn btn-primary btn-lg"
                                    onClick={this.handleBackBtn}>
                                <i className="fa fa-chevron-circle-left fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                Повернутися до групи
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        );

        return (
            <div className="row h-100 justify-content-center">
                {this.state.isLoading
                    ? (<Load/>)
                    : (taskGroupInfo)
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
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(GroupPage);
