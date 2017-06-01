import request from '../utils/request';

export function fetchDirectorsByKeyword(keyword, size, page = 1) {
  const url = `api/director/search?keyword=${keyword}&orderBy=popularity&order=desc&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
  });
}

export function fetchActorsByKeyword(keyword, size, page = 1) {
  const url = `api/actor/search?keyword=${keyword}&orderBy=popularity&order=desc&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
  });
}

export function fetchDirector(id) {
  const url = `api/director/${id}`;
  return request(url, {
    method: 'GET',
  });
}

export function fetchActor(id) {
  const url = `api/actor/${id}`;
  return request(url, {
    method: 'GET',
  });
}
