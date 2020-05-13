import apiRequest from './apiRequest'

export const getAllCourses = async (page = 0, size = 5) =>
    await apiRequest('GET', '/courses?page=' + page + '&size=' + size);

export const getCourse = async (courseId) =>
    await apiRequest('GET', '/courses/' + courseId);





