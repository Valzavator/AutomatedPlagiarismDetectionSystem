// import "bootstrap/dist/css/bootstrap.min.css";
import "popper.js/dist/umd/popper.min";
import "bootstrap/dist/js/bootstrap.min";
import "jquery/dist/jquery.slim.min";
import "font-awesome/css/font-awesome.min.css";
import "./stylesheet/App.css";
import "./stylesheet/react-datetime.css";

import React from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {Provider} from 'react-redux';

import Layout from "./view/container/Layout";
import HomePage from "./view/component/HomePage"
import SignInPage from "./view/container/SignInPage"
import SignUpPage from "./view/container/SignUpPage"
import ProfilePage from "./view/container/ProfilePage"
import CourseCatalogPage from "./view/container/CourseCatalogPage"
import ErrorPage from "./view/container/ErrorPage";
import NotFoundPage from "./view/component/NotFoundPage";
import configureStore from "./store/configureStore";
import Auth from "./view/container/Auth";
import AutocloseablePage from "./view/component/AutocloseablePage";
import SingleCheckPage from "./view/container/SingleCheckPage";
import CoursePage from "./view/container/CoursePage";
import GroupPage from "./view/container/GroupPage";
import TaskGroupDetailPage from "./view/container/TaskGroupDetailPage";
import TasksPage from "./view/container/TasksPage";
import StudentsPage from "./view/container/StudentsPage";
import NotFoundRedirect from "./view/component/NotFoundRedirect";

import {checkAuth, getBasicUser} from "./store/action/authActions";

const store = configureStore();
store.dispatch(checkAuth());
store.dispatch(getBasicUser());

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
                            <Route exact path="/students" component={Auth(StudentsPage)}/>
                            <Route exact path="/courses" component={Auth(CourseCatalogPage)}/>
                            <Route exact path="/courses/:courseId" component={Auth(CoursePage)}/>
                            <Route exact path="/courses/:courseId/tasks" component={Auth(TasksPage)}/>
                            <Route exact path="/courses/:courseId/groups/:groupId" component={Auth(GroupPage)}/>
                            <Route exact path="/courses/:courseId/groups/:groupId/tasks/:taskId" component={Auth(TaskGroupDetailPage)}/>

                            <Route exact path="/close" component={AutocloseablePage}/>
                            <Route exact path="/error" component={ErrorPage}/>
                            <Route exact path="/404" component={NotFoundPage}/>
                            <Route component={NotFoundRedirect}/>
                        </Switch>
                    </Layout>
                </BrowserRouter>
            </Provider>
        )
    }
}

export default App;