import request, { requestWithoutError } from '../utils/request';

export function fetch() {
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
