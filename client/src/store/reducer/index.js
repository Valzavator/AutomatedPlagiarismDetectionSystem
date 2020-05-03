import {combineReducers} from 'redux';
import auth from './authReducer';
import sidebar from "./sidebarReducer";
import {reducer as notificationsReducer} from 'reapop';

const rootReducer = combineReducers({
    auth,
    sidebar,
    notifications: notificationsReducer()
});

export default rootReducer;