import React from "react";
import {NavLink, withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import * as sidebarActions from '../../store/action/sidebarActions';
import * as authActions from '../../store/action/authActions';
import {LinkContainer} from 'react-router-bootstrap';

class Header extends React.Component {
    constructor(props) {
        super(props);

        this.handleLogout = this.handleLogout.bind(this);
    }

    handleLogout() {
        this.props.actions.auth.logoutUser();
        this.props.history.push('/');
    };

    render() {

        const renderSidebarButton = () => {
            if (this.props.sidebarButton) {
                return (
                    <ul className="navbar-nav">
                        <li className="nav-item" onClick={this.props.actions.sidebar.sidebarChangeState}>
                            <button className="btn btn-link nav-link">
                                <i className="fa fa-bars fa-lg" aria-hidden="true">&nbsp;&nbsp;&nbsp;&nbsp;</i>
                            </button>
                        </li>
                    </ul>
                )
            }
        }

        const renderLinks = () => {
            if (this.props.isAuthorized) {
                return (
                    <ul className="navbar-nav ml-auto">
                        {singleCheckLink}

                        <li className="nav-item">
                            <LinkContainer to="/courses">
                                <button className="nav-link btn btn-link">
                                    <i className="fa fa-list fa-lg" aria-hidden="true">&nbsp;</i>
                                    Курси
                                </button>
                            </LinkContainer>
                        </li>

                        <li className="nav-item">
                            <LinkContainer to="/courses">
                                <button className="nav-link btn btn-link">
                                    <i className="fa fa-users fa-lg" aria-hidden="true">&nbsp;</i>
                                    Студенти
                                </button>
                            </LinkContainer>
                        </li>

                        <li className="nav-item dropdown">
                            <button className="btn btn-link nav-link dropdown-toggle"
                                    id="profileDropdownMenuLink"
                                    data-toggle="dropdown"
                                    aria-haspopup="true"
                                    aria-expanded="false">
                                <i className="fa fa-user-circle-o fa-lg" aria-hidden="true">&nbsp;</i>
                                {this.props.userInfo ? this.props.userInfo.username : ''}
                            </button>
                            <div className="dropdown-menu dropdown-menu-right"
                                 aria-labelledby="profileDropdownMenuLink">
                                <LinkContainer to="/profile">
                                    <button className="dropdown-item">
                                        Профіль
                                    </button>
                                </LinkContainer>
                                <div className="dropdown-divider"/>
                                <button className="dropdown-item" onClick={this.handleLogout}>
                                    Вийти
                                </button>
                            </div>
                        </li>

                    </ul>
                )
            } else {
                return (
                    <ul className="navbar-nav ml-auto">
                        {singleCheckLink}

                        {/*<li className="nav-item">*/}
                        {/*    <LinkContainer to="/courses">*/}
                        {/*        <button className="nav-link btn btn-link">*/}
                        {/*            <i className="fa fa-list fa-lg" aria-hidden="true">&nbsp;</i>*/}
                        {/*            Курси*/}
                        {/*        </button>*/}
                        {/*    </LinkContainer>*/}
                        {/*</li>*/}

                        <li className="nav-item">
                            <LinkContainer to="/signup">
                                <button className="nav-link btn btn-link">
                                    <i className="fa fa-user-plus fa-lg" aria-hidden="true">&nbsp;</i>
                                    Зареєструватися
                                </button>
                            </LinkContainer>
                        </li>

                        <li className="nav-item">
                            <LinkContainer to="/signin">
                                <button className="nav-link btn btn-link">
                                    <i className="fa fa-sign-in fa-lg" aria-hidden="true">&nbsp;</i>
                                    Увійти
                                </button>
                            </LinkContainer>
                        </li>

                    </ul>
                )
            }
        }

        const singleCheckLink = (
            <li className="nav-item">
                <LinkContainer to="/single-check">
                    <button className="nav-link btn btn-link">
                        <i className="fa fa-check-circle-o fa-lg" aria-hidden="true">&nbsp;</i>
                        Разова перевірка
                    </button>
                </LinkContainer>
            </li>
        )

        return (
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary sticky-top">
                {renderSidebarButton()}
                <NavLink to={"/"} className="navbar-brand" style={{marginBottom: '-10px'}}>
                    AutoPlag&nbsp;&nbsp;&nbsp;
                    <img src={require('../../images/navbar-brand.png')} alt="Logo" style={{width:'40px'}}/>
                </NavLink>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01"
                        aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"/>
                </button>

                <div className="collapse navbar-collapse" id="navbarColor01">
                    {renderLinks()}
                </div>
            </nav>
        )
    }
}

function mapDispatchToProps(dispatch) {
    return {
        actions: {
            auth: bindActionCreators(authActions, dispatch),
            sidebar: bindActionCreators(sidebarActions, dispatch),
        }
    };
}

function mapStateToProps(state) {
    return {
        isAuthorized: state.auth.isAuthorized,
        userInfo: state.auth.userInfo,
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Header));

