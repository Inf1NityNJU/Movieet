import request from '../utils/request';

import { SEARCH_PREVIEW_MOVIE_SIZE } from '../constants'

export function fetchMoviesByKeyword(keyword, size, page = 1) {
  return request(`api/movie/search?keyword=${keyword}&orderBy=score&order=desc&size=${size}&page=${page}`, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function fetchMoviesByGenre(genres, size, page = 1) {
  return request(`api/movie/search?genre=${genres}&orderBy=score&order=desc&size=${size}&page=${page}`, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}

export function test(keyword) {
  console.log(keyword);
}
