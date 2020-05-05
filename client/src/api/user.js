import apiRequest from './apiRequest'

export const getBasicUserInfo = async () => {
    let res = await apiRequest('GET', '/user/basic');
    return res.status === 200
        ? res.data
        : null;
}

export const getUserProfileInfo = async () => await apiRequest('GET', '/user/profile');

export const getUserProfileVcsInfo = async () => await apiRequest('GET', '/user/profile/vcs');




