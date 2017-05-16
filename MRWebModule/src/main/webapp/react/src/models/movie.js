import * as movieService from '../services/movie';

import { USER_MOVIE_STATUS } from '../constants'

export default {
  namespace: 'movie',
  state: {
    movie: {
      id: null,
      title: null,
      poster: null,
    },
    reviews: [],
    likeMovies: [],
    user: {
      status: null
    }

  },
  reducers: {
    saveMovie(state, { payload: movie }) {
      return {
        ...state,
        movie,
      }
    },
    saveUserMovie(state, { payload: user }) {
      return {
        ...state,
        user,
      }
    },
  },
  effects: {
    *fetchMovie(action, { call, put }) {
      const { data } = yield call(movieService.fetch);
      console.log(data);
      yield put({
        type: 'saveMovie',
        payload: data,
      });
    },
    *fetchUserMovie(action, { call, put }) {
      const { data } = yield call(movieService.fetch);
      console.log(data);
      yield put({
        type: 'saveUserMovie',
        payload: data,
      });
    }
  },
  subscriptions: {},
};
