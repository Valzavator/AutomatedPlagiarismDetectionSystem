import apiRequest from './apiRequest'

export const getAllCourseTasks= async (courseId, page = 0, size = 6) =>
    await apiRequest('GET', `/courses/${courseId}/tasks?page=${page}&size=${size}`);

export const addTaskToCourse= async (courseId, data) =>
    await apiRequest('POST', `/courses/${courseId}/tasks/add`, data);

