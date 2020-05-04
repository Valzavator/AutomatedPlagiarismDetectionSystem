import apiRequest from './apiRequest'

export const getUserInfo = async (signInDto) => await apiRequest('POST', '...', signInDto);

