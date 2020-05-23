import React from "react";
import {bindActionCreators} from "redux";
import * as errorActions from "../../../store/action/errorActions";
import * as workflowActions from "../../../store/action/workflowActions";
import {connect} from "react-redux";
import {matchPath, withRouter} from "react-router-dom";
import Load from "../../component/Load";
import ReactTooltip from "react-tooltip";
import $ from 'jquery';
import {addStudentToSystem} from "../../../api/student";
import {notify} from "reapop";

class AddStudentToSystemModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,

            fullName: ''
        }

        this.handleChangesField = this.handleChangesField.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCloseModal = this.handleCloseModal.bind(this);
    }

    componentDidMount() {
        const match = matchPath(this.props.location.pathname, {
            path: "/courses/:courseId",
            exact: false,
            strict: false
        });
        if (match) {
            this.setState({
                courseId: match.params.courseId,
            });
        }
    }

    componentWillUnmount() {
        this.props.onClose();
        $(this.modal).modal('hide');
    }

    handleChangesField(e) {
        const fieldName = [e.target.name];
        this.setState({
            [fieldName]: e.target.value,
        });
        this.setError(fieldName, '', false)
    }

    async handleSubmit() {
        if (!this.validateForm()) {
            return;
        }
        const {notify} = this.props;
        try {
            await this.setState({
                isLoading: true,
            });
            await addStudentToSystem({
                fullName: this.state.fullName
            });
            notify({
                title: 'Успіх!',
                message: `Студента "${this.state.fullName}" успішно додано до системи.`,
                status: 'success',
                position: 'tc',
                dismissible: true,
                dismissAfter: 3000
            });
            await this.setState({
                isLoading: false,
                fullName: ''
            });
        } catch (err) {
            if (err.status === 400 && err.success === false) {
                notify({
                    title: 'Невдача!',
                    message: `Студент з повним ім'ям "${this.state.fullName}" вже існує в системі.`,
                    status: 'error',
                    position: 'tc',
                    dismissible: true,
                    dismissAfter: 3000
                });
                await this.setState({
                    isLoading: false,
                    fullName: ''
                });
            } else {
                this.props.error.throwError(err);
            }
        }
    }

    validateForm() {
        const fullName = this.state.fullName;

        let isValid = true;
        const regexp = /^(https:\/\/bitbucket.org\/.+|https:\/\/github.com\/.+)$/;

        if (!fullName || fullName.length === 0) {
            this.setError('fullName',
                'Буль-ласка заповніть дане поле!');
            isValid = false;
        } else if (!/^([\wА-яЁёІіЇїЄє]+ ?)+$/.test(fullName)) {
            this.setError('fullName',
                "Може містити лище літери, цифри та пробільний символ");
            isValid = false;
        }
        return isValid;
    }

    setError(fieldName, errorMessage, isVisible = true) {
        this.setState({
            [fieldName + 'VisibleError']: isVisible,
            [fieldName + 'MessageError']: errorMessage
        });
    }

    handleCloseModal() {
        this.props.onClose();
        this.setState({
            name: '',
            repositoryPrefixPath: '',
            description: '',
            invalidForm: true
        })
        window.location.reload();
    }

    render() {
        const renderErrorMessage = (fieldName) => {
            if (this.state[fieldName + 'VisibleError'] &&
                this.state[fieldName + 'MessageError']) {
                return (
                    <div className="invalid-feedback">
                        {this.state[fieldName + 'MessageError']}
                    </div>
                )
            }
        };

        const renderFieldStyle = (fieldName, baseStyle = 'form-control form-control-lg ') => {
            const isInvalid = this.state[fieldName + 'VisibleError'];
            return isInvalid
                ? baseStyle + 'is-invalid'
                : baseStyle
        };

        const form = (
            <form>
                <div className="form-group mt-4">
                    <label htmlFor="inputFullName">
                        <span className="" id="inputFullName" data-tip
                              data-for='inputFullNameFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Повне ім'я студента:
                    </label>
                    <ReactTooltip id='inputFullNameFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Унакальне ім'я в студента в системі
                    </ReactTooltip>
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="inputFullName">
                                <i className="fa fa-user fa-lg" aria-hidden="true"/>
                            </span>
                        </div>
                        <input type="text"
                               name={"fullName"}
                               value={this.state.fullName}
                               onChange={this.handleChangesField}
                               className={renderFieldStyle('fullName')}
                               id="inputFullName"
                               maxLength="255"/>
                        {renderErrorMessage('fullName')}
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
                                        Додати студента до курсу
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
                                                {form}
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
                                            onClick={this.handleSubmit}>
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

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        workflow: bindActionCreators(workflowActions, dispatch),
        notify: bindActionCreators(notify, dispatch)
    };
}

export default withRouter(connect(null, mapDispatchToProps)(AddStudentToSystemModal));
