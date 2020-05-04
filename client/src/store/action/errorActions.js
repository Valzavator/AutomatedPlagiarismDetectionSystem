import * as types from '../action/actionTypes';

export function throwError(error) {
    return {type: types.THROW_ERROR, error}
}

export function resetError() {
    return {type: types.RESET_ERROR}
}

export function showError(isShow) {
    return {type: types.SHOW_ERROR, showError: isShow}
}