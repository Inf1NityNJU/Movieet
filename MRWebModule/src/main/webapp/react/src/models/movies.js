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
        order: ORDER[1],
      },
    },
    search: {
      keyword: null,
      recent: ["1", "something", "ddsa","asdas","asdasd","asdasdfa","sdasd","s","asdasdasd","asdasdadasd","hjfj","vcvz","yetye"],
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
    saveKeyword(state, { payload: keyword }) {
      return {
        ...state,
        search: {
          ...state.search,
          keyword,
        }
      }
    }
  },
  effects: {},
  subscriptions: {},
};
