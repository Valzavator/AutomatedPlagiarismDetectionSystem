import React from "react";
import {connect} from "react-redux";
import {Link, NavLink} from "react-router-dom";
import {notify} from 'reapop';

class SignInPage extends React.Component {
    constructor(props) {
        super(props);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(e) {
        e.preventDefault();

        const {notify} = this.props;
        notify({
            title: 'Authorization error',
            message: 'you clicked on the button',
            status: 'error',
            position: 'br',
            dismissible: true,
            dismissAfter: 0
        });
    }

    render() {
        return (
            <div className="row h-100 justify-content-center align-items-center">
                <div className="card mx-auto">
                    <div className="card-header h2 text-center brand">
                        AutoPlag
                    </div>
                    <div className="card-body mx-auto w-100">
                        <form acceptCharset="UTF-8" role="form" method="post" onSubmit={this.onSubmit}>
                            <div className="form-group">
                                <label htmlFor="email">
                                    Email
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                        <span className="input-group-text" id="inputGroupPrepend">
                            <i className="fa fa-envelope fa-lg" aria-hidden="true"></i>
                        </span>
                                    </div>
                                    <input type="email" id="email"
                                           name="email"
                                        // value="123"
                                           className="form-control form-control-lg is-invalid"
                                           aria-describedby="emailHelp"
                                           placeholder="Email"
                                           maxLength="255"
                                           required/>
                                    <div className="invalid-feedback">
                                        Буль-ласка заповніть дане поле! | Некоректна Email адреса!
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">
                                    Пароль
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                <span className="input-group-text" id="inputGroupPrepend2">
                                    <i className="fa fa-lock fa-2x" aria-hidden="true"></i>
                                </span>
                                    </div>
                                    <input type="password" id="password"
                                           name="password"
                                           className="form-control form-control-lg is-invalid"
                                           placeholder="Пароль"
                                           minLength="6"
                                           maxLength="255"
                                           required/>
                                    <div className="invalid-feedback">
                                        Пароль повинен містити не менше 6 символів!
                                    </div>
                                </div>

                            </div>
                            <div className="form-group text-center">
                                <button type="submit" className="btn btn-primary btn-lg mt-3">
                                    Увійти&nbsp;&nbsp;
                                    <i className="fa fa-sign-in fa-lg" aria-hidden="true"></i>
                                </button>
                            </div>
                        </form>
                    </div>

                    {/*<div className="card-footer text-muted">*/}
                    {/*    <div className="alert alert-danger alert-dismissible fade show"*/}
                    {/*         role="alert">*/}
                    {/*        error.authentication*/}
                    {/*        <button type="button" className="close" data-dismiss="alert"*/}
                    {/*                aria-label="Close">*/}
                    {/*            <span aria-hidden="true">&times;</span>*/}
                    {/*        </button>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                </div>
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        activeUser: state.auth,
    };
}

export default connect(mapStateToProps, {notify})(SignInPage);


// export default SignInPage;