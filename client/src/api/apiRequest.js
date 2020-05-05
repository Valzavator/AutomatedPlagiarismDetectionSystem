import axios from 'axios';
import LocalStorage from '../util/LocalStorage';
import {SERVER_BASE_URL} from "../util/constants";

const axiosAPI = axios.create({
    baseURL: SERVER_BASE_URL,
    headers: {
        'Accept': 'application/json'
    },
    validateStatus: function (status) {
        // return status < 500 && status !== 413 && status !== 401;
        return status < 300;
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
    } catch (error) {
        if (error.response) {
            throw Exception(error.response.data);
        } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            throw Exception({
                status: 504,
                error: 'Gateway Timeout',
            });
        } else {
            // Something happened in setting up the request that triggered an Error
            console.log('Error', error.message);
            throw Exception({
                status: 418,
                error: ' I’m a teapot ;)',
                message: error.message,
            });
        }
    }
};

function Exception(data) {
    const template = {
        timestamp: Date.now(),
        status: 200,
        error: '',
        message: '',
        debugMessage: '',
        path: ''
    }
    return Object.assign({}, template, data)
}

export default apiRequest;