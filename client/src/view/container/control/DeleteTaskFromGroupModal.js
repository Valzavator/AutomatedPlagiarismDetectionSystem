import React from "react";
import {bindActionCreators} from "redux";
import * as errorActions from "../../../store/action/errorActions";
import {connect} from "react-redux";
import {matchPath, withRouter} from "react-router-dom";
import $ from 'jquery';
import Load from "../../component/Load";
import {deleteTaskGroup} from "../../../api/taskGroup";

class DeleteTaskFromGroupModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            courseId: -1,
            groupId: -1,
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
            path: "/courses/:courseId/groups/:groupId/tasks/:taskId",
            exact: false,
            strict: false
        });
        if (match) {
            this.setState({
                courseId: match.params.courseId,
                groupId: match.params.groupId,
                taskId: match.params.taskId
            });
        }
    }

    async handleSubmit() {
        try {
            await this.setState({
                isLoading: true,
            });
            await deleteTaskGroup(this.state.courseId, this.state.groupId, this.state.taskId);
            this.handleCloseBtn();
            this.props.history.push(`/courses/${this.state.courseId}/groups/${this.state.groupId}`);
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
                                Видалення завдання для групи
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
                                Видалити завдання
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
        activeGroup: state.workflow.activeGroup,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(DeleteTaskFromGroupModal));
