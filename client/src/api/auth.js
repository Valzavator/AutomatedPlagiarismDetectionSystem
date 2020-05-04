import apiRequest from './apiRequest'

export const signUp = async (signUpDto) => await apiRequest('POST', '/auth/signup', signUpDto);

export const signIn = async (signInDto) => await apiRequest('POST', '/auth/signin', signInDto);

