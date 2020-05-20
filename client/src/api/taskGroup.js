import apiRequest from './apiRequest'

export const getTaskGroup = async (courseId, groupId, taskId) =>
    await apiRequest('GET', `/courses/${courseId}/groups/${groupId}/tasks/${taskId}`);

export const deleteTaskGroup = async (courseId, groupId, taskId) =>
    await apiRequest('POST', `/courses/${courseId}/groups/${groupId}/tasks/${taskId}/delete`);
