import React from "react";
import {connect} from "react-redux";
import {notify} from 'reapop';
import validator from 'email-validator';
import {bindActionCreators} from "redux";

import * as authActions from '../../store/action/authActions';
import * as errorActions from '../../store/action/errorActions';

class SignInPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            email: '',
            password: '',
            isSubmitted: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const fieldName = [e.target.name];
        this.setState({
            [fieldName]: e.target.value
        });
        this.setError(fieldName, '', false)
    }

    async handleSubmit(e) {
        e.preventDefault();

        let isValid = this.validateForm();

        if (isValid) {
            this.setState({isSubmitted: true});
            try {
                let isSuccessful = await this.props.actions.auth.signInUser({
                    email: this.state.email,
                    password: this.state.password
                });
                if (isSuccessful === true) {
                    // await this.props.actions.auth.getUser();
                } else if (isSuccessful === false) {
                    const {notify} = this.props;
                    notify({
                        title: 'Помилка авторизації',
                        message: 'Невірний Email або Пароль!',
                        status: 'error',
                        position: 'br',
                        dismissible: true,
                        dismissAfter: 2000
                    });
                }
            } catch (err) {
                this.props.actions.error.throwError(err);
            }
        }
    }

    validateForm() {
        const email = this.state.email;
        const password = this.state.password;

        let isValid = true;

        if (!validator.validate(email)) {
            this.setError('email',
                'Некоректна Email адреса!')
            isValid = false;
        }

        if (!password || password.length === 0) {
            this.setError('password',
                'Буль-ласка заповніть дане поле!')
            isValid = false;
        }

        return isValid;
    }

    setError(fieldName, errorMessage, isVisible = true) {
        this.setState({
            [fieldName + 'VisibleError']: isVisible,
            [fieldName + 'MessageError']: errorMessage
        });
    };

    render() {
        const renderErrorMessage = (fieldName) => {
            if (this.state[fieldName + 'VisibleError'] &&
                this.state[fieldName + 'MessageError']) {
                return (
                    <div className="invalid-feedback">
                        {this.state[fieldName + 'MessageError']}
                    </div>
                )
            }
        };

        const renderFieldStyle = (fieldName, baseStyle = 'form-control form-control-lg ') => {
            const isInvalid = this.state[fieldName + 'VisibleError'];
            return isInvalid
                ? baseStyle + 'is-invalid'
                : baseStyle
        };

        const cartStyle = {
            width: '500px',
        };

        return (
            <div className="row h-100 justify-content-center align-items-center">
                <div className="card mx-auto" style={cartStyle}>
                    <div className="card-header h2 text-center brand">
                        AutoPlag
                    </div>
                    <div className="card-body mx-auto w-100">
                        <form acceptCharset="UTF-8" method="post" onSubmit={this.handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="email">
                                    Email
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                        <span className="input-group-text" id="inputGroupPrepend">
                            <i className="fa fa-envelope fa-lg" aria-hidden="true"/>
                        </span>
                                    </div>
                                    <input type="text"
                                           id="email"
                                           name="email"
                                           value={this.state.email}
                                           onChange={this.handleChange}
                                           className={renderFieldStyle('email')}
                                           aria-describedby="emailHelp"
                                           placeholder="Email"
                                           maxLength="255"/>
                                    {renderErrorMessage('email')}
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">
                                    Пароль
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                <span className="input-group-text" id="inputGroupPrepend2">
                                    <i className="fa fa-lock fa-2x" aria-hidden="true"/>
                                </span>
                                    </div>
                                    <input type="password"
                                           id="password"
                                           name="password"
                                           className={renderFieldStyle('password')}
                                           value={this.state.password}
                                           onChange={this.handleChange}
                                           placeholder="Пароль"
                                           maxLength="255"/>
                                    {renderErrorMessage('password')}
                                </div>

                            </div>
                            <div className="form-group text-center">
                                <button type="submit" className="btn btn-primary btn-lg mt-3">
                                    Увійти&nbsp;&nbsp;
                                    <i className="fa fa-sign-in fa-lg" aria-hidden="true"/>
                                </button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        isAuthorized: state.auth.isAuthorized,
        user: state.auth.user
    };
}

function mapDispatchToProps(dispatch) {
    return {
        actions: {
            auth: bindActionCreators(authActions, dispatch),
            error: bindActionCreators(errorActions, dispatch)
        },
        notify: bindActionCreators(notify, dispatch)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(SignInPage);
