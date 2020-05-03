import React from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {Provider} from 'react-redux';

import Layout from "./container/Layout";
import HomePage from "./component/HomePage"
import SignInPage from "./component/SignInPage"
import SignUpPage from "./component/SignUpPage"
import configureStore from "./store/configureStore";

// import "bootstrap/dist/css/bootstrap.min.css";
import "popper.js/dist/umd/popper.min";
import "bootstrap/dist/js/bootstrap.min";
import "jquery/dist/jquery.slim.min";
import "font-awesome/css/font-awesome.min.css";
import "./App.css";

const store = configureStore();
// store.dispatch(checkAuth());
// store.dispatch(getUser());

class App extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <BrowserRouter>
                    <Layout>
                        <Switch>
                            <Route exact path="/" component={HomePage}/>
                            <Route exact path="/signin" component={SignInPage}/>
                            <Route exact path="/signup" component={SignUpPage}/>

                            {/*<Route exact path="/error" component={ErrorPage}/>*/}
                            {/*<Route component={NotFoundPage}/>*/}
                        </Switch>
                    </Layout>
                </BrowserRouter>
            </Provider>
        )
    }
}

export default App;