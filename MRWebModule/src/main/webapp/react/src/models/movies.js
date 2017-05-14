import { GENRES, MOVIE_SORT, ORDER, SEARCH_STATUS } from '../constants'

export default {
  namespace: 'movies',
  state: {
    discover: {
      newReleased: [],
      recommend: [],
    },
    category: {
      filter: {
        genres: [GENRES[0]],
      },
      sort: {
        name: MOVIE_SORT[0],
        order: ORDER[1],
      },
      list: [],
    },
    search: {
      keyword: null,
      recent: ["1", "something", "ddsa","asdas","asdasd","asdasdfa"],
      status: SEARCH_STATUS[0],
      result: {
        movies: [],
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
    saveKeyword(state, { payload: keyword }) {
      return {
        ...state,
        search: {
          ...state.search,
          keyword,
        }
      }
    },
    saveStatus(state, { payload: status }) {
      return {
        ...state,
        search: {
          ...state.search,
          status,
        }
      }
    }
  },
  effects: {},
  subscriptions: {},
};
