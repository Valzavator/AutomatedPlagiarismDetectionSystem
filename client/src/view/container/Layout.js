import React from 'react';
import Header from './Header';
import Footer from '../component/Footer'
import Sidebar from "../component/Sidebar";
import {connect} from "react-redux";
import NotificationsSystem from 'reapop';
import theme from 'reapop-theme-bootstrap';

class Layout extends React.Component {
    render() {
        return (
            <div className="wrapper-1">
                <div className="wrapper-2">
                    <NotificationsSystem theme={theme}/>
                    <Header/>
                    <main role="main"
                          className={this.props.isOpenSidebar && this.props.isAuthorized ? "d-flex " : "d-flex toggled"}
                          id="wrapper">
                        <Sidebar/>
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
        isAuthorized: state.auth.isAuthorized
    };
}

export default connect(mapStateToProps)(Layout);
