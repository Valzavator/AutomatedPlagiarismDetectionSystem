import React from 'react';
import {Redirect} from 'react-router-dom';

class NotFoundRedirect extends React.Component {
    render() {
        return (
            <Redirect to="/404"/>
        )
    }
}

export default NotFoundRedirect;

