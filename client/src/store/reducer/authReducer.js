import * as types from '../action/actionTypes';

const INITIAL_STATE = {
    isAuthorized: false,
    username: 'Svynarchuk'
};

export default function authReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case types.UNAUTH_USER:
            return Object.assign({}, state,
                {
                    // user: {},
                    isAuthorized: false
                }
            );
        case types.AUTH_USER:
            return Object.assign({}, state,
                {
                    // user: action.user,
                    isAuthorized: true
                }
            );
        case types.SET_AUTH_USER:
            return Object.assign({}, state, {user: action.user});
        default:
            return state;
    }
}
