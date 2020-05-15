import apiRequest from './apiRequest'

export const getTaskGroup = async (courseId, groupId, taskId) =>
    await apiRequest('GET', '/courses/' + courseId + '/groups/' + groupId + '/tasks/' + taskId);



