import React from "react";

class Footer extends React.Component {
    render() {
        return (
            <footer className="footer py-3 bg-dark">
                <div className="container text-center">
                    <div className="row justify-content-center">
                        <a href="https://github.com/Valzavator" target="noopener">
                            <span className="fa fa-github fa-2x social-media">&nbsp;&nbsp;</span>
                        </a>
                        <span className="text-muted"> © 2020 Maksym Svynarchuk All Rights Reserved</span>
                    </div>
                </div>
            </footer>
        )
    }
}

export default Footer;