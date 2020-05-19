import apiRequest from './apiRequest'

export const downloadSingleCheckPlagDetectionSettings = async () =>
    await apiRequest('GET', '/single-check/options');

export const uploadCodeToSingleCheckPlagDetection = async (formData) =>
    await apiRequest('POST', '/single-check/process', formData);

export const downloadTaskGroupPlagDetectionSettings = async (courseId, groupId) =>
    await apiRequest('GET', '/courses/' + courseId + '/groups/' + groupId + '/tasks/options');

export const assignNewTaskGroup = async (courseId, groupId, formData) =>
    await apiRequest('POST', '/courses/' + courseId + '/groups/' + groupId + '/tasks/assign', formData);

