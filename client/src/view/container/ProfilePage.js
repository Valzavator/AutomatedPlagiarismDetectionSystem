import React from "react";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import {notify} from "reapop";
import {connect} from "react-redux";
import {getUserProfileInfo, getUserProfileVcsInfo} from "../../api/user";
import {VCS_SERVICE_BITBUCKET, VCS_SERVICE_GITHUB} from "../../util/constants";
import {deleteVcs} from "../../api/vcs";

class ProfilePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            userProfile: {
                firstName: 'NOT_LOADED',
                lastName: 'NOT_LOADED',
                email: 'NOT_LOADED',
                coursesCount: -1,
                groupsCount: -1,
                tasksCount: -1,
                studentsCount: -1,
                studentsRepositoriesCount: -1
            },
            vcs: {
                isAuthorizedGitHub: false,
                gitHubAuthorizationLink: '#',
                isAuthorizedBitbucket: false,
                bitbucketAuthorizationLink: '#',
            },
            updateVcsInfo: false
        }
        this.handleDeleteVcsBtnClick = this.handleDeleteVcsBtnClick.bind(this);
        this.handleAuthorizeVcsBtnClick = this.handleAuthorizeVcsBtnClick.bind(this);
        this.handleDeleteVcsModalBtnClick = this.handleDeleteVcsModalBtnClick.bind(this);
    }

    componentDidMount() {
        this.setState({}, () => this.loadUserProfileInfo());
        this.timerID = setInterval(
            () => this.loadUserProfileVcsInfo(),
            1000
        );
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.vcs.isAuthorizedGitHub !== prevState.vcs.isAuthorizedGitHub ||
            this.state.vcs.isAuthorizedBitbucket !== prevState.vcs.isAuthorizedBitbucket) {
            this.setState({
                updateVcsInfo: false
            });
        }
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    async loadUserProfileVcsInfo() {
        if (this.state.updateVcsInfo) {
            let response = await getUserProfileVcsInfo();
            await this.setState({
                vcs: response.data
            });
        }
    }

    async loadUserProfileInfo() {
        try {
            let response = await getUserProfileInfo();
            this.setState({
                userProfile: response.data,
                vcs: response.data.userProfileVcs
            });
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handleAuthorizeVcsBtnClick(e) {
        e.preventDefault();
        window.open(e.target.authorizationLink.value, "_blank")
        this.setState({
            updateVcsInfo: true
        });
    }

    handleDeleteVcsBtnClick(e) {
        e.preventDefault();
        const vcsType = e.target.vcsType.value;
        this.setState({
            vcsToDelete: vcsType
        });
    }

    async handleDeleteVcsModalBtnClick(e) {
        e.preventDefault();
        try {
            const vcsType = this.state.vcsToDelete;
            await deleteVcs(vcsType);
            if (vcsType === VCS_SERVICE_GITHUB) {
                this.setState({
                    vcs: {...this.state.vcs, isAuthorizedGitHub: false}
                });
            } else if (vcsType === VCS_SERVICE_BITBUCKET) {
                this.setState({
                    vcs: {...this.state.vcs, isAuthorizedBitbucket: false}
                });
            }
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    render() {
        const renderVcsStatus = (isAuthorized) => {
            return isAuthorized
                ? (
                    <span className="badge badge-success">
                        АВТОРИЗОВАНО
                    </span>
                )
                : (
                    <span className="badge badge-danger">
                        НЕ АВТОРИЗОВАНО
                    </span>
                )
        };

        const renderVcsControlBtn = (isAuthorized, authorizationLink, serviceType) => {
            return isAuthorized
                ? (
                    <form onSubmit={this.handleDeleteVcsBtnClick}>
                        <input name="vcsType" value={serviceType} hidden readOnly/>
                        <button type="submit" className="btn btn-link"
                                data-toggle="modal"
                                data-target="#deleteVcsModal"
                                id="deleteVcsBtn">
                            <i className="fa fa-times fa-2x"
                               aria-hidden="true"/>
                        </button>
                    </form>
                )
                : (
                    <form onSubmit={this.handleAuthorizeVcsBtnClick}>
                        <input name="authorizationLink" value={authorizationLink} hidden readOnly/>
                        <button className="btn btn-link">
                            <i className="fa fa-plus fa-2x"
                               aria-hidden="true"/>
                        </button>
                    </form>
                )
        }

        return (
            <div className="row justify-content-center align-items-center mt-5">
                <div className="container">
                    <div className="jumbotron">
                        <div className="row my-2">
                            <div className="col-lg-3 order-lg-1 text-center">
                                <img src={require('../../images/male.png')}
                                     className="mx-auto img-fluid img-circle d-block"
                                     alt="avatar"/>
                                {/*<h6 className="mt-2">Upload a different photo</h6>*/}
                                {/*<label className="custom-file">*/}
                                {/*    <input type="file" id="file" className="custom-file-input"/>*/}
                                {/*    <span className="custom-file-control">Choose file</span>*/}
                                {/*</label>*/}
                            </div>
                            <div className="col-lg-9 order-lg-2">
                                <ul className="nav nav-tabs justify-content-center">
                                    <li className="nav-item">
                                        <button data-target="#profile" data-toggle="tab"
                                                className="nav-link btn btn-link active">
                                            Головна
                                        </button>
                                    </li>
                                    <li className="nav-item">
                                        <button data-target="#vcs" data-toggle="tab"
                                                className="nav-link btn btn-link">
                                            Налаштування VCS
                                        </button>
                                    </li>
                                    <li className="nav-item">
                                        <button data-target="#edit" data-toggle="tab"
                                                className="nav-link btn btn-link">
                                            Редагувати
                                        </button>
                                    </li>
                                </ul>
                                <div className="tab-content py-4">
                                    <div className="tab-pane active" id="profile">
                                        <h5 className="mb-3 text-center">
                                            <span className="fa fa-user-circle fa-lg"/>
                                            &nbsp;&nbsp;Профіль користувача
                                        </h5>
                                        <hr/>
                                        <div className="row">
                                            <div className="col-md-12">
                                                <h6><strong>Ім'я користувача в системі:</strong></h6>
                                                <p>
                                                    {this.state.userProfile.firstName + " " + this.state.userProfile.lastName}
                                                </p>
                                                <h6><strong>Email:</strong></h6>
                                                <p>
                                                    {this.state.userProfile.email}
                                                </p>
                                            </div>

                                            <div className="col-md-12">
                                                <h5 className="mt-2 text-center">
                                                    <span className="fa fa-bar-chart fa-lg"/>
                                                    &nbsp;Статисика профілю
                                                </h5>
                                                <table
                                                    className="table table-hover table-striped table-dark table-bordered">
                                                    <tbody>
                                                    <tr>
                                                        <td className="w-25">Створено курсів:</td>
                                                        <td>
                                                            <strong>{this.state.userProfile.coursesCount}</strong>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td className="w-25">Створено груп:</td>
                                                        <td>
                                                            <strong>{this.state.userProfile.groupsCount}</strong>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td className="w-25">Кількість завдань:</td>
                                                        <td>
                                                            <strong>{this.state.userProfile.tasksCount}</strong>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td className="w-25">Студентів:</td>
                                                        <td>
                                                            <strong>{this.state.userProfile.studentsCount}</strong>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td className="w-25">Репозиторіїв студентів:</td>
                                                        <td>
                                                            <strong>{this.state.userProfile.studentsRepositoriesCount}</strong>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="tab-pane" id="vcs">
                                        {/*<div className="alert alert-info alert-dismissable">*/}
                                        {/*    <a className="panel-close close" data-dismiss="alert">×</a> This is*/}
                                        {/*    an <strong>.alert</strong>. Use this to show important messages to the user.*/}
                                        {/*</div>*/}
                                        <h5 className="mt-2 text-center">
                                            <span className="fa fa-sliders fa-lg"/>
                                            &nbsp;&nbsp;Cервіси
                                        </h5>
                                        <table className="table table-striped text-center table-responsive-sm">
                                            <thead>
                                            <tr>
                                                <th scope="col">Назва</th>
                                                <th scope="col"/>
                                                <th scope="col">Статус</th>
                                                <th scope="col"/>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td className="align-middle" style={{width: '200px'}}>
                                                        <span className="h5">
                                                            <i className="fa fa-github fa-lg" aria-hidden="true"/>
                                                            &nbsp;&nbsp;GitHub
                                                        </span>
                                                </td>
                                                <td className="align-middle" style={{width: '20px'}}>
                                                    <a href="https://github.com/" target="noopener">
                                                        <i className="fa fa-external-link fa-lg" aria-hidden="true"/>
                                                    </a>
                                                </td>
                                                <td className="align-middle">
                                                    {renderVcsStatus(this.state.vcs.isAuthorizedGitHub)}
                                                </td>
                                                <td className="align-middle text-right" style={{width: '20px'}}>
                                                    {renderVcsControlBtn(
                                                        this.state.vcs.isAuthorizedGitHub,
                                                        this.state.vcs.gitHubAuthorizationLink,
                                                        VCS_SERVICE_GITHUB
                                                    )}
                                                </td>
                                            </tr>

                                            <tr>
                                                <td className="align-middle" style={{width: '200px'}}>
                                                        <span className="h5">
                                                            <i className="fa fa-bitbucket fa-lg" aria-hidden="true"/>
                                                            &nbsp;&nbsp;Bitbucket
                                                        </span>
                                                </td>
                                                <td className="align-middle" style={{width: '20px'}}>
                                                    <a href="https://bitbucket.org/" target="noopener">
                                                        <i className="fa fa-external-link fa-lg" aria-hidden="true"/>
                                                    </a>
                                                </td>
                                                <td className="align-middle">
                                                    {renderVcsStatus(this.state.vcs.isAuthorizedBitbucket)}
                                                </td>
                                                <td className="align-middle text-right" style={{width: '20px'}}>
                                                    {renderVcsControlBtn(
                                                        this.state.vcs.isAuthorizedBitbucket,
                                                        this.state.vcs.bitbucketAuthorizationLink,
                                                        VCS_SERVICE_BITBUCKET
                                                    )}
                                                </td>
                                            </tr>

                                            <tr>
                                                <td className="align-middle" style={{width: '200px'}}>
                                                        <span className="h5">
                                                            <i className="fa fa-gitlab fa-lg" aria-hidden="true"/>
                                                            &nbsp;&nbsp;GitLab
                                                        </span>
                                                </td>
                                                <td className="align-middle" style={{width: '20px'}}>
                                                    <a href="https://gitlab.com/" target="noopener">
                                                        <i className="fa fa-external-link fa-lg" aria-hidden="true"/>
                                                    </a>
                                                </td>
                                                <td className="align-middle">
                                                        <span className="badge badge-secondary">
                                                            НЕ РЕАЛІЗОВАНО
                                                        </span>
                                                </td>
                                                <td className="align-middle text-right" style={{width: '20px'}}>
                                                    <form onSubmit={this.handleClick}>
                                                        <input name="delete" value={"???"} hidden readOnly/>
                                                        <button type="submit" className="btn btn-link" disabled>
                                                            <i className="fa fa-plus fa-2x"
                                                               aria-hidden="true"/>
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>

                                        <div className="modal fade" id="deleteVcsModal" tabIndex="-1" role="dialog"
                                             aria-labelledby="deleteVcsModalTitle" aria-hidden="true">
                                            <div className="modal-dialog modal-dialog-centered" role="document">
                                                <div className="modal-content">
                                                    <div className="modal-header">
                                                        <h5 className="modal-title" id="deleteVcsModalTitle">
                                                            Видалення авторизації сервісу {this.state.vcsToDelete}
                                                        </h5>
                                                        <button type="button" className="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div className="modal-body">
                                                        Ви впевнені?
                                                    </div>
                                                    <div className="modal-footer">
                                                        <button type="button" className="btn btn-secondary"
                                                                data-dismiss="modal">Повернутися
                                                        </button>
                                                        <button type="button" className="btn btn-danger"
                                                                onClick={this.handleDeleteVcsModalBtnClick}
                                                                data-dismiss="modal">
                                                            Видалити
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div className="tab-pane" id="edit">
                                        <form role="form">
                                            <div className="form-group row">
                                                <label className="col-lg-3 col-form-label form-control-label">
                                                    Ім'я:
                                                </label>
                                                <div className="input-group col-lg-9">
                                                    <div className="input-group-prepend">
                                                        <span className="input-group-text" id="inputGroupPrepend">
                                                            <i className="fa fa-user fa-lg" aria-hidden="true"/>
                                                        </span>
                                                    </div>
                                                    <input type="text"
                                                           id="firstName"
                                                           name="firstName"
                                                           value={this.state.userProfile.firstName}
                                                        // onChange={this.handleChange}
                                                           className="form-control"
                                                        // className={renderFieldStyle('firstName')}
                                                           placeholder="Ім'я"
                                                           aria-describedby="inputGroupPrepend"
                                                           maxLength="32" readOnly/>
                                                    {/*{renderErrorMessage('firstName')}*/}
                                                </div>


                                            </div>
                                            <div className="form-group row">
                                                <label className="col-lg-3 col-form-label form-control-label">
                                                    Прізвище:
                                                </label>
                                                <div className="input-group col-lg-9">
                                                    <div className="input-group-prepend">
                                                        <span className="input-group-text" id="inputGroupPrepend2">
                                                            <i className="fa fa-user fa-lg" aria-hidden="true"/>
                                                        </span>
                                                    </div>
                                                    <input type="text"
                                                           id="lastName"
                                                           name="lastName"
                                                           value={this.state.userProfile.lastName}
                                                        // onChange={this.handleChange}
                                                        // className={renderFieldStyle('lastName')}
                                                           className="form-control"
                                                           placeholder="Прізвище"
                                                           maxLength="32"
                                                           aria-describedby="inputGroupPrepend" readOnly/>
                                                    {/*{renderErrorMessage('lastName')}*/}
                                                </div>
                                            </div>
                                            <div className="form-group row">
                                                <label className="col-lg-3 col-form-label form-control-label">
                                                    Email:
                                                </label>
                                                <div className="input-group col-lg-9">
                                                    <div className="input-group-prepend">
                                                        <span className="input-group-text" id="inputGroupPrepend">
                                                            <i className="fa fa-envelope fa-lg" aria-hidden="true"/>
                                                        </span>
                                                    </div>
                                                    <input type="text"
                                                           id="email"
                                                           name="email"
                                                           value={this.state.userProfile.email}
                                                        // onChange={this.handleChange}
                                                           className="form-control"
                                                        // className={renderFieldStyle('email')}
                                                           aria-describedby="emailHelp"
                                                           placeholder="Email"
                                                           maxLength="255" readOnly/>
                                                    {/*{renderErrorMessage('email')}*/}
                                                </div>
                                            </div>
                                            <div className="form-group row">
                                                <label className="col-lg-3 col-form-label form-control-label">
                                                    Пароль:
                                                </label>
                                                <div className="input-group col-lg-9">
                                                    <div className="input-group-prepend">
                                                        <span className="input-group-text" id="inputGroupPrepend3">
                                                            <i className="fa fa-lock fa-lg" aria-hidden="true"/>
                                                        </span>
                                                    </div>
                                                    <input type="password"
                                                           id="password"
                                                           name="password"
                                                        // value={this.state.password}
                                                        // onChange={this.handleChange}
                                                        // className={renderFieldStyle('password')}
                                                           className="form-control"
                                                           placeholder="Пароль"
                                                           maxLength="255" readOnly/>
                                                    {/*{renderErrorMessage('password')}*/}
                                                </div>
                                            </div>
                                            <div className="form-group row">
                                                <label className="col-lg-3 col-form-label form-control-label">
                                                    Підтвердіть пароль:
                                                </label>
                                                <div className="input-group col-lg-9">
                                                    <div className="input-group-prepend">
                                                        <span className="input-group-text" id="inputGroupPrepend4">
                                                            <i className="fa fa-lock fa-lg" aria-hidden="true"/>
                                                        </span>
                                                    </div>
                                                    <input type="password"
                                                           id="confirm-password"
                                                           name="confirm-password"
                                                        // value={this.state.password}
                                                        // onChange={this.handleChange}
                                                        // className={renderFieldStyle('password')}
                                                           className="form-control"
                                                           placeholder="Підтвердіть пароль"
                                                           maxLength="255" readOnly/>
                                                    {/*{renderErrorMessage('password')}*/}
                                                </div>
                                            </div>
                                            <div className="form-group row">
                                                <label className="col-lg-3 col-form-label form-control-label"/>
                                                <div className="col-lg-9">
                                                    <button type="submit" className="btn btn-primary mr-1" disabled>
                                                        Зберегти зміни
                                                    </button>
                                                    <button type="reset" className="btn btn-secondary ml-1">
                                                        Відмінити
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    {/*<div className="col-lg-4 order-lg-1 text-center">*/}
                    {/*    <img src="//placehold.it/150" className="mx-auto img-fluid img-circle d-block" alt="avatar"/>*/}
                    {/*    <h6 className="mt-2">Upload a different photo</h6>*/}
                    {/*    <label className="custom-file">*/}
                    {/*        <input type="file" id="file" className="custom-file-input"/>*/}
                    {/*        <span className="customRecent Activity-file-control">Choose file</span>*/}
                    {/*    </label>*/}
                    {/*</div>*/}
                </div>
            </div>
        )
    }
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        notify: bindActionCreators(notify, dispatch)
    };
}

export default connect(null, mapDispatchToProps)(ProfilePage);