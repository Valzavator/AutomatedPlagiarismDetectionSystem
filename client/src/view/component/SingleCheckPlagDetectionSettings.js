import React from "react";
import ReactTooltip from "react-tooltip";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import {connect} from "react-redux";
import Load from "./Load";

function getOr(value, orValue) {
    return value ? value : orValue;
}

class PlagDetectionSettings extends React.Component {
    constructor(props) {
        super(props);

        const defaultState = this.props.defaultState ? this.props.defaultState : {};
        defaultState.programmingLanguageId = getOr(defaultState.programmingLanguageId, 1);
        defaultState.comparisonSensitivity = getOr(defaultState.comparisonSensitivity, 9);
        defaultState.minimumSimilarityPercent = getOr(defaultState.minimumSimilarityPercent, 1);
        defaultState.saveLog = getOr(defaultState.saveLog, true);
        defaultState.baseCodeZip = getOr(defaultState.baseCodeZip, null);
        defaultState.codeToPlagDetectionZip = getOr(defaultState.codeToPlagDetectionZip, null);

        this.state = {
            isLoading: true,
            languages: [],
            tasks: [],
            detectionTypes: [],

            ...defaultState,

            invalidBaseCodeFile: false,
            invalidBaseCodeMessage: "",
            baseCodeLabel: defaultState.baseCodeZip ? defaultState.baseCodeZip.name : "Обрати файл...",
            invalidCodeToPlagDetectionFile: !defaultState.codeToPlagDetectionZip,
            invalidCodeToPlagDetectionFileMessage: "Архів обов'язковий до завантаження!",
            codeToPlagDetectionLabel: defaultState.codeToPlagDetectionZip ? defaultState.codeToPlagDetectionZip.name : "Обрати файл..."
        }

        this.handleChangeSimilarityPercent = this.handleChangeSimilarityPercent.bind(this);
        this.handleChangeComparisonSensitivity = this.handleChangeComparisonSensitivity.bind(this);
        this.handleChangeLanguage = this.handleChangeLanguage.bind(this);
        this.handleChangeSaveLog = this.handleChangeSaveLog.bind(this);
        this.handleUploadBaseCodeFile = this.handleUploadBaseCodeFile.bind(this);
        this.handleUploadToPlagDetectionFile = this.handleUploadToPlagDetectionFile.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.loadSettings();
    }

    async loadSettings() {
        try {
            const response = await this.props.loadSettings(this.props.courseId);
            const languages = response.data.languages;
            let activeLanguage = languages.find(l => l.id === this.state.programmingLanguageId);
            await this.setState({
                languages: languages,
                isLoading: false,
                fileTypesSupport: activeLanguage.fileTypesSupport,
                programmingLanguageName: activeLanguage.name
            }, () => this.setAllChanges());
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleChangeLanguage(e) {
        let activeLanguage = this.state.languages.find(l => l.id === parseInt(e.target.value));
        const newState = {
            programmingLanguageId: activeLanguage.id,
            comparisonSensitivity: activeLanguage.defaultComparisonSensitivity,
            programmingLanguageName: activeLanguage.name
        };
        this.setState({
            ...newState,
            fileTypesSupport: activeLanguage.fileTypesSupport
        });
        this.props.onSettingsChange({
            ...newState
        });
    }

    handleChangeSaveLog(e) {
        this.setState({
            saveLog: e.target.checked,
        });
        this.props.onSettingsChange({
            saveLog: e.target.checked,
        });
    }

    handleChangeComparisonSensitivity(e) {
        this.setState({
            comparisonSensitivity: e.target.value
        });
        this.props.onSettingsChange({
            comparisonSensitivity: e.target.value,
        });
    }

    handleChangeSimilarityPercent(e) {
        this.setState({
            minimumSimilarityPercent: e.target.value
        });
        this.props.onSettingsChange({
            minimumSimilarityPercent: e.target.value,
        });
    }

    handleUploadBaseCodeFile(e) {
        const file = e.target.files[0];
        if (!file) {
            this.setState({
                baseCodeZip: null,
                baseCodeLabel: "Обрати файл..."
            });
            this.props.onSettingsChange({
                baseCodeZip: null
            });
        } else if (file.type.search("zip") === -1) {
            this.setState({
                invalidBaseCodeFile: true,
                invalidBaseCodeMessage: "Необхідний формат файлу .zip!"
            });
            this.props.onSettingsChange({
                invalidBaseCodeFile: true
            });
        } else if (file.size > 5242880) { // 5MB
            this.setState({
                invalidBaseCodeFile: true,
                invalidBaseCodeMessage: "Розмір архіву не може перевищувати 5 MB!"
            });
            this.props.onSettingsChange({
                invalidBaseCodeFile: true
            });
        } else {
            this.setState({
                baseCodeZip: file,
                invalidBaseCodeFile: false,
                baseCodeLabel: file.name
            });
            this.props.onSettingsChange({
                baseCodeZip: file,
                invalidBaseCodeFile: false
            });
        }
    }

    handleUploadToPlagDetectionFile(e) {
        const file = e.target.files[0];
        if (!file) {
            this.setState({
                invalidCodeToPlagDetectionFile: true,
                invalidCodeToPlagDetectionFileMessage: "Архів обов'язковий до завантаження!",
                codeToPlagDetectionLabel: "Обрати файл..."
            });
            this.props.onSettingsChange({
                codeToPlagDetectionZip: null,
                invalidCodeToPlagDetectionFile: true
            });
        } else if (file.type.search("zip") === -1) {
            this.setState({
                invalidCodeToPlagDetectionFile: true,
                invalidCodeToPlagDetectionFileMessage: "Необхідний формат файлу .zip!"
            });
            this.props.onSettingsChange({
                invalidCodeToPlagDetectionFile: true
            });
        } else if (file.size > 5242880) { // 5MB
            this.setState({
                invalidCodeToPlagDetectionFile: true,
                invalidCodeToPlagDetectionFileMessage: "Розмір архіву не може перевищувати 5 MB!"
            });
            this.props.onSettingsChange({
                invalidCodeToPlagDetectionFile: true
            });
        } else {
            this.setState({
                codeToPlagDetectionZip: file,
                invalidCodeToPlagDetectionFile: false,
                codeToPlagDetectionLabel: file.name
            });
            this.props.onSettingsChange({
                codeToPlagDetectionZip: file,
                invalidCodeToPlagDetectionFile: false
            });
        }
    }

    handleSubmit(e) {
        e.preventDefault();
        this.setAllChanges();
        this.props.onSubmitForm();
    }

    setAllChanges() {
        this.props.onSettingsChange({
            programmingLanguageName: this.state.programmingLanguageName,
            programmingLanguageId: this.state.programmingLanguageId,
            comparisonSensitivity: this.state.comparisonSensitivity,
            minimumSimilarityPercent: this.state.minimumSimilarityPercent,
            saveLog: this.state.saveLog,
            baseCodeZip: this.state.baseCodeZip,
            invalidBaseCodeFile: this.state.invalidBaseCodeFile,
            codeToPlagDetectionZip: this.state.codeToPlagDetectionZip,
            invalidCodeToPlagDetectionFile: this.state.invalidCodeToPlagDetectionFile,
        });
    }

    render() {

        return (
            <form onSubmit={this.handleSubmit}>
                {this.state.isLoading ? <Load/> : null}

                <div className="form-group">
                    <label htmlFor="selectLanguage">
                        <span className="" id="selectLanguage" data-tip
                              data-for='selectLanguageFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Мова програмування:
                    </label>
                    <ReactTooltip id='selectLanguageFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Мова, над якою буде проведено аналіз
                    </ReactTooltip>
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="selectLanguage">
                                <i className="fa fa-language fa-lg" aria-hidden="true"/>
                            </span>
                        </div>
                        {(() => {
                            let options = this.state.languages.map(
                                l =>
                                    <option value={l.id} key={l.id}>
                                        {l.name}
                                    </option>
                            );
                            return (
                                <select className="form-control form-control-lg"
                                        id="selectLanguage" data-for='fileTypesFAQ' data-tip
                                        value={this.state.programmingLanguageId}
                                        onChange={this.handleChangeLanguage}>
                                    {options}
                                </select>
                            );
                        })()}
                        <ReactTooltip id='fileTypesFAQ' place="right" type='info'
                                      multiline={true}
                                      effect="solid">
                            {this.state.fileTypesSupport}
                        </ReactTooltip>
                    </div>
                </div>

                <div className="form-group mt-4">
                    <label htmlFor="selectSensitivity">
                        <span className="" id="selectSensitivity" data-tip
                              data-for='selectSensitivityFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Чутливість порівняння:
                    </label>
                    <ReactTooltip id='selectSensitivityFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Менше значення підвищує чутливість
                    </ReactTooltip>
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="selectSensitivity">
                                <i className="fa fa-cogs fa-lg" aria-hidden="true"/>
                            </span>
                        </div>
                        <input type="number" min="1" max="2147483647"
                               value={this.state.comparisonSensitivity}
                               onChange={this.handleChangeComparisonSensitivity}
                               className="form-control form-control-lg"
                               id="selectSensitivity"/>
                    </div>
                </div>

                <div className="form-group mt-4">
                    <label htmlFor="selectPercent">
                        <span className="" id="selectPercent" data-tip
                              data-for='selectPercentFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Мінімальний відсоток співпадіння:
                    </label>
                    <ReactTooltip id='selectPercentFAQ' place="left" type='info' effect="solid"
                                  multiline={true}>
                        Мінімальний відсоток співпадіння двох порівнюваних програм, який буде показано в
                        результатах
                    </ReactTooltip>
                    <div className="input-group">
                        <input type="range" className="custom-range" id="selectPercent"
                               data-tip
                               data-for='selectPercent'
                               defaultValue={this.state.minimumSimilarityPercent}
                               step="1"
                               min="1"
                               max="100"
                               onChange={this.handleChangeSimilarityPercent}/>
                        <ReactTooltip id='selectPercent' place="top" type='info' effect="float">
                            {this.state.minimumSimilarityPercent}
                        </ReactTooltip>
                    </div>
                </div>

                <div className="form-group mt-4">
                    <div className="custom-control custom-switch">
                        <input type="checkbox" className="custom-control-input " id="customSwitch1"
                               checked={this.state.saveLog}
                               onChange={this.handleChangeSaveLog}/>
                        <label className="custom-control-label " htmlFor="customSwitch1">
                            Зберігати журнал виконання?
                        </label>
                    </div>
                </div>

                <div className="form-group mt-4">
                    <label htmlFor="selectTemplateFolder">
                        <span className="" id="selectTemplateFolder" data-tip
                              data-for='selectTemplateFolderFAQ'>
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>
                        &nbsp;Шаблонний код:
                    </label>
                    <ReactTooltip id='selectTemplateFolderFAQ' place="left" type='info'
                                  effect="solid" multiline={true}>
                        Архів з шаблонним кодом, який буде виключено з порівняння.
                        Обов'язково в форматі <strong>.zip</strong>
                    </ReactTooltip>
                    <div className="custom-file">
                        <input type="file" className="custom-file-input form-control-lg"
                               accept=".zip"
                               id="selectTemplateFolder"
                               onChange={this.handleUploadBaseCodeFile}/>
                        <label className="custom-file-label form-control-lg"
                               htmlFor="selectTemplateFolder">
                            {this.state.baseCodeLabel}
                        </label>
                        {this.state.invalidBaseCodeFile
                            ? (
                                <div className="text-danger">
                                    {this.state.invalidBaseCodeMessage}
                                </div>
                            )
                            : null
                        }
                    </div>
                </div>

                <div className="form-group mt-4">
                    <label htmlFor="selectArchive">
                        <span id="selectArchive"
                              data-tip
                              data-for='selectArchiveFAQ'
                              data-iscapture="true">
                                <i className="fa fa-question-circle-o fa-lg"
                                   aria-hidden="true"/>
                        </span>&nbsp;
                        Aрхів з програмними проектами:
                    </label>
                    <ReactTooltip id='selectArchiveFAQ' place="left" type='info'
                                  effect="solid" multiline={true}>
                        Архів з програмними проектами форматі <strong>.zip</strong>
                    </ReactTooltip>
                    <div className="custom-file">
                        <input type="file" className="custom-file-input form-control-lg"
                               accept=".zip"
                               id="selectArchive"
                               onChange={this.handleUploadToPlagDetectionFile}
                               required/>
                        <label className="custom-file-label form-control-lg"
                               htmlFor="selectArchive">
                            {this.state.codeToPlagDetectionLabel}
                        </label>
                        {this.state.invalidCodeToPlagDetectionFile
                            ? (
                                <div className="text-danger">
                                    {this.state.invalidCodeToPlagDetectionFileMessage}
                                </div>
                            )
                            : null
                        }
                    </div>
                </div>
            </form>
        )
    }
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
    };
}

export default connect(null, mapDispatchToProps)(PlagDetectionSettings);
