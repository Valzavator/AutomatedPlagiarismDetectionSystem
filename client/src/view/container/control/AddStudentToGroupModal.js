import React from "react";
import {bindActionCreators} from "redux";
import * as errorActions from "../../../store/action/errorActions";
import * as workflowActions from "../../../store/action/workflowActions";
import {connect} from "react-redux";
import {matchPath, withRouter} from "react-router-dom";
import {getAllStudentsForAddingToGroup} from "../../../api/student";
import Load from "../../component/Load";

class AddStudentToGroupModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            invalidForm: false,
            students: []
        }

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCloseBtn = this.handleCloseBtn.bind(this);
    }

    componentDidMount() {
        const match = matchPath(this.props.location.pathname, {
            path: "/courses/:courseId/groups/:groupId",
            exact: false,
            strict: false
        });
        if (match) {
            // this.loadStudents(match.params.courseId, match.params.groupId);
            this.setState({
                courseId: match.params.courseId,
                groupId: match.params.groupId
            });
        }
    }

    componentWillUnmount() {
        this.props.onClose();
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
            await this.setState({
                isLoading: false,
                students: res.data.students
            });
            console.log(this.state.students);
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    async handleSubmit() {
        try {
            await this.setState({
                isLoading: true,
            });
            //
            // const data = new FormData();
            // //using File API to get chosen file
            // data.append('taskId', this.state.taskId);
            // data.append('expiryDate', new Date(this.state.expiryDate).toUTCString());
            // data.append('programmingLanguageId', this.state.programmingLanguageId);
            // data.append('detectionType', this.state.detectionType);
            // data.append('comparisonSensitivity', this.state.comparisonSensitivity);
            // data.append('minimumSimilarityPercent', this.state.minimumSimilarityPercent);
            // data.append('saveLog', this.state.saveLog);
            // if (this.state.baseCodeZip) {
            //     data.append('baseCodeZip', this.state.baseCodeZip);
            // }
            // await assignNewTaskGroup(this.state.courseId, this.state.groupId, data);
            // await this.setState({
            //     isLoading: false,
            // });
            // window.location.reload();
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleCloseBtn() {
        this.props.onClose();
        this.setState({
            taskId: -1,
            expiryDate: Date.now(),
            programmingLanguageId: 1,
            detectionType: 'GROUP',
            comparisonSensitivity: 9,
            minimumSimilarityPercent: 20,
            saveLog: true,
            baseCodeZip: null,
        })
    }

    render() {

        return (
            <div className="modal fade" id={this.props.id} tabIndex="-1" role="dialog" data-backdrop="static"
                 aria-labelledby="assignTaskModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-xl modal-dialog-scrollable modal-dialog-centered">
                    {this.state.isLoading
                        ? (<Load/>)
                        : (
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h4 className="modal-title">
                                        Додати студента до групи "{this.props.activeGroup.name}"
                                    </h4>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close"
                                            onClick={this.handleCloseBtn}>
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div className="modal-body">
                                    <div className="container-fluid">
                                        <div className="row justify-content-center">
                                            <div className="col-lg-8">
                                                {/*{this.props.isOpen*/}
                                                {/*    ? (*/}
                                                {/*        <PlagDetectionSettings*/}
                                                {/*            loadSettings={() => downloadTaskGroupPlagDetectionSettings(this.state.courseId, this.state.groupId)}*/}
                                                {/*            courseId={this.props.match.params.courseId}*/}
                                                {/*            onSettingsChange={this.onSettingsChange}*/}
                                                {/*            onSubmitForm={this.handleSubmit}*/}
                                                {/*            defaultState={this.state}*/}
                                                {/*        />*/}
                                                {/*    )*/}
                                                {/*    : null*/}
                                                {/*}*/}
                                                {this.state.students.length}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal"
                                            onClick={this.handleCloseBtn}>
                                        <i className="fa fa-chevron-circle-left fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                        Повернутися
                                    </button>
                                    <button type="button" className="btn btn-primary"
                                            onClick={this.handleSubmit}
                                            disabled={this.state.invalidForm}>
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
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AddStudentToGroupModal));
