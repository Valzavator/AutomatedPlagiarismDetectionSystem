import * as types from '../action/actionTypes';

const INITIAL_STATE = {
    isOpenSidebar: true
};

export default function sidebarReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case types.SIDEBAR_CHANGE_STATE:
            return Object.assign({}, state,
                {
                    isOpenSidebar: !state.isOpenSidebar,
                });
        default:
            return state;
    }
}
