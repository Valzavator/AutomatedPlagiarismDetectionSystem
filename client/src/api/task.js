import apiRequest from './apiRequest'

export const getAllCourseTasks= async (courseId, page = 0, size = 6) =>
    await apiRequest('GET', `/courses/${courseId}/tasks?page=${page}&size=${size}`);
