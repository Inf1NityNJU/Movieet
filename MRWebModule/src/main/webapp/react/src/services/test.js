import request from '../utils/request';



export function fetch() {
    return request(`/api/language`, {
        method: 'GET',
    });
}

export function estimate() {
    const url = `/api/movie/estimate?genre=36&director=12835&actor=85`;
    return request(url, {
        method: 'GET',
    });
}