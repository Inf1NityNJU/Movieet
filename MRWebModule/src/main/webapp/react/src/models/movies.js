import { GENRES, MOVIE_SORT, ORDER } from '../constants'

export default {
  namespace: 'movies',
  state: {
    category: {
      filter: {
        genres: [GENRES[0]],
      },
      sort: {
        name: MOVIE_SORT[0],
        order: ORDER[0],
      },
    }
  },
  reducers: {
    saveGenres(state, { payload: genres }) {
      return {
        ...state,
        category: {
          ...state.category,
          filter: {
            genres
          }
        }
      };
    },
    saveSort(state, { payload: sort }) {
      return {
        ...state,
        category: {
          ...state.category,
          sort: sort
        }
      };
    },
  },
  effects: {},
  subscriptions: {},
};
