import request from '../utils/request';

import { SEARCH_PREVIEW_MOVIE_SIZE } from '../constants'

export function fetchMoviesByKeyword(keyword, size, page = 1) {
  const url = `api/movie/search?keyword=${keyword}&orderBy=score&order=desc&size=${size}&page=${page}`;
  console.log(url);
  return request(url, {
    method: 'GET',
    //headers: {
      //'Authorization': localStorage.getItem('token')
    //},
  });
}

export function fetchMoviesByGenre(genres, sort, order, size, page = 1) {
  const url = `api/movie/search?genre=${genres}&orderBy=${sort}&order=${order}&size=${size}&page=${page}`;
  console.log(url);
  return request(url, {
    method: 'GET',
    //headers: {
    //  'Authorization': localStorage.getItem('token')
    //},
  });
}
