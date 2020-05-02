import React from "react";
import {connect} from "react-redux";

class SignInPage extends React.Component {
    render() {
        const { name, surname, age } = this.props.activeUser

        return (
            <div>
                <p>Привет из App, {name} {surname}!</p>
                <p>Тебе уже {age} ?</p>

                "SignInPage"
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        activeUser: state.auth,
    };
}

export default connect(mapStateToProps)(SignInPage);


// export default SignInPage;