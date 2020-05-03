import React from "react";

class SignUpPage extends React.Component {
    render() {
        return (
            <div className="row h-100 justify-content-md-center align-items-center">
                <div className="card w-50 mx-auto">
                    <div className="card-header h2 text-center">
                        Форма реєстрації
                    </div>
                    <div className="card-body mx-auto w-100">
                        <form acceptCharset="UTF-8" role="form" method="post">
                            <div className="form-group">
                                <label htmlFor="email">
                                    Email
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                <span className="input-group-text" id="inputGroupPrepend0">
                                    <i className="fa fa-envelope fa-lg" aria-hidden="true"></i>
                                </span>
                                    </div>
                                    <input type="email"
                                           id="email"
                                           name="email"
                                        // value="userDTO.email"
                                           className="form-control form-control-lg is-invalid"
                                           placeholder="Email"
                                           maxLength="255"
                                           required/>
                                    <div className="invalid-feedback">
                                        Буль-ласка заповніть дане поле! | Некоректна Email адреса!
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="firstName">
                                    Ім'я
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                    <span className="input-group-text" id="inputGroupPrepend">
                                        <i className="fa fa-user fa-2x" aria-hidden="true"></i>
                                    </span>
                                    </div>
                                    <input type="text" id="firstName"
                                           name="firstName"
                                        // value="userDTO.firstName"
                                           className="form-control form-control-lg is-invalid"
                                           placeholder="Ім'я"
                                           aria-describedby="inputGroupPrepend"
                                           maxLength="255"
                                           required/>
                                    <div className="invalid-feedback">
                                        Буль-ласка заповніть дане поле!
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="lastName">
                                    Прізвище
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                        <span className="input-group-text" id="inputGroupPrepend2">
                                            <i className="fa fa-user fa-2x" aria-hidden="true"></i>
                                        </span>
                                    </div>
                                    <input type="text" id="lastName"
                                           name="lastName"
                                        // value="userDTO.lastName"
                                           className="form-control form-control-lg is-invalid"
                                           placeholder="Прізвище"
                                           maxLength="255"
                                           aria-describedby="inputGroupPrepend"
                                           required/>
                                    <div className="invalid-feedback">
                                        Буль-ласка заповніть дане поле!
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">
                                    Пароль
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                <span className="input-group-text" id="inputGroupPrepend3">
                                    <i className="fa fa-lock fa-2x" aria-hidden="true"></i>
                                </span>
                                    </div>
                                    <input type="password" id="password"
                                           name="password"
                                           className="form-control form-control-lg is-invalid"
                                           placeholder="Пароль"
                                           minLength="5"
                                           maxLength="255"
                                           required/>
                                    <div className="invalid-feedback">
                                        Пароль повинен містити не менше 6 символів!
                                    </div>
                                </div>
                            </div>

                            <div className="form-group text-center">
                                <button type="submit" className="btn btn-primary btn-lg mt-3">
                                    Зареєструватися&nbsp;&nbsp;
                                    <i className="fa fa-paper-plane-o fa-lg" aria-hidden="true"></i>
                                </button>
                            </div>

                        </form>
                    </div>
                    {/*<div className="card-footer text-muted">*/}
                    {/*    <div className="alert alert-danger alert-dismissible fade show" role="alert">*/}
                    {/*        error.registration*/}
                    {/*        <button type="button" className="close" data-dismiss="alert" aria-label="Close">*/}
                    {/*            <span aria-hidden="true">&times;</span>*/}
                    {/*        </button>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                </div>
            </div>
        )
    }
}

export default SignUpPage;