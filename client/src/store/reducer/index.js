import {combineReducers} from 'redux';
// import search from './searchReducer';
import auth from './authReducer';
// import chat from './chatReducer';
// import exchange from './exchangeReducer';
// import error from './errorReducer';

const rootReducer = combineReducers({
    // search,
    auth,
    // chat,
    // exchange,
    // error
});

export default rootReducer;