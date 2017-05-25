import request from '../utils/request';

import { NEW_RELEASED_SIZE, RECOMMEND_SIZE } from '../constants'

export function fetchLatestMovies(size = NEW_RELEASED_SIZE) {
  const url = `api/movie/latest?size=${size}`;
  return request(url, {
    method: 'GET',
  });
}

export function fetchRecommendMovies(size = RECOMMEND_SIZE) {
  const url = `api/user/recommend?size=${size}`;
  return request(url, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('token')
    },
  });
}


export function fetchMoviesByKeyword(keyword, size, page = 1) {
  const url = `api/movie/search?keyword=${keyword}&orderBy=score&order=desc&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
  });
}

export function fetchMoviesByGenre(genres, sort, order, size, page = 1) {
  const url = `api/movie/search?genre=${genres}&orderBy=${sort}&order=${order}&size=${size}&page=${page}`;
  return request(url, {
    method: 'GET',
  });
}

