import React from "react";
import {bindActionCreators} from "redux";
import * as errorActions from "../../../store/action/errorActions";
import * as workflowActions from "../../../store/action/workflowActions";
import {connect} from "react-redux";
import {matchPath, withRouter} from "react-router-dom";
import Load from "../../component/Load";
import ReactTooltip from "react-tooltip";
import $ from 'jquery';
import {addCourseToSystem} from "../../../api/course";

class AddCourseToSystemModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,

            name: '',
            description: ''
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
        this.handleCloseModal();
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
        try {
            await this.setState({
                isLoading: true,
            });
            let res = await addCourseToSystem({
                name: this.state.name,
                description: this.state.description
            });
            this.props.history.push(`/courses/${res.data.id}`);
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    validateForm() {
        const name = this.state.name;
        let isValid = true;
        if (!name || name.length === 0) {
            this.setError('name',
                'Буль-ласка заповніть дане поле!');
            isValid = false;
        } else if (!/^[\w \-А-яЁёІіЇїЄє]+$/.test(name)) {
            this.setError('name',
                "Назва може містити лище літери, цифри, пробільний символ та символи дефісу і нижньго підкреслення");
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
        $(this.modal).modal('hide');
        this.props.onClose();
        this.setState({
            name: '',
            description: ''
        })
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
                    <label htmlFor="inputName">
                        <span className="" id="inputName" data-tip
                              data-for='inputNameFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Назва курсу:
                    </label>
                    <ReactTooltip id='inputNameFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Назва курсу
                    </ReactTooltip>
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="inputURL">
                                <i className="fa fa-book fa-lg" aria-hidden="true"/>
                            </span>
                        </div>
                        <input type="text"
                               name={"name"}
                               value={this.state.name}
                               onChange={this.handleChangesField}
                               className={renderFieldStyle('name')}
                               id="inputName"
                               maxLength="255"/>
                        {renderErrorMessage('name')}
                    </div>
                </div>
                <div className="form-group mt-4">
                    <label htmlFor="inputDescription">
                        <span className="" id="inputDescription" data-tip
                              data-for='inputDescriptionFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Опис курсу:
                    </label>
                    <ReactTooltip id='inputDescriptionFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Опис курсу
                    </ReactTooltip>
                    <div className="input-group">
                        <textarea rows="5"
                                  name={"description"}
                                  value={this.state.description}
                                  onChange={this.handleChangesField}
                                  id="inputDescription"
                                  className={renderFieldStyle('description')}
                                  maxLength="1000"/>
                        {renderErrorMessage('description')}
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
                                        Створити новий курс
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
                                        <i className="fa fa-book fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                        Додати курс
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
    };
}

export default withRouter(connect(null, mapDispatchToProps)(AddCourseToSystemModal));
