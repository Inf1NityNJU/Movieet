import request, { getToken } from '../utils/request';

export function fetch() {
  console.log(localStorage.getItem('token'));
  return request(`/api/user`, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function signUp(user) {
  console.log(user);
  return request(`/api/user/signup`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user),
  });
}

export function signIn(user) {
  return getToken(`/api/user/signin`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user),
  });
}

export function signOut() {
  return request(`/api/user/signout`, {
    method: 'POST',
  });
}

export function test() {
  return request(`/api/user/1`);
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
