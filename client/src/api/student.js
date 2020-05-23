import apiRequest from './apiRequest'

export const getAllStudents= async (page = 0, size = 10) =>
    await apiRequest('GET', `/students?page=${page}&size=${size}`);

export const addStudentToSystem = async (data) =>
    await apiRequest('POST', `/students/add`, data);

export const deleteStudentFromSystem = async (studentId) =>
    await apiRequest('POST', `/students/${studentId}/delete`);