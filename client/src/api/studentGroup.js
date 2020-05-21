import apiRequest from './apiRequest'

export const getAllStudentsForAddingToGroup = async (courseId, groupId) =>
    await apiRequest('GET', `/courses/${courseId}/groups/${groupId}/students`);

export const addStudentToGroup = async (courseId, groupId, data) =>
    await apiRequest('POST', `/courses/${courseId}/groups/${groupId}/students/add`, data);

export const deleteStudentFromGroup = async (courseId, groupId, studentId) =>
    await apiRequest('POST', `/courses/${courseId}/groups/${groupId}/students/${studentId}/delete`);