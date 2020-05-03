import apiRequest from './apiRequest'

export const signUp = async (newUser) => await apiRequest('POST', '/auth/signup', newUser);

export const signIn = async userData => await apiRequest('POST', '/auth/signin', userData);

