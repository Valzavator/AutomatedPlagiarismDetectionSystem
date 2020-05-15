import apiRequest from './apiRequest'

export const downloadSingleCheckPlagDetectionSettings = async () =>
    await apiRequest('GET', '/single-check/options');

export const uploadCodeToSingleCheckPlagDetection = async (formData) =>
    await apiRequest('POST', '/single-check/process', formData);

export const downloadTaskGroupPlagDetectionSettings = async (courseId) =>
    await apiRequest('GET', '/courses/' + courseId + '/options');


export const assignNewTaskGroup = async (formData) =>
    await apiRequest('POST', '/taskGroup-check/process', formData);

