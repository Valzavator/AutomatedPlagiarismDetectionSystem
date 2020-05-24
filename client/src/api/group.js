import apiRequest from './apiRequest'

export const getAllGroupsForCourse = async (courseId, page = 0, size = 6) =>
    await apiRequest('GET', '/courses/' + courseId + '/groups?page=' + page + '&size=' + size);

export const getGroup = async (groupId, courseId) =>
    await apiRequest('GET', '/courses/' + courseId + '/groups/' + groupId);

export const addGroupToCourse = async (courseId, data) =>
    await apiRequest('POST', `/courses/${courseId}/groups/add`, data);

export const deleteGroupFromCourse = async (courseId, groupId) =>
    await apiRequest('POST', `/courses/${courseId}/groups/${groupId}/delete`);