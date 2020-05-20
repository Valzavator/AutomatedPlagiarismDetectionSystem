import LocalStorage from './LocalStorage';
import jwt_decode from 'jwt-decode';

export function getInfoFromToken(token) {
    try {
        let date = jwt_decode(LocalStorage.getToken());
        return {
            id: date.id,
            name: date.name,
            imageLink: date.imageLink,
            role: date.role
        }
    } catch (err) {
        return null;
    }
}