import request from '../utils/request';

import { GENRES, RANK_MOVIE_SIZE, RANK_PEOPLE_SIZE } from '../constants';


// rank
export function fetchRankMovieFR(size = RANK_MOVIE_SIZE) {
    const url = `/api/analysis/rank/movieFR?size=${size}`;
    return request(url, {
        method: 'GET',
    });
}

export function fetchRankMovieCN(size = RANK_MOVIE_SIZE) {
    const url = `/api/analysis/rank/movieCN?size=${size}`;
    return request(url, {
        method: 'GET',
    });
}

export function fetchRankDirector(size = RANK_PEOPLE_SIZE) {
    const url = `/api/analysis/rank/director?size=${size}`;
    return request(url, {
        method: 'GET',
    });
}

export function fetchRankActor(size = RANK_PEOPLE_SIZE) {
    const url = `/api/analysis/rank/actor?size=${size}`;
    return request(url, {
        method: 'GET',
    });
}

// data
export function fetchQuantityInGenre() {

  let data = [];

  GENRES.map((genre) => {
    data.push({
      genre: genre,
      quantity: Math.ceil(Math.random() * 200),
    });
  });
  return {data};

}

export function fetchGenreQuantityScoreInYear() {
  let data = [];

  for (let i = 1970; i <= 2017; i++) {
    data.push({
      year: i + '',
      count: Math.ceil(Math.random() * 100),
      score: Math.random() * 10,
    })
  }

  return {data};

}


export function fetchCountryScoreInYear(id) {
    const url = `/api/analysis/countryscoreinyear?country=${id}`;
    return request(url, {
        method: 'GET',
    });
}

export function fetchCountryCount(id) {
    const url = `/api/analysis/countrycount?country=${id}`;
    return request(url, {
        method: 'GET',
    });
}
