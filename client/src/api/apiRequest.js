import axios from 'axios';
import LocalStorage from '../utils/LocalStorage';

const axiosAPI = axios.create({
    baseURL: '/api/v2',
    headers: {
        'Accept': 'application/json'
    },
    validateStatus: function (status) {
        return status < 500 && status !== 413 && status !== 401;
    }
});

export const apiRequest = async (method, path, body) => {
    axiosAPI.defaults.headers.common['Authorization'] = `Bearer ${LocalStorage.getToken()}`;
    try {
        if (method.toUpperCase() === 'GET') {
            return await axiosAPI.get(path);
        } else {  // POST
            return await axiosAPI.post(path, body);
        }
    } catch (err) {
        throw {
            timestamp: err.response.timestamp,
            status: err.response.status,
            error: err.response.error,
            message: err.response.message,
            debugMessage: err.response.debugMessage,
            path: err.response.path
        };
    }
};

export default apiRequest;