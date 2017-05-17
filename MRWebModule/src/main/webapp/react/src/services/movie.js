import request, { requestWithoutError } from '../utils/request';

export function fetchMovie(id) {
  const url = `/api/movie/?id=${id}`;
  return request(url, {
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
