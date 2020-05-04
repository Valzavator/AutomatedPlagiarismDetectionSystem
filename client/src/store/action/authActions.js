import LocalStorage from '../../util/LocalStorage';
import * as types from './actionTypes';
import {signIn} from '../../api/auth'

// import {getUserInfo} from '../../api/user'

export function signInUser({email, password}) {
    return async function (dispatch) {
        try {
            let res = await signIn({email, password});
            if (res.status === 200 && res.data.accessToken) {
                LocalStorage.authenticateUser(res.data.accessToken);
                dispatch({type: types.AUTH_USER});
                return true;
            } else {
                let error = res.data;
                dispatch({type: types.THROW_ERROR, error});
                return false;
            }
        } catch (error) {
            if (error.status === 401) {
                dispatch({type: types.UNAUTH_USER});
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
            return dispatch({type: types.AUTH_USER});

        }
            // let userInfo = getInfoFromToken();
            // if (userInfo)
            //     return dispatch({
            //         type: types.AUTH_USER, user: {
            //             id: userInfo.id,
            //             role: userInfo.role
            //         }
        //     });
        else {
            LocalStorage.deauthenticateUser();
            dispatch({type: types.UNAUTH_USER, user: {}});
        }
    };
}

//
// export function getUser() {
//     return async function (dispatch) {
//         try {
//             if (!LocalStorage.isUserAuthenticated()) return;
//             let userInfo = await getUserInfo();
//             if (!userInfo) {
//                 LocalStorage.deauthenticateUser();
//                 dispatch({type: types.UNAUTH_USER});
//             } else
//                 dispatch({type: types.SET_USER, user: userInfo});
//         } catch (error) {
//             dispatch({type: types.AUTH_ERROR, error});
//         }
//     };
// }

