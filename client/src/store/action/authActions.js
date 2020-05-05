import LocalStorage from '../../util/LocalStorage';
import * as types from './actionTypes';
import {signIn} from '../../api/auth'
import {getBasicUserInfo} from "../../api/user";

// import {getUserInfo} from '../../api/user'

export function signInUser({email, password}) {
    return async function (dispatch) {
        try {
            let res = await signIn({email, password});
            if (res.data.accessToken) {
                LocalStorage.authenticateUser(res.data.accessToken);
                dispatch({type: types.AUTH_USER});
                return true;
            }
        } catch (error) {
            if (error.status === 401) {
                dispatch({type: types.UNAUTH_USER});
                return false;
            } else {
                dispatch({type: types.THROW_ERROR, error});
            }
        }
    };
}

export function logoutUser() {
    return function (dispatch) {
        LocalStorage.deauthenticateUser();
        dispatch({type: types.UNAUTH_USER});
    };
}

export function checkAuth() {
    return function (dispatch) {
        if (LocalStorage.isUserAuthenticated()) {
            dispatch({type: types.AUTH_USER});
        } else {
            dispatch({type: types.UNAUTH_USER});
        }
    };
}

export function getBasicUser() {
    return async function (dispatch) {
        try {
            if (!LocalStorage.isUserAuthenticated()) return;
            let basicUserInfo = await getBasicUserInfo();
            if (basicUserInfo) {
                dispatch({type: types.SET_BASIC_USER_INFO, userInfo: basicUserInfo});
            } else {
                LocalStorage.deauthenticateUser();
                dispatch({type: types.UNAUTH_USER});
            }
        } catch (error) {
            if (error.status === 401) {
                LocalStorage.deauthenticateUser();
                dispatch({type: types.UNAUTH_USER});
                return false;
            } else {
                dispatch({type: types.THROW_ERROR, error});
            }
        }
    };
}

