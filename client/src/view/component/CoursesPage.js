import React from "react";
import Load from "./Load";

class CoursesPage extends React.Component {
    render() {
        return (
            // <Load/>
            <div>
                <div className="row justify-content-center">
                    <img src={require('../../images/logo.png')} alt="Logo"/>
                </div>
                <div className="row justify-content-center align-items-center">
                    <div className="jumbotron jumbotron-fluid w-75">
                        <div className="container text-center">
                            <h1 className="display-4 text-break">
                                Courses
                            </h1>
                            <hr className="my-4"/>
                            <p className="h4 text-justify">
                                site.description
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default CoursesPage;