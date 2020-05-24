import apiRequest from './apiRequest'

export const getAllCourses = async (page = 0, size = 5) =>
    await apiRequest('GET', '/courses?page=' + page + '&size=' + size);

export const getCourse = async (courseId) =>
    await apiRequest('GET', '/courses/' + courseId);

export const addCourseToSystem = async (data) =>
    await apiRequest('POST', `/courses/add`, data);

export const deleteCourseFromSystem = async (courseId) =>
    await apiRequest('POST', `/courses/${courseId}/delete`);


