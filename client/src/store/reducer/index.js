import {combineReducers} from 'redux';
import auth from './authReducer';
import sidebar from "./sidebarReducer";
import error from "./errorReducer";
import {reducer as notificationsReducer} from 'reapop';

const rootReducer = combineReducers({
    auth,
    sidebar,
    error,
    notifications: notificationsReducer()
});

export default rootReducer;