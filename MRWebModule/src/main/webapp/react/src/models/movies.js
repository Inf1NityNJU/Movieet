import * as moviesService from '../services/movies';

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
      recent: ["1", "something", "ddsa", "asdas", "asdasd", "asdasdfa"],
      status: SEARCH_STATUS[0],
      result: {
        movies: [],
      },
      page: null,
      totalCount: null,
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
    },
    saveMovies(state, { payload }) {
      return {
        ...state,
        search: {
          ...state.search,
          result: {
            ...state.search.result,
            movies: payload.movies,
          },
          page: payload.page,
          totalCount: payload.totalCount,
        }
      }
    },
  },
  effects: {

    *changeGenres({ payload: {genres, size, page = 1} }, { call, put }) {
      console.log(genres);
      yield put({
        type: 'saveGenres',
        payload: genres,
      });
      const { data } = yield call(moviesService.fetchMoviesByGenre, genres, size, page);
      console.log(data);


    },

    *fetchMoviesByKeyword({ payload: {keyword, size, page} }, { call, put }){
      //console.log(keyword);
      //console.log(111);
      yield call(moviesService.test("111"));
      yield put({
        type: 'saveKeyword',
        payload: {keyword}
      });
      const { data } = yield call(moviesService.fetchMoviesByKeyword, keyword, size, page);
      console.log(data);
      yield put({
        type: 'saveMovies',
        payload: {
          movies: data.result,
          page: data.pageNo,
          totalCount: data.totalCount,
        }
      })

    },
  },
  subscriptions: {},
};
