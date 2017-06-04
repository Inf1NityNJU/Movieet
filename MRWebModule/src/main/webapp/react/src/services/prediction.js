import request from '../utils/request';

import {GENRES} from '../constants';

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
