import request, { requestWithoutError } from '../utils/request';

import {PREVIEW_COLLECT_SIZE, PREVIEW_EVALUATE_SIZE, PREVIEW_FRIEND_SIZE } from '../constants'

export function fetch() {
  return request(`/api/user`, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function fetchUser(id) {
  const url = `/api/user/info/${id}`;
  return request(url, {
    method: 'GET',
  });
}

export function fetchUserFollow(id) {
  const url = `/api/user/${id}/status/`;
  return request(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
  });
}

export function signUp(user) {
  return request(`/api/user/signup`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user),
  });
}

export function signIn(user) {
  return requestWithoutError(`/api/user/signin`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user),
  });
}

export function signOut() {
  localStorage.removeItem('token');
  return request(`/api/user/signout`, {
    method: 'POST',
  });
}

export function postUserSurvey(survey) {
  const url = `api/user/survey`;
  return request(url, {
    method: 'POST',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
    body: JSON.stringify(survey),
  });
}


export function fetchUserCollectMovies(id, size = PREVIEW_COLLECT_SIZE, page = 1) {
  const url = `api/user/${id}/collect?orderBy=time&order=desc&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function fetchUserEvaluateMovies(id, size = PREVIEW_EVALUATE_SIZE, page = 1) {
  const url = `api/user/${id}/evaluate?orderBy=time&order=desc&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function fetchUserFollowing(id, size = PREVIEW_COLLECT_SIZE, page = 1) {
  const url = `api/user/${id}/following?orderBy=time&order=desc&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function fetchUserFollower(id, size = PREVIEW_COLLECT_SIZE, page = 1) {
  const url = `api/user/${id}/follower?orderBy=time&order=desc&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

//export function remove(id) {
//  return request(`/api/users/${id}`, {
//    method: 'DELETE',
//  });
//}
//
//export function patch(id, users) {
//  return request(`/api/users/${id}`, {
//    method: 'PATCH',
//    headers: {
//      'Content-Type': 'application/json'
//    },
//    body: JSON.stringify(users),
//  });
//}
