import apiRequest from './apiRequest'

export const getTaskGroup = async (courseId, groupId, taskId) =>
    await apiRequest('GET', `/courses/${courseId}/groups/${groupId}/tasks/${taskId}`);

export const checkTaskNow = async (courseId, groupId, taskId) =>
    await apiRequest('POST', `/courses/${courseId}/groups/${groupId}/tasks/${taskId}/check-now`);

export const assignNewTaskGroup = async (courseId, groupId, formData) =>
    await apiRequest('POST', '/courses/' + courseId + '/groups/' + groupId + '/tasks/assign', formData);

export const deleteTaskGroup = async (courseId, groupId, taskId) =>
    await apiRequest('POST', `/courses/${courseId}/groups/${groupId}/tasks/${taskId}/delete`);
