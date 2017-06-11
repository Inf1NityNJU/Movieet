import request from '../utils/request';

import {GENRES} from '../constants';

export function fetchDefaultDirectors() {
    const url = `/api/prediction/director`;
    return request(url, {
        method: 'GET',
    });
}

export function fetchDefaultActors() {
    const url = `/api/prediction/actor`;
    return request(url, {
        method: 'GET',
    });
}

export function fetchGenresByKeyword(keyword, size, page = 1) {
  const all = GENRES.filter(genre => genre.id !== 0 && genre.value.toLowerCase().includes(keyword.toLowerCase()));
  const result = all.slice(size * (page - 1), size * page);
  const data = {
    result,
    page,
    totalCount: all.length,
  };
  return {data};
}

export function predict(combination) {
    const url = `/api/movie/predict?genre=${combination.genre}&director=${combination.director}&actor=${combination.actor}`;
    return request(url, {
        method: 'GET',
    });
}

export function estimate(combination) {
    const url = `/api/movie/estimate?genre=${combination.genre}&director=${combination.director}&actor=${combination.actor}`;
    return request(url, {
        method: 'GET',
    });
}