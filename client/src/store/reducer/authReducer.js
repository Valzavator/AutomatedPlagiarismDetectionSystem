import * as types from '../action/actionTypes';

const INITIAL_STATE = {
    isAuthorized: false,
    userInfo: {
        username: 'NOT_LOADED',
        email: 'NOT_LOADED'
    }
};

export default function authReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case types.UNAUTH_USER:
            return Object.assign({}, state, INITIAL_STATE);
        case types.AUTH_USER:
            return Object.assign({}, state,
                {
                    userInfo: action.userInfo,
                    isAuthorized: true
                }
            );
        case types.SET_BASIC_USER_INFO:
            return Object.assign({}, state, {userInfo: action.userInfo});
        default:
            return state;
    }
}
