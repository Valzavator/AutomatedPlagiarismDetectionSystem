import React from "react";
// import Load from "../../component/Load";
import {bindActionCreators} from "redux";
import * as errorActions from "../../../store/action/errorActions";
import * as workflowActions from "../../../store/action/workflowActions";
import {connect} from "react-redux";
import PlagDetectionSettings from "../../component/PlagDetectionSettings";
import {
    downloadTaskGroupPlagDetectionSettings,
    assignNewTaskGroup
} from "../../../api/plagiarism";
import {matchPath, withRouter} from "react-router-dom";
import * as moment from "moment";

class AssignTaskModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            courseId: -1,
            isLoading: false,
            invalidForm: false,

            taskId: -1,
            expiryDate: Date.now(),
            programmingLanguageId: 1,
            detectionType: 'GROUP',
            comparisonSensitivity: 9,
            minimumSimilarityPercent: 20,
            saveLog: true,
            baseCodeZip: null,
        }

        this.onSettingsChange = this.onSettingsChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCloseBtn = this.handleCloseBtn.bind(this);
    }

    componentWillUnmount() {
        this.props.onClose();
    }

    onSettingsChange(settings) {
        this.setState({
            ...settings
        })
    }

    componentDidMount() {
        const match = matchPath(this.props.location.pathname, {
            path: "/courses/:courseId/groups/:groupId",
            exact: false,
            strict: false
        });
        this.setState({
            courseId: match.params.courseId,
            groupId: match.params.groupId
        });
    }

    async handleSubmit() {
        try {
            await this.setState({
                isLoading: true,
            });

            const data = new FormData();
            //using File API to get chosen file
            data.append('taskId', this.state.taskId);
            data.append('expiryDate', new Date(this.state.expiryDate).toUTCString());
            data.append('programmingLanguageId', this.state.programmingLanguageId);
            data.append('detectionType', this.state.detectionType);
            data.append('comparisonSensitivity', this.state.comparisonSensitivity);
            data.append('minimumSimilarityPercent', this.state.minimumSimilarityPercent);
            data.append('saveLog', this.state.saveLog);
            if (this.state.baseCodeZip) {
                data.append('baseCodeZip', this.state.baseCodeZip);
            }

            data.forEach((v, k) => console.log(k + " ---- " + v));

            await assignNewTaskGroup(this.state.courseId, this.state.groupId, data);
            await this.setState({
                isLoading: false,
            });
            window.location.reload();
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
                    <div className="modal-content">
                        <div className="modal-header">
                            <h4 className="modal-title">
                                Назначити завдання для Групи "{this.props.activeGroup.name}"
                            </h4>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close" onClick={this.handleCloseBtn}>
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="container-fluid">
                                <div className="row justify-content-center">
                                    <div className="col-lg-8">
                                        {this.props.isOpen
                                            ? (
                                                <PlagDetectionSettings
                                                    loadSettings={() => downloadTaskGroupPlagDetectionSettings(this.state.courseId, this.state.groupId)}
                                                    courseId={this.props.match.params.courseId}
                                                    onSettingsChange={this.onSettingsChange}
                                                    onSubmitForm={this.handleSubmit}
                                                    defaultState={this.state}
                                                />
                                            )
                                            : null
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal"
                                    onClick={this.handleCloseBtn}>
                                Повернутися
                            </button>
                            <button type="button" className="btn btn-primary"
                                    onClick={this.handleSubmit}
                                    disabled={this.state.invalidForm}>
                                Зберегти
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
        workflow: bindActionCreators(workflowActions, dispatch),
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AssignTaskModal));
