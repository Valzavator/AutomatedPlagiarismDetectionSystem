import React from "react";

class HomePage extends React.Component {
    render() {
        return (
            <div>
                <div className="row justify-content-center">
                    <img src={require('../../images/logo.png')} alt="Logo"/>
                </div>
                <div className="row justify-content-center align-items-center">
                    <div className="jumbotron jumbotron-fluid w-75">
                        <div className="container text-center">
                            <h1 className="display-4 text-break">
                                Automate plagiarism detection system
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

export default HomePage;