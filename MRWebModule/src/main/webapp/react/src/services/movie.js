import request, { requestWithoutError } from '../utils/request';


import { LIKE_SIZE } from '../constants'

export function fetchMovie(id) {
  const url = `/api/movie/?id=${id}`;
  return request(url, {
    method: 'GET',
    //headers: {
    //'Authorization': localStorage.getItem('token')
    //},
  });
}

export function fetchSimilarMovie(id, size = LIKE_SIZE) {
  const url = `/api/movie/${id}/similar?size=${size}`;
  console.log(url);
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


export function collectMovie(movieId) {
  const url = `api/user/movie/${movieId}/collect`;
  return request(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function removeCollectMovie(movieId) {
  const url = `api/user/movie/${movieId}/collect`;
  return request(url, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function evaluateMovie(movieId, evaluate) {
  const url = `api/user/movie/${movieId}/evaluate`;
  return request(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')
    },
    body: JSON.stringify(evaluate),
  });
}

export function removeEvaluateMovie(movieId) {
  const url = `api/user/movie/${movieId}/evaluate`;
  return request(url, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')
    },
  });
}
