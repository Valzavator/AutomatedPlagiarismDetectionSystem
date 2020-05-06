import React from "react";

class HomePage extends React.Component {
    render() {
        return (
            <div className="row justify-content-center align-content-center h-100">
                <div className="col-12 text-center">
                    <h1 className="display-1 text-break" style={{fontSize: '5vw'}}>
                        Automate plagiarism detection system
                    </h1>
                </div>
                <div className="col-12 text-center">
                    <img src={require('../../images/logo.png')} alt="Logo" className="img-fluid"
                        style={{width: '60vh'}}/>
                </div>
            </div>
        )
    }
}

export default HomePage;