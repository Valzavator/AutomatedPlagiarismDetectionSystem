import React from "react";
import {Link} from "react-router-dom";

class Header extends React.Component {

    render() {
        return (
            <div>
                <Link to={"/"} className="nav-link">
                    Home
                </Link>
                <Link to={"/signin"} className="nav-link">
                    Home
                </Link>
                <Link to={"/signup"} className="nav-link">
                    Home
                </Link>
                HEADER
            </div>
        )
    }
}

export default Header;