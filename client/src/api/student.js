import apiRequest from './apiRequest'

export const getAllStudents= async (page = 0, size = 10) =>
    await apiRequest('GET', `/students?page=${page}&size=${size}`);

export const getAllStudentsForAddingToGroup = async (courseId, groupId) =>
    await apiRequest('GET', `/courses/${courseId}/groups/${groupId}/students`);