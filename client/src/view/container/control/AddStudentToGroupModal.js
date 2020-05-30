import React from "react";
import {bindActionCreators} from "redux";
import * as errorActions from "../../../store/action/errorActions";
import * as workflowActions from "../../../store/action/workflowActions";
import {connect} from "react-redux";
import {matchPath, withRouter} from "react-router-dom";
import {getAllStudentsForAddingToGroup, addStudentToGroup} from "../../../api/studentGroup";
import Load from "../../component/Load";
import ReactTooltip from "react-tooltip";
import $ from 'jquery';
import {notify} from "reapop";

class AddStudentToGroupModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            invalidForm: true,
            students: [],

            studentId: -1,
            studentURL: ''
        }

        this.handleChangeStudent = this.handleChangeStudent.bind(this);
        this.handleChangesStudentURL = this.handleChangesStudentURL.bind(this);
        this.handleStudentsRedirectLink = this.handleStudentsRedirectLink.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCloseModal = this.handleCloseModal.bind(this);
    }

    componentDidMount() {
        const match = matchPath(this.props.location.pathname, {
            path: "/courses/:courseId/groups/:groupId",
            exact: false,
            strict: false
        });
        if (match) {
            this.setState({
                courseId: match.params.courseId,
                groupId: match.params.groupId
            });
        }
    }

    componentWillUnmount() {
        this.handleCloseModal();
    }

    async componentDidUpdate(prevProps, prevState, snapshot) {
        if (!prevProps.isOpen && this.props.isOpen) {
            await this.loadStudents(this.state.courseId, this.state.groupId);
        }
    }

    async loadStudents(courseId, groupId) {
        try {
            await this.setState({
                isLoading: true
            });
            let res = await getAllStudentsForAddingToGroup(courseId, groupId);
            const students = res.data.students;
            await this.setState({
                isLoading: false,
                students: students,
                studentId: students.length > 0 ? res.data.students[0].id : -1
            });
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleChangeStudent(e) {
        this.setState({
            studentId: e.target.value,
            responseVisibleError: false,
        });
    }

    handleChangesStudentURL(e) {
        this.setState({
            studentURL: e.target.value,
            URLVisibleError: false,
            responseVisibleError: false,
            invalidForm: false
        });
    }

    handleStudentsRedirectLink(e) {
        e.preventDefault();
        this.handleCloseModal();
        this.props.history.push('/students');
    }

    async handleSubmit(e) {
        e.preventDefault();

        const {notify} = this.props;
        if (!this.validateForm()) {
            return;
        }
        try {
            await this.setState({
                isLoading: true,
            });

            let res = await addStudentToGroup(this.state.courseId, this.state.groupId, {
                courseId: this.state.courseId,
                groupId: this.state.groupId,
                studentId: this.state.studentId,
                vcsRepositoryUrl: this.state.studentURL
            });
            this.props.workflow.addStudentToActiveGroup(res.data);
            let students = this.state.students.filter(s => s.id !== this.state.studentId);
            await this.setState({
                students: students,
                studentId: students.length > 0 ? students[0].id : -1,
                studentURL: '',
                invalidForm: true,
                isLoading: false,
            });
            notify({
                title: 'Успіх!',
                message: `Студента "${res.data.studentFullName}" успішно додано до групи.`,
                status: 'success',
                position: 'tc',
                dismissible: true,
                dismissAfter: 3000
            });
        } catch (err) {
            if (err.status === 400) {
                notify({
                    title: 'Невдача!',
                    message: err.message,
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

    validateForm() {
        const URL = this.state.studentURL;
        const regexp = /^(https:\/\/bitbucket.org\/.+|https:\/\/github.com\/.+)$/;
        if (!regexp.test(URL)) {
            this.setState({
                URLVisibleError: true,
                invalidForm: true
            });
            return false;
        }
        return true;
    }

    handleCloseModal() {
        $(this.modal).modal('hide');
        this.props.onClose();
        this.setState({
            studentId: -1,
            studentURL: '',
            invalidForm: true
        })
    }

    render() {

        const form = (
            <form onSubmit={this.handleSubmit}>
                <div className="form-group">
                    <label htmlFor="selectLanguage">
                        <span className="" id="selectStudent" data-tip
                              data-for='selectStudentFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Студент:
                    </label>
                    <ReactTooltip id='selectStudentFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Студенти, які не включені до жодної групи даного курсу
                    </ReactTooltip>
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="selectStudent">
                                <i className="fa fa-user fa-lg" aria-hidden="true"/>
                            </span>
                        </div>
                        {(() => {
                            let options = this.state.students.map(
                                s =>
                                    <option value={s.id} key={s.id}>
                                        {s.fullName}
                                    </option>
                            );
                            return (
                                <select className="form-control form-control-lg"
                                        value={this.state.studentId}
                                        onChange={this.handleChangeStudent}
                                        disabled={this.state.students.length === 0}>
                                    {options}
                                </select>
                            );
                        })()}
                    </div>
                </div>

                <div className="form-group mt-4">
                    <label htmlFor="inputURL">
                        <span className="" id="inputURL" data-tip
                              data-for='inputURLFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;URL репозиторія стутдента:
                    </label>
                    <ReactTooltip id='inputURLFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Підтримуються сервіси GitHub та Bitbucket
                    </ReactTooltip>
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="inputURL">
                                <i className="fa fa-link fa-lg" aria-hidden="true"/>
                            </span>
                        </div>
                        <input type="text"
                               value={this.state.studentURL}
                               onChange={this.handleChangesStudentURL}
                               className={this.state.URLVisibleError ? "form-control form-control-lg is-invalid" : "form-control form-control-lg"}
                               id="inputURL"/>
                        <div className="invalid-feedback">
                            Некоректне посилання на репоизторій студента!
                        </div>
                    </div>
                </div>
            </form>
        );

        return (
            <div className="modal fade" id={this.props.id} tabIndex="-1" role="dialog" data-backdrop="static"
                 aria-labelledby="assignTaskModalLabel" aria-hidden="true"
                 ref={modal => this.modal = modal}>
                <div className="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
                    {this.state.isLoading
                        ? (<Load/>)
                        : (
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h4 className="modal-title">
                                        Додати студента до групи "{this.props.activeGroup.name}"
                                    </h4>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close"
                                            onClick={this.handleCloseModal}>
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div className="modal-body">
                                    <div className="container-fluid">
                                        <div className="row justify-content-center">
                                            <div className="col-lg-10 col-md-12">

                                                {this.state.students.length > 0
                                                    ? form
                                                    : (
                                                        <div className="alert alert-warning text-center" role="alert">
                                                            Всі студенти в системі вже включені до даного курсу.<br/>
                                                            <a href="/students" className="alert-link"
                                                               onClick={this.handleStudentsRedirectLink}>
                                                                Додати нового студента.
                                                            </a>
                                                        </div>
                                                    )
                                                }

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="modal-footer justify-content-center">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal"
                                            onClick={this.handleCloseModal}>
                                        <i className="fa fa-chevron-circle-left fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                        Повернутися
                                    </button>
                                    <button type="button" className="btn btn-primary"
                                            onClick={this.handleSubmit}
                                            disabled={this.state.students.length === 0 || this.state.invalidForm}>
                                        <i className="fa fa-user-plus fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                        Додати студента
                                    </button>
                                </div>
                            </div>
                        )
                    }
                </div>
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
        workflow: bindActionCreators(workflowActions, dispatch),
        notify: bindActionCreators(notify, dispatch)
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AddStudentToGroupModal));
