import React from "react";
import ReactTooltip from "react-tooltip";
import Datetime from "react-datetime";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import {connect} from "react-redux";
import Load from "./Load";
import * as moment from "moment";
import $ from "jquery";

function getOr(value, orValue) {
    return value ? value : orValue;
}

class PlagDetectionSettings extends React.Component {
    constructor(props) {
        super(props);

        const defaultState = this.props.defaultState ? this.props.defaultState : {};
        defaultState.taskId = getOr(defaultState.taskId, -1);
        defaultState.programmingLanguageId = getOr(defaultState.programmingLanguageId, 1);
        defaultState.comparisonSensitivity = getOr(defaultState.comparisonSensitivity, 9);
        defaultState.minimumSimilarityPercent = getOr(defaultState.minimumSimilarityPercent, 1);
        defaultState.saveLog = getOr(defaultState.saveLog, true);
        defaultState.baseCodeZip = getOr(defaultState.baseCodeZip, null);
        defaultState.expiryDate = getOr(defaultState.expiryDate, Date.now());
        defaultState.detectionType = getOr(defaultState.detectionType, 'GROUP');

        this.state = {
            isLoading: true,
            languages: [],
            tasks: [],
            detectionTypes: [],

            ...defaultState,

            invalidForm: true,
            invalidBaseCodeFile: false,
            invalidBaseCodeMessage: "",
            baseCodeLabel: defaultState.baseCodeZip ? defaultState.baseCodeZip.name : "Обрати файл...",
        }

        this.handleChangeSimilarityPercent = this.handleChangeSimilarityPercent.bind(this);
        this.handleChangeComparisonSensitivity = this.handleChangeComparisonSensitivity.bind(this);
        this.handleChangeExpireDate = this.handleChangeExpireDate.bind(this);
        this.handleChangeTask = this.handleChangeTask.bind(this);
        this.handleChangeLanguage = this.handleChangeLanguage.bind(this);
        this.handleChangeTypeDetection = this.handleChangeTypeDetection.bind(this);
        this.handleChangeSaveLog = this.handleChangeSaveLog.bind(this);
        this.handleUploadBaseCodeFile = this.handleUploadBaseCodeFile.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.loadSettings();
    }

    async loadSettings() {
        try {
            const response = await this.props.loadSettings();
            const tasks = response.data.tasks;
            const detectionTypes = response.data.detectionTypes;
            const languages = response.data.languages;
            let activeLanguage = languages.find(l => l.id === this.state.programmingLanguageId);
            await this.setState({
                isLoading: false,

                tasks: tasks,
                detectionTypes: detectionTypes,
                languages: languages,

                taskId: tasks && tasks.length > 0 ? tasks[0].id : -1,

                fileTypesSupport: activeLanguage.fileTypesSupport,
                invalidForm: !tasks || tasks.length === 0
            }, () => this.setAllChanges());
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleChangeExpireDate(date) {
        const expiryDate = moment(date).format('YYYY-MM-DD HH:mm:ss')
        if (expiryDate !== 'Invalid date') {
            this.setState({
                expiryDate: expiryDate,
                invalidForm: false,
            });
            this.props.onSettingsChange({
                expiryDate: expiryDate,
                invalidForm: false,
            });
        } else {
            this.setState({
                invalidForm: true,
            });
            this.props.onSettingsChange({
                invalidForm: true,
            });
        }
    }

    handleChangeTask(e) {
        this.setState({
            taskId: e.target.value,
        });
        this.props.onSettingsChange({
            taskId: e.target.value,
        });
    }

    handleChangeLanguage(e) {
        let activeLanguage = this.state.languages.find(l => l.id === parseInt(e.target.value));
        const newState = {
            programmingLanguageId: activeLanguage.id,
            comparisonSensitivity: activeLanguage.defaultComparisonSensitivity,
        };
        this.setState({
            ...newState,
            fileTypesSupport: activeLanguage.fileTypesSupport
        });
        this.props.onSettingsChange({
            ...newState
        });
    }

    handleChangeTypeDetection(e) {
        let activeDetectionType = this.state.detectionTypes.find(dt => dt === e.target.value);
        this.setState({
            detectionType: activeDetectionType,
        });
        this.props.onSettingsChange({
            detectionType: activeDetectionType,
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
            // } else if (file && file.size > 50000) {
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

    handleSubmit(e) {
        e.preventDefault();
        this.setAllChanges();
        this.props.onSubmitForm();
    }

    setAllChanges() {
        this.props.onSettingsChange({
            programmingLanguageId: this.state.programmingLanguageId,
            comparisonSensitivity: this.state.comparisonSensitivity,
            minimumSimilarityPercent: this.state.minimumSimilarityPercent,
            saveLog: this.state.saveLog,
            baseCodeZip: this.state.baseCodeZip,
            detectionType: this.state.detectionType,
            taskId: this.state.taskId,
            expiryDate: this.state.expiryDate,

            invalidForm: this.state.invalidForm || this.state.invalidBaseCodeFile,
        });
    }

    render() {

        const renderDateTimeInput = (props) => {
            return (
                <div className="input-group">
                    <div className="input-group-prepend">
                                <span className="input-group-text" id="selectTypeComparing">
                                    <i className="fa fa-calendar fa-lg" aria-hidden="true"/>
                                </span>
                    </div>
                    <input {...props} className="form-control form-control-lg"/>
                </div>
            );
        }

        const yesterday = Datetime.moment().subtract(1, 'day');
        const validDateTime = (current) => {
            return current.isAfter(yesterday);
        }

        return (
            <form onSubmit={this.handleSubmit}>
                {this.state.isLoading ? <Load/> : null}

                {this.state.tasks.length > 0
                ? (
                        <div className="form-group">
                            <label htmlFor="selectTask">
                                <span className="" id="selectTask" data-tip
                                      data-for='selectTaskFAQ'>
                                        <i className="fa fa-question-circle-o fa-lg"
                                           aria-hidden="true"/>
                                </span>
                                &nbsp;Завдання:
                            </label>
                            <ReactTooltip id='selectTaskFAQ' place="left" type='info'
                                          multiline={true}
                                          effect="solid">
                                Завдання курсу
                            </ReactTooltip>
                            <div className="input-group">
                                <div className="input-group-prepend">
                            <span className="input-group-text" id="selectTask">
                                <i className="fa fa-thumb-tack fa-lg" aria-hidden="true"/>
                            </span>
                                </div>
                                {(() => {
                                    let options = this.state.tasks.map(
                                        l =>
                                            <option value={l.id} key={l.id}>
                                                {l.name}
                                            </option>
                                    );
                                    return (
                                        <select className="form-control form-control-lg"
                                                value={this.state.taskId}
                                                onChange={this.handleChangeTask}
                                                disabled={this.state.tasks.length === 0}>
                                            {options}
                                        </select>
                                    );
                                })()}
                            </div>
                        </div>

                    )
                    : (
                        <div className="alert alert-warning" role="alert">
                            Всі завдання курсу вже назначені для групи.
                            <a href={`/courses/${this.props.courseId}/tasks`} className="alert-link"
                               onClick={this.props.redirectTasksLink}>
                                Створити нове завдання.
                            </a>
                        </div>
                    )
                }

                <div className="form-group">
                    <label htmlFor="selectDateTime">
                            <span className="" id="selectDateTime" data-tip
                                  data-for='selectDateTimeFAQ'>
                                    <i className="fa fa-question-circle-o fa-lg"
                                       aria-hidden="true"/>
                            </span>
                        &nbsp;Дата перевірки:
                    </label>
                    <ReactTooltip id='selectDateTimeFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        Час, коли буде здійснено перевірку на плагіат
                    </ReactTooltip>
                    <Datetime
                        onChange={this.handleChangeExpireDate}
                        renderInput={renderDateTimeInput}
                        dateFormat="(dddd) DD.MM.YYYY /"
                        timeFormat="HH:mm"
                        isValidDate={validDateTime}
                        defaultValue={this.state.expiryDate}
                    />
                </div>


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


                <div className="form-group">
                    <label htmlFor="selectTypeComparing">
                            <span className="" id="selectTypeComparing" data-tip
                                  data-for='selectTypeComparingFAQ'>
                                    <i className="fa fa-question-circle-o fa-lg"
                                       aria-hidden="true"/>
                            </span>
                        &nbsp;Тип порівняння:
                    </label>
                    <ReactTooltip id='selectTypeComparingFAQ' place="left" type='info'
                                  multiline={true}
                                  effect="solid">
                        <p>Множина студентів, серед яких буде проводитися порівняння:</p>
                        <ul>
                            <li>Група - серед студентів даної групи</li>
                            <li>Курс - серед усіх студентів даного курса, які виконали завдання</li>
                        </ul>
                    </ReactTooltip>
                    <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="selectTypeComparing">
                                <i className="fa fa-users fa-lg" aria-hidden="true"/>
                            </span>
                        </div>
                        {(() => {
                            let options = this.state.detectionTypes.map(
                                dt =>
                                    <option value={dt} key={dt}>
                                        {dt === 'COURSE' ? 'Курс' : 'Група'}
                                    </option>
                            );
                            return (
                                <select className="form-control form-control-lg"
                                        id="selectTypeComparing"
                                        value={this.state.detectionType}
                                        onChange={this.handleChangeTypeDetection}>
                                    {options}
                                </select>
                            );
                        })()}
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
