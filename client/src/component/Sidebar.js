import React from "react";
import {Link, NavLink} from "react-router-dom";

class Sidebar extends React.Component {
    render() {
        return (
            <div className="bg-dark" id="sidebar-wrapper">
                <div className="sidebar-sticky">
                    <div className="sidebar-heading">Start Bootstrap</div>
                    <div className="dropdown-divider"></div>
                    <div className="list-group list-group-flush">
                        <a href="#" className="list-group-item list-group-item-action">Dashboard</a>
                        <a href="#" className="list-group-item list-group-item-action ">Shortcuts</a>
                        <a href="#" className="list-group-item list-group-item-action ">Overview</a>
                        <a href="#" className="list-group-item list-group-item-action ">Events</a>
                        <a href="#" className="list-group-item list-group-item-action ">Profile</a>
                        <a href="#" className="list-group-item list-group-item-action ">Status</a>
                    </div>
                </div>
            </div>
        )
    }
}

export default Sidebar;
