import React from "react";
import {NavLink} from "react-router-dom";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import * as sidebarActions from '../store/action/sidebarActions';


class Header extends React.Component {
    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary sticky-top">

                <ul className="navbar-nav">
                    <li className="nav-item" onClick={this.props.actions.sidebarChangeState}>
                        <NavLink to={"#"} className="nav-link">
                            <i className="fa fa-bars fa-lg" aria-hidden="true">&nbsp;&nbsp;&nbsp;&nbsp;</i>
                        </NavLink>
                    </li>
                </ul>


                {/*<button className="nav-link" id="menu-toggle" onClick={this.props.actions.sidebarChangeState}>Toggle*/}
                {/*    Menu*/}
                {/*</button>*/}

                <NavLink to={"/"} className="navbar-brand">
                    AutoPlag
                </NavLink>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01"
                        aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarColor01">
                    {/*<ul className="navbar-nav mr-auto">*/}
                    {/*    <li className="nav-item">*/}
                    {/*        <NavLink to={"/signin"} className="nav-link">*/}
                    {/*            <i className="fa fa-check-circle-o fa-lg" aria-hidden="true">&nbsp;</i>*/}
                    {/*            Разова перевірка*/}
                    {/*        </NavLink>*/}
                    {/*        /!*<a className="nav-link" href="/signin">Home <span className="sr-only">(current)</span></a>*!/*/}
                    {/*    </li>*/}
                    {/*</ul>*/}
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <NavLink to={"/1"} className="nav-link">
                                <i className="fa fa-check-circle-o fa-lg" aria-hidden="true">&nbsp;</i>
                                Разова перевірка
                            </NavLink>
                            {/*<a className="nav-link" href="/signin">Home <span className="sr-only">(current)</span></a>*/}
                        </li>

                        <li className="nav-item">
                            <NavLink to={"/signup"} className="nav-link">
                                <i className="fa fa-user-plus fa-lg" aria-hidden="true">&nbsp;</i>
                                Зареєструватися
                            </NavLink>
                            {/*<a className="nav-link" href="/signin">Home <span className="sr-only">(current)</span></a>*/}
                        </li>
                        <li className="nav-item">
                            <NavLink to={"/signin"} className="nav-link">
                                <i className="fa fa-sign-in fa-lg" aria-hidden="true">&nbsp;</i>
                                Увійти
                            </NavLink>
                        </li>

                        <li className="nav-item">
                            <NavLink to={"/courses"} className="nav-link">
                                <i className="fa fa-list fa-lg" aria-hidden="true">&nbsp;</i>
                                Курси
                            </NavLink>
                        </li>
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" href="#" id="profileDropdownMenuLink"
                               role="button"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i className="fa fa-user-circle-o fa-lg" aria-hidden="true">&nbsp;</i>
                                firstName
                            </a>
                            <div className="dropdown-menu dropdown-menu-right"
                                 aria-labelledby="profileDropdownMenuLink">
                                <NavLink to={"/profile"} className="dropdown-item">
                                    profile
                                </NavLink>
                                <div className="dropdown-divider"/>
                                <NavLink to={"/signout"} className="dropdown-item">
                                    signout
                                </NavLink>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        )
    }
}

function mapDispatchToProps(dispatch) {
    return {
        actions: bindActionCreators(sidebarActions, dispatch),
    };
}

export default connect(null, mapDispatchToProps)(Header);

