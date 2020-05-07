import apiRequest from './apiRequest'

export const downloadPlagDetectionSettings = async () =>
    await apiRequest('GET', '/single-check/options');

export const uploadCodeToPlagDetection = async (formData) =>
    await apiRequest('POST', '/single-check/process', formData);


