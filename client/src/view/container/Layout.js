import React from 'react';
import Header from './Header';
import Footer from '../component/Footer'
import Sidebar from "../component/Sidebar";
import {connect} from "react-redux";
import NotificationsSystem from 'reapop';
import theme from 'reapop-theme-bootstrap';
import {withRouter} from "react-router-dom";

class Layout extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    };

    static getDerivedStateFromProps(props, state) {
        if (props.errorStatus >= 300 &&
            props.location.pathname !== '/error') {
            props.history.push('/error');
            return null;
        }
        return state;
    };

    render() {
        return (
            <div className="wrapper-1">
                <div className="wrapper-2">
                    <NotificationsSystem theme={theme}/>
                    <Header/>
                    <main role="main"
                          className={this.props.isOpenSidebar && this.props.isAuthorized ? "d-flex " : "d-flex toggled"}
                          id="wrapper">
                        {this.props.isAuthorized ? <Sidebar/> : null}
                        <div id="page-content-wrapper">
                            <div className="container-fluid h-100">
                                {this.props.children}
                            </div>
                        </div>
                    </main>
                </div>
                <Footer/>
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        isOpenSidebar: state.sidebar.isOpenSidebar,
        isAuthorized: state.auth.isAuthorized,
        errorStatus: state.error.status
    };
}

export default withRouter(connect(mapStateToProps)(Layout));
