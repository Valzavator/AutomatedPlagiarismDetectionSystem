import * as types from '../action/actionTypes';

const INITIAL_STATE = {
    isOpenSidebar: true,
    sidebarState: 'courseCatalog',
    sidebarTitle: 'Керування курсами:'
};

export default function sidebarReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case types.SIDEBAR_TOGGLE:
            return Object.assign({}, state,
                {
                    isOpenSidebar: !state.isOpenSidebar,
                });
        case types.SIDEBAR_CHANGE_STATE:
            let sidebarTitle = action.sidebarTitle ? action.sidebarTitle : INITIAL_STATE.sidebarTitle;
            return Object.assign({}, state,
                {
                    sidebarState: action.sidebarState,
                    sidebarTitle: sidebarTitle
                });
        default:
            return state;
    }
}
