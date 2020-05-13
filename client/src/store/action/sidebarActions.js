import * as types from './actionTypes';

export function toggleSidebar() {
    return {type: types.SIDEBAR_TOGGLE};
}

export function changeSidebarState(newState, newSidebarTitle) {
    return {
        type: types.SIDEBAR_CHANGE_STATE,
        sidebarState: newState,
        sidebarTitle: newSidebarTitle
    };
}