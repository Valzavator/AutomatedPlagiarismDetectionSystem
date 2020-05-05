import apiRequest from './apiRequest'

export const deleteVcs = async (authorizationProvider) =>
    await apiRequest('POST', '/vcs/delete/' + authorizationProvider);



