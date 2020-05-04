import * as types from '../action/actionTypes';

const INITIAL_STATE = {
    timestamp: Date.now(),
    status: 200,
    error: '',
    message: '',
    debugMessage: '',
    path: '',
    subErrors: []
};

export default function errorReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case types.THROW_ERROR:
            return Object.assign({}, state, action.error);
        case types.RESET_ERROR:
            return Object.assign({}, state, INITIAL_STATE);
        case types.SHOW_ERROR:
            return Object.assign({}, state,
                {
                    showError: action.showError
                }
            );
        default:
            return state;
    }
}
