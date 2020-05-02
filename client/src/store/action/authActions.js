// import LocalStorage from '../utils/LocalStorage';
// import * as types from './actionTypes';
// import {logIn} from '../api/auth'
// import {getUserInfo} from '../api/users'
//
// import {getInfoFromToken} from '../utils/Token';

// export function signInUser({email, password}) {
//     return async function (dispatch) {
//         try {
//             let res = await logIn({email, password});
//             let invalidToken = false;
//             if (res.status === 200) {
//                 LocalStorage.authenticateUser(res.data.token);
//                 let userInfo = getInfoFromToken();
//                 if (userInfo)
//                     dispatch({type: types.AUTH_USER, user: userInfo});
//                 else invalidToken = true;
//             }
//             if (res.status === 401 || invalidToken) {
//                 dispatch({type: types.UNAUTH_USER});
//             }
//         } catch (error) {
//             dispatch({type: types.AUTH_ERROR, error});
//         }
//     };
// }
//
// export function logoutUser() {
//     return function (dispatch) {
//         LocalStorage.deauthenticateUser();
//         dispatch({type: types.UNAUTH_USER});
//     };
// }
//
// export function checkAuth() {
//     return function (dispatch) {
//         let userInfo = getInfoFromToken();
//         if (userInfo)
//             return dispatch({
//                 type: types.AUTH_USER, user: {
//                     id: userInfo.id,
//                     role: userInfo.role
//                 }
//             });
//         else {
//             LocalStorage.deauthenticateUser();
//             dispatch({type: types.UNAUTH_USER, user: {}});
//         }
//     };
// }
//
// export function getUser() {
//     return async function (dispatch) {
//         try {
//             if (!LocalStorage.isUserAuthenticated()) return;
//             let userInfo = await getUserInfo();
//             if (!userInfo) {
//                 LocalStorage.deauthenticateUser();
//                 dispatch({type: types.UNAUTH_USER});
//             }
//             else
//                 dispatch({type: types.SET_USER, user: userInfo});
//         } catch (error) {
//             dispatch({type: types.AUTH_ERROR, error});
//         }
//     };
// }
//
// export function resetAuthError() {
//     return {type: types.RESET_AUTH_ERROR}
// }
