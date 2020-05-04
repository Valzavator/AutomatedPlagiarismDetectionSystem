import React from 'react';
import {Link} from 'react-router-dom';

const NotFoundPage = () => (
    <div className="row h-100 justify-content-md-center align-items-center">
        <div className="text-center">
            <h1 className="default-status-error">404</h1>
            <h1 className="default-status-error">Not found</h1>
            <Link to={"/"} className="btn btn-lg btn-primary">
                Повернутися
            </Link>
        </div>
    </div>
);

export default NotFoundPage;

