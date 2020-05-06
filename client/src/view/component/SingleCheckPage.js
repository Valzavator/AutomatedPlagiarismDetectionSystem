import React from "react";
import ReactTooltip from "react-tooltip";
import Load from "./Load";

class SingleCheckPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            minimumSimilarityPercent: 1,
            isSettings: true,
            isLoading: false
        }

        this.handleChangeRange = this.handleChangeRange.bind(this);
    }

    handleChangeRange(e) {
        this.setState({
            minimumSimilarityPercent: e.target.value
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
                            <form>
                                <div className="form-group">
                                    <label htmlFor="selectLanguage">
                                                <span className="" id="selectLanguage" data-tip
                                                      data-for='selectLanguageFAQ'>
                                                        <i className="fa fa-question-circle-o fa-lg"
                                                           aria-hidden="true"/>
                                                </span>&nbsp;
                                        Мова програмування:
                                    </label>
                                    <ReactTooltip id='selectLanguageFAQ' place="left" type='info'
                                                  multiline={true}
                                                  effect="solid">
                                        selectLanguageFAQ
                                    </ReactTooltip>
                                    <div className="input-group">
                                        <div className="input-group-prepend">
                                                    <span className="input-group-text" id="selectLanguage">
                                                        <i className="fa fa-language fa-lg" aria-hidden="true"/>
                                                    </span>
                                        </div>
                                        <select className="form-control form-control-lg" id="selectLanguage">
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                        </select>
                                    </div>
                                </div>

                                <div className="form-group mt-4">
                                    <label htmlFor="selectSensitivity">
                                                <span className="" id="selectSensitivity" data-tip
                                                      data-for='selectSensitivityFAQ'>
                                                        <i className="fa fa-question-circle-o fa-lg"
                                                           aria-hidden="true"/>
                                                </span>&nbsp;
                                        Чутливість порівняння:
                                    </label>
                                    <ReactTooltip id='selectSensitivityFAQ' place="left" type='info'
                                                  multiline={true}
                                                  effect="solid">
                                        selectSensitivityFAQ
                                    </ReactTooltip>
                                    <div className="input-group">
                                        <div className="input-group-prepend">
                                                        <span className="input-group-text" id="selectSensitivity">
                                                            <i className="fa fa-cogs fa-lg" aria-hidden="true"/>
                                                        </span>
                                        </div>
                                        <input type="number" min="1" max="2147483647"
                                               className="form-control form-control-lg" id="selectSensitivity"/>
                                    </div>
                                </div>

                                <div className="form-group mt-4">
                                    <label htmlFor="selectPercent">
                                                <span className="" id="selectPercent" data-tip
                                                      data-for='selectPercentFAQ'>
                                                        <i className="fa fa-question-circle-o fa-lg"
                                                           aria-hidden="true"/>
                                                </span>&nbsp;
                                        Мінімальний відсоток співпадіння:
                                    </label>
                                    <ReactTooltip id='selectPercentFAQ' place="left" type='info' effect="solid"
                                                  multiline={true}>
                                        selectPercentFAQ
                                    </ReactTooltip>
                                    <div className="input-group">
                                        <input type="range" className="custom-range" id="selectPercent"
                                               data-tip
                                               data-for='selectPercent'
                                               defaultValue="1"
                                               step="1"
                                               min="1"
                                               max="100"
                                               onChange={this.handleChangeRange}/>
                                        <ReactTooltip id='selectPercent' type='info' effect="float"
                                                      multiline={true}>
                                            {this.state.minimumSimilarityPercent}
                                        </ReactTooltip>
                                    </div>
                                </div>

                                <div className="form-group mt-4 was-validated">
                                    <label htmlFor="selectTemplateFolder">
                                                <span className="" id="selectTemplateFolder" data-tip
                                                      data-for='selectTemplateFolderFAQ'>
                                                        <i className="fa fa-question-circle-o fa-lg"
                                                           aria-hidden="true"/>
                                                </span>&nbsp;
                                        Шаблонний код:
                                    </label>
                                    <ReactTooltip id='selectTemplateFolderFAQ' place="left" type='info'
                                                  effect="solid" multiline={true}>
                                        Збережіть програмні проекти в архів формату <strong>.zip</strong> та
                                        завантажте
                                        його до системи
                                    </ReactTooltip>
                                    <div className="custom-file">
                                        <input type="file" className="custom-file-input form-control-lg"
                                               accept=".zip"
                                               id="selectTemplateFolder" required/>
                                        <label className="custom-file-label form-control-lg"
                                               htmlFor="selectTemplateFolder">
                                            Обрати файл...
                                        </label>
                                        <div className="invalid-feedback">Example invalid custom file
                                            feedback
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <hr/>

                    <div className="row justify-content-center">
                        <div className="col-md-7 col-sm-12">
                            <form>
                                <div className="form-group mt-4 was-validated">
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
                                        Збережіть програмні проекти в архів формату <strong>.zip</strong> та
                                        завантажте його до системи
                                    </ReactTooltip>
                                    <div className="custom-file">
                                        <input type="file" className="custom-file-input form-control-lg"
                                               accept=".zip"
                                               id="selectArchive"
                                               required/>
                                        <label className="custom-file-label form-control-lg"
                                               htmlFor="selectArchive">
                                            Обрати файл...
                                        </label>
                                        <div className="invalid-feedback">Example invalid custom file
                                            feedback
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div className="progress my-3">
                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                             aria-valuemax="100"/>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-12 col-sm-12 text-center">
                            <button type="button" className="btn btn-primary btn-lg">
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
                        <div className="col-md-8 col-sm-12">
                            <div className="card bg-secondary text-white border-secondary">
                                <div className="card-header">Header</div>
                                <div className="card-body">
                                    This is some text within a card body.
                                </div>
                            </div>
                        </div>
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
                            <button type="button" className="btn btn-primary btn-lg mx-3">
                                <i className="fa fa-chevron-circle-left fa-lg" aria-hidden="true"/>&nbsp;&nbsp;
                                До налаштувань
                            </button>
                            <button type="button" className="btn btn-primary btn-lg mx-3">
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

export default SingleCheckPage;