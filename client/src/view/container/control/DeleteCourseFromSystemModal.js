import React from "react";
import {bindActionCreators} from "redux";
import * as errorActions from "../../../store/action/errorActions";
import {connect} from "react-redux";
import {matchPath, withRouter} from "react-router-dom";
import $ from 'jquery';
import Load from "../../component/Load";
import {deleteCourseFromSystem} from "../../../api/course";

class DeleteCourseFromSystemModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            courseId: -1,
            isLoading: false
        }

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCloseBtn = this.handleCloseBtn.bind(this);
    }

    componentWillUnmount() {
        this.handleCloseBtn();
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

    async handleSubmit() {
        try {
            await this.setState({
                isLoading: true,
            });
            await deleteCourseFromSystem(this.state.courseId);
            this.handleCloseBtn();
            this.props.history.push(`/courses/`);
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleCloseBtn() {
        $(this.modal).modal('hide');
        this.props.onClose();
    }

    render() {

        return (
            <div className="modal fade" id={this.props.id} tabIndex="-1" role="dialog" data-backdrop="static"
                 aria-labelledby="deleteGroupFromCourseModalLabel" aria-hidden="true"
                 ref={modal => this.modal = modal}>
                {this.state.isLoading ? (<Load/>) : null}
                <div className="modal-dialog modal-dialog-scrollable modal-dialog-centered">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h4 className="modal-title">
                                Видалення курсу "{this.props.activeCourse.name}"
                            </h4>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close"
                                    onClick={this.handleCloseBtn}>
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>
                                Ви впевнені?
                            </p>
                        </div>
                        <div className="modal-footer justify-content-center">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal"
                                    onClick={this.handleCloseBtn}>
                                <i className="fa fa-chevron-circle-left fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                Повернутися
                            </button>
                            <button type="button" className="btn btn-danger"
                                    onClick={this.handleSubmit}>
                                <i className="fa fa-trash fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                Видалити курс
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        activeCourse: state.workflow.activeCourse,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(DeleteCourseFromSystemModal));
