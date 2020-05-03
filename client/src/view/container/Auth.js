import React from 'react';
import {connect} from 'react-redux';

export default function (ComposedComponent, isMustAuthorized) {
    isMustAuthorized = isMustAuthorized === undefined ? true : isMustAuthorized;

    class Authentication extends React.Component {
        componentDidMount() {
            if (!this.props.isAuthorized && isMustAuthorized)
                this.props.history.push('/signin');
            else if (this.props.isAuthorized && !isMustAuthorized)
                this.props.history.push('/');
        }

        render() {
            return <ComposedComponent {...this.props} />
        }
    }

    function mapStateToProps(state) {
        return {
            isAuthorized: state.auth.isAuthorized
        };
    }

    return connect(mapStateToProps)(Authentication);
}