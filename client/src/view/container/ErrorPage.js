import React from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as errorActions from '../../store/action/errorActions';
import * as authActions from '../../store/action/authActions';
import {Link} from 'react-router-dom';

class ErrorPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    };

    static getDerivedStateFromProps(props, state) {
        if (props.error.status < 300) {
            props.history.goBack();
            return null;
        }
        return state;
    };

    componentDidMount() {
        if (this.props.error.status === 401) {
            this.props.actions.auth.logoutUser();
            this.props.history.push("/signin")
        }
    }

    componentWillUnmount() {
        this.props.actions.error.resetError();
    }

    render() {
        return (
            <div className="row h-100 justify-content-md-center align-items-center">
                <div className="text-center">
                    <h1 className="default-status-error">{this.props.error.status}</h1>
                    <h1 className="default-status-error">{this.props.error.error}</h1>
                    <p className="default-text-muted-error">{this.props.error.message}</p>
                    <Link to={"/"} className="btn btn-lg btn-primary">
                        До головної сторінки
                    </Link>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        error: state.error
    };
}

function mapDispatchToProps(dispatch) {
    return {
        actions: {
            error: bindActionCreators(errorActions, dispatch),
            auth: bindActionCreators(authActions, dispatch)
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ErrorPage);


