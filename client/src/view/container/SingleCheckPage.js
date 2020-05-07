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
            invalidCodeToPlagDetectionFile: false,
            invalidBaseCodeFile: false,

            programmingLanguageId: 1,
            comparisonSensitivity: 9,
            minimumSimilarityPercent: 1,
            baseCodeZip: null,
            codeToPlagDetectionZip: null,
        }

        this.onSettingsChange = this.onSettingsChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleBackBtn = this.handleBackBtn.bind(this);
        this.handleResetBtn = this.handleResetBtn.bind(this);
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
            data.append('codeToPlagDetectionZip', this.state.codeToPlagDetectionZip);
            if (this.state.baseCodeZip) {
                data.append('baseCodeZip', this.state.baseCodeZip);
            }

            let response = await uploadCodeToPlagDetection(data);

            console.log(response);

            await this.setState({
                isLoading: false,
                isSettings: false,
                result: response.data
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
                            Результати виявлення плагіату
                        </h1>
                    </div>

                    <div className="progress my-5">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-8 col-sm-12">
                            <div className="alert alert-success text-center" role="alert">
                                Перевірка виконана успішно
                            </div>
                        </div>
                        {/*<div className="col-md-8 col-sm-12">*/}
                        {/*    <div className="card bg-secondary text-white border-secondary">*/}
                        {/*        <div className="card-header">Логи</div>*/}
                        {/*        <div className="card-body">*/}
                        {/*            This is some text within a card body.*/}
                        {/*        </div>*/}
                        {/*    </div>*/}
                        {/*</div>*/}
                        <div className="col-md-12 text-center">
                            <button type="button" className="btn btn-success btn-lg mt-3">
                                Переглянути результати&nbsp;&nbsp;
                                <i className="fa fa-external-link fa-lg" aria-hidden="true"/>
                            </button>
                        </div>
                    </div>

                    <div className="progress my-5">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-12 text-center">
                            <button type="button" className="btn btn-primary btn-lg mx-3" onClick={this.handleBackBtn}>
                                <i className="fa fa-chevron-circle-left fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                До налаштувань
                            </button>
                            <button type="button" className="btn btn-primary btn-lg mx-3" onClick={this.handleResetBtn}>
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
