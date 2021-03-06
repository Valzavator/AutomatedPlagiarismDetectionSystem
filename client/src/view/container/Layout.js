import React from 'react';
import Header from './Header';
import Footer from '../component/Footer'
import Sidebar from "../component/Sidebar";
import {connect} from "react-redux";
import NotificationsSystem from 'reapop';
import theme from 'reapop-theme-bootstrap';
import {withRouter} from "react-router-dom";

const pagesPrefixWithSidebar = ['/courses', '/students']

class Layout extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            renderSidebar: false,
        };
    };

    componentDidMount() {
        const currLocation = this.props.location.pathname;

        if (this.props.isAuthorized && pagesPrefixWithSidebar.find(prefix => currLocation.startsWith(prefix))) {
            this.setState({renderSidebar: true});
        } else {
            this.setState({renderSidebar: false});
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        const currLocation = this.props.location.pathname;

        if (this.props.isAuthorized !== prevProps.isAuthorized ||
            currLocation !== prevProps.location.pathname) {
            if (this.props.isAuthorized &&
                pagesPrefixWithSidebar.find(prefix => currLocation.startsWith(prefix))) {
                this.setState({renderSidebar: true});
            } else {
                this.setState({renderSidebar: false});
            }
        }

        if (this.props.errorStatus >= 300 &&
            currLocation !== '/error') {
            this.props.history.push('/error');
        }
    }

    render() {
        return (
            <div className="wrapper-1">
                <div className="wrapper-2">
                    <NotificationsSystem theme={theme}/>
                    <Header sidebarButton={this.state.renderSidebar}/>
                    <main role="main"
                          className={this.props.isOpenSidebar ? "d-flex " : "d-flex toggled"}
                          id="wrapper">
                        {this.state.renderSidebar ? <Sidebar/> : null}
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
