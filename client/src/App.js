// import "bootstrap/dist/css/bootstrap.min.css";
import "popper.js/dist/umd/popper.min";
import "bootstrap/dist/js/bootstrap.min";
import "jquery/dist/jquery.slim.min";
import "font-awesome/css/font-awesome.min.css";
import "./stylesheet/App.css";

import React from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {Provider} from 'react-redux';

import Layout from "./view/container/Layout";
import HomePage from "./view/component/HomePage"
import SignInPage from "./view/container/SignInPage"
import SignUpPage from "./view/container/SignUpPage"
import ProfilePage from "./view/container/ProfilePage"
import CoursesPage from "./view/component/CoursesPage"
import ErrorPage from "./view/container/ErrorPage";
import NotFoundPage from "./view/component/NotFoundPage";
import configureStore from "./store/configureStore";
import Auth from "./view/container/Auth";
import AutocloseablePage from "./view/component/AutocloseablePage";
import SingleCheckPage from "./view/container/SingleCheckPage";

import {checkAuth, getBasicUser} from "./store/action/authActions";

const store = configureStore();
store.dispatch(checkAuth());
// store.dispatch(getBasicUser());

class App extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <BrowserRouter>
                    <Layout>
                        <Switch>
                            <Route exact path="/" component={HomePage}/>
                            <Route exact path="/signin" component={Auth(SignInPage,false)}/>
                            <Route exact path="/signup" component={Auth(SignUpPage,false)}/>
                            <Route exact path="/single-check" component={SingleCheckPage}/>

                            <Route exact path="/profile" component={Auth(ProfilePage, true)}/>
                            <Route exact path="/courses" component={Auth(CoursesPage)}/>

                            <Route exact path="/close" component={AutocloseablePage}/>
                            <Route exact path="/error" component={ErrorPage}/>
                            <Route component={NotFoundPage}/>
                        </Switch>
                    </Layout>
                </BrowserRouter>
            </Provider>
        )
    }
}

export default App;