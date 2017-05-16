import * as moviesService from '../services/movies';

import { GENRES, MOVIE_SORT, ORDER, SEARCH_STATUS, CATEGORY_SIZE } from '../constants'

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
        name: MOVIE_SORT[1],
        order: ORDER[1],
      },
      result: {
        movies: [],
      },
      page: null,
      totalCount: null,
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
    saveSearchMovies(state, { payload: {result, pageNo, totalCount} }) {
      return {
        ...state,
        search: {
          ...state.search,
          result: {
            ...state.search.result,
            movies: result,
          },
          page: pageNo,
          totalCount,
        }
      }
    },
    saveCategoryMovies(state, { payload: {result, pageNo, totalCount} }) {
      return {
        ...state,
        category: {
          ...state.category,
          result: {
            ...state.category.result,
            movies: result,
          },
          page: pageNo,
          totalCount,
        }
      }
    },
  },
  effects: {

    *changeGenres({ payload: {genres} }, { call, put, select }) {
      //const sort = yield select(state => state.movies.category.sort);
      console.log(genres);
      yield put({
        type: 'saveGenres',
        payload: genres,
      });
      //const { data } = yield call(moviesService.fetchMoviesByGenre, genres, sort.name, sort.order, size, page);
      //console.log(data);
      yield put({
        type: 'fetchMoviesByCategory',
        payload: {}
      });
    },


    *changeSort({ payload: {name, order} }, { call, put, select }) {
      //const category = yield select(state => state.movies.category);
      console.log(name + ' ' + order);
      yield put({
        type: 'saveSort',
        payload: {
          name,
          order,
        }
      });
      //const { data } = yield call(moviesService.fetchMoviesByGenre, category.filter.genres, name, order, size, page);
      //console.log(data);
      yield put({
        type: 'fetchMoviesByCategory',
      });
    },

    *fetchMoviesByCategory({ payload: { size = CATEGORY_SIZE, page = 1 } }, { call, put, select}){
      const category = yield select(state => state.movies.category);
      //console.log(size);
      const { data } = yield call(moviesService.fetchMoviesByGenre, category.filter.genres, category.sort.name, category.sort.order, size, page);
      console.log(data);

      yield put({
        type: 'saveCategoryMovies',
        payload: data,
      });
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
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/movies/category') {
          dispatch({type: 'fetchMoviesByCategory', payload: {} });
        }
      });
    },
  },
};
