import React from 'react';
import {connect} from 'react-redux';

export default function (ComposedComponent, isMustAuthorized = true) {
    class Authentication extends React.Component {
        constructor(props) {
            super(props);
            this.state = {};
        };

        static getDerivedStateFromProps(props, state) {
            if (!props.isAuthorized && isMustAuthorized) {
                props.history.push('/signin');
                return null;
            } else if (props.isAuthorized && !isMustAuthorized) {
                props.history.push('/');
                return null;
            }
            return state;
        };

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