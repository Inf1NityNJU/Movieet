import request from '../utils/request';

import { GENRES } from '../constants';

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


/**
 * Created by Sorumi on 17/5/15.
 */
