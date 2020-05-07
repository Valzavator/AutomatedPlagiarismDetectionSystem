import React from "react";
import Load from "../component/Load";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import {connect} from "react-redux";
import {downloadPlagDetectionSettings, uploadCodeToPlagDetection} from "../../api/plagiarism";
import PlagDetectionSettings from "../component/PlagDetectionSettings";

class SingleCheckPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isSettings: true,
            isLoading: false,
            invalidCodeToPlagDetectionFile: true,
            invalidBaseCodeFile: false,

            programmingLanguageId: 1,
            comparisonSensitivity: 9,
            minimumSimilarityPercent: 1,
            saveLog: true,
            baseCodeZip: null,
            codeToPlagDetectionZip: null,

            plagDetectResult: {
                resultMessage: '',
                log: '',
                isSuccessful: false,
                resultPath: ''
            }
        }

        this.onSettingsChange = this.onSettingsChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleBackBtn = this.handleBackBtn.bind(this);
        this.handleResetBtn = this.handleResetBtn.bind(this);
        this.handleResultBtn = this.handleResultBtn.bind(this);
    }

    componentDidMount() {
        this.setState({})
    }

    onSettingsChange(settings) {
        this.setState({
            ...settings
        })
    }

    async handleSubmit() {
        try {
            await this.setState({
                isLoading: true,
            });

            const data = new FormData();
            //using File API to get chosen file
            data.append('programmingLanguageId', this.state.programmingLanguageId);
            data.append('comparisonSensitivity', this.state.comparisonSensitivity);
            data.append('minimumSimilarityPercent', this.state.minimumSimilarityPercent);
            data.append('saveLog', this.state.saveLog);
            data.append('codeToPlagDetectionZip', this.state.codeToPlagDetectionZip);
            if (this.state.baseCodeZip) {
                data.append('baseCodeZip', this.state.baseCodeZip);
            }

            let response = await uploadCodeToPlagDetection(data);

            await this.setState({
                isLoading: false,
                isSettings: false,
                plagDetectResult: {
                    ...this.state.plagDetectResult,
                    ...response.data
                }
            });
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleBackBtn() {
        this.setState({
            isSettings: true
        })
    }

    handleResetBtn() {
        this.setState({
            isSettings: true,
            programmingLanguageId: 1,
            comparisonSensitivity: 9,
            minimumSimilarityPercent: 1,
            baseCodeZip: null,
            codeToPlagDetectionZip: null,
        })
    }

    handleResultBtn() {
        window.open(this.state.plagDetectResult.resultPath, "_blank")
    }

    render() {
        const settingsMenu = (
            <div className="jumbotron jumbotron-fluid w-75 my-5">
                <div className="container ">
                    <div className="d-flex justify-content-center align-items-center">
                        <h1 className="" style={{fontSize: '3vw'}}>
                            Налаштування виявлення плагіату
                        </h1>
                    </div>

                    <div className="progress my-3">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-7 col-sm-12">
                            <PlagDetectionSettings
                                loadSettings={downloadPlagDetectionSettings}
                                onSettingsChange={this.onSettingsChange}
                                onSubmitForm={this.handleSubmit}
                                defaultState={this.state}
                                singleCheck
                            />
                        </div>
                    </div>

                    <div className="progress my-3">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-12 col-sm-12 text-center">
                            <button type="button"
                                    className="btn btn-primary btn-lg"
                                    onClick={this.handleSubmit}
                                    disabled={this.state.invalidCodeToPlagDetectionFile || this.state.invalidBaseCodeFile}>
                                Виконати перевірку&nbsp;&nbsp;
                                <i className="fa fa-check-circle-o fa-lg" aria-hidden="true"/>
                            </button>
                        </div>
                    </div>

                </div>

            </div>
        );

        const resultMenu = (
            <div className="jumbotron jumbotron-fluid w-75 my-5">
                <div className="container">

                    <div className="d-flex justify-content-center align-items-center">
                        <h1 className="" style={{fontSize: '3vw'}}>
                            Результати виявлення плагіату&nbsp;&nbsp;&nbsp;
                            {this.state.plagDetectResult.isSuccessful
                                ? (
                                    <span className="badge badge-success">
                                        <i className="fa fa-check-circle-o fa-lg" aria-hidden="true"/>
                                    </span>
                                )
                                : (
                                    <span className="badge badge-danger">
                                        <i className="fa fa-times-circle fa-lg" aria-hidden="true"/>
                                    </span>
                                )
                            }
                        </h1>
                    </div>

                    <div className="progress my-3">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        {this.state.plagDetectResult.isSuccessful
                            ? (
                                <div className="col-md-12 col-sm-12">
                                    <div className="alert alert-success" role="alert">
                                        <h4 className="alert-heading text-center">Перевірка виконана успішно</h4>
                                        <hr/>
                                        <p className="mb-0" style={{wordWrap: 'break-word'}}>
                                            {this.state.plagDetectResult.resultMessage}
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
                                            {this.state.plagDetectResult.resultMessage}
                                        </p>
                                    </div>
                                </div>
                            )
                        }
                        {this.state.plagDetectResult.log && this.state.plagDetectResult.log.length > 0
                            ? (
                                <div className="col-md-12 col-sm-12 my-3">
                                    <div className="card bg-light">
                                        <div className="card-header bg-light text-center h3">Журнал виконання</div>
                                        <div className="card-body bg-dark overflow-auto" style={{maxHeight: '500px'}}>
                                            <p className="card-text" style={{whiteSpace: 'pre-line'}}>
                                                <samp>
                                                    {this.state.plagDetectResult.log}
                                                </samp>
                                            </p>
                                        </div>
                                        <div className="card-footer bg-light"/>
                                    </div>
                                </div>
                            )
                            : null
                        }

                        {this.state.plagDetectResult.isSuccessful
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

                    <div className="progress mb-3">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-12 text-center">
                            <button type="button" className="btn btn-primary btn-lg mx-3 mt-3"
                                    onClick={this.handleBackBtn}>
                                <i className="fa fa-chevron-circle-left fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                До налаштувань
                            </button>
                            <button type="button" className="btn btn-primary btn-lg mx-3 mt-3"
                                    onClick={this.handleResetBtn}>
                                <i className="fa fa-undo fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                Нова перевірка
                            </button>
                        </div>
                    </div>

                </div>
            </div>
        );

        return (
            <div>
                {this.state.isLoading ? <Load/> : null}
                <div className="row justify-content-center">
                    {this.state.isSettings ? settingsMenu : resultMenu}
                </div>
            </div>
        )
    }
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
    };
}

export default connect(null, mapDispatchToProps)(SingleCheckPage);
