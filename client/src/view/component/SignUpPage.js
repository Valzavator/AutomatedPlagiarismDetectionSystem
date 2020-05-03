import React from "react";
import validator from "email-validator";
import {connect} from "react-redux";
import {notify} from "reapop";

class SignUpPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            email: '',
            firstName: '',
            lastName: '',
            password: ''
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

    handleSubmit(e) {
        e.preventDefault();

        this.validateForm();
    }

    validateForm() {
        const email = this.state.email;
        const firstName = this.state.firstName;
        const lastName = this.state.lastName;
        const password = this.state.password;

        if (!validator.validate(email)) {
            this.setError('email',
                'Некоректна Email адреса!')
        }

        if (!firstName || firstName.length === 0) {
            this.setError('firstName',
                'Буль-ласка заповніть дане поле!')
        } else if ( !/^[A-zА-яЁёІіЇїЄє]+$/.test(firstName)) {
            this.setError('firstName',
                "Ім'я повинно містити лише букви!")
        }

        if (!lastName || lastName.length === 0) {
            this.setError('lastName',
                'Буль-ласка заповніть дане поле!')
        } else if ( !/^[A-zА-яЁёІіЇїЄє]+$/.test(lastName)) {
            this.setError('lastName',
                "Прізвище повинно містити лише букви!")
        }

        if (!password || password.length === 0) {
            this.setError('password',
                'Буль-ласка заповніть дане поле!')
        } else if (password.length < 6) {
            this.setError('password',
                'Пароль повинен містити не менше 6 символів!')
        }
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

        return (
            <div className="row h-100 justify-content-md-center align-items-center">
                <div className="card w-50 mx-auto">
                    <div className="card-header h2 text-center">
                        Форма реєстрації
                    </div>
                    <div className="card-body mx-auto w-100">
                        <form acceptCharset="UTF-8" onSubmit={this.handleSubmit} method="post">
                            <div className="form-group">
                                <label htmlFor="email">
                                    Email
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                <span className="input-group-text" id="inputGroupPrepend0">
                                    <i className="fa fa-envelope fa-lg" aria-hidden="true"/>
                                </span>
                                    </div>
                                    <input type="text"
                                           id="email"
                                           name="email"
                                           value={this.state.email}
                                           onChange={this.handleChange}
                                           className={renderFieldStyle('email')}
                                           placeholder="Email"
                                           maxLength="255"/>
                                    {renderErrorMessage('email')}
                                </div>
                            </div>
                            <div className="form-group">
                                <label htmlFor="firstName">
                                    Ім'я
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                    <span className="input-group-text" id="inputGroupPrepend">
                                        <i className="fa fa-user fa-2x" aria-hidden="true"/>
                                    </span>
                                    </div>
                                    <input type="text"
                                           id="firstName"
                                           name="firstName"
                                           value={this.state.firstName}
                                           onChange={this.handleChange}
                                           className={renderFieldStyle('firstName')}
                                           placeholder="Ім'я"
                                           aria-describedby="inputGroupPrepend"
                                           maxLength="255"/>
                                    {renderErrorMessage('firstName')}
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="lastName">
                                    Прізвище
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                        <span className="input-group-text" id="inputGroupPrepend2">
                                            <i className="fa fa-user fa-2x" aria-hidden="true"/>
                                        </span>
                                    </div>
                                    <input type="text"
                                           id="lastName"
                                           name="lastName"
                                           value={this.state.lastName}
                                           onChange={this.handleChange}
                                           className={renderFieldStyle('lastName')}
                                           placeholder="Прізвище"
                                           maxLength="255"
                                           aria-describedby="inputGroupPrepend"/>
                                    {renderErrorMessage('lastName')}
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">
                                    Пароль
                                </label>
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                <span className="input-group-text" id="inputGroupPrepend3">
                                    <i className="fa fa-lock fa-2x" aria-hidden="true"/>
                                </span>
                                    </div>
                                    <input type="password"
                                           id="password"
                                           name="password"
                                           value={this.state.password}
                                           onChange={this.handleChange}
                                           className={renderFieldStyle('password')}
                                           placeholder="Пароль"
                                           maxLength="255"/>
                                    {renderErrorMessage('password')}
                                </div>
                            </div>

                            <div className="form-group text-center">
                                <button type="submit" className="btn btn-primary btn-lg mt-3">
                                    Зареєструватися&nbsp;&nbsp;
                                    <i className="fa fa-paper-plane-o fa-lg" aria-hidden="true"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}

export default connect(null, {notify})(SignUpPage);
