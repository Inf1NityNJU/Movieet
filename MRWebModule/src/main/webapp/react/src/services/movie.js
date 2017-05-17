import request, { requestWithoutError } from '../utils/request';

export function fetchMovie(id) {
  return request(`/api/movie/${id}`, {
    method: 'GET',
    //headers: {
    //'Authorization': localStorage.getItem('token')
    //},
  });
}

export function fetchUserMovie(id) {
  if (localStorage.getItem('token') === null) {
    return null;
  }
  return request(`/api/user/movie/${id}`, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}
