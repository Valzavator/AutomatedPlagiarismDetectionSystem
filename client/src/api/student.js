import apiRequest from './apiRequest'

export const getAllStudents= async (page = 0, size = 10) =>
    await apiRequest('GET', `/students?page=${page}&size=${size}`);