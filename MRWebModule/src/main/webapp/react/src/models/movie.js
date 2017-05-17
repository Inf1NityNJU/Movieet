import * as movieService from '../services/movie';

import { USER_MOVIE_STATUS } from '../constants'

export default {
  namespace: 'movie',
  state: {
    movie: {

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
    *fetchMovie({payload: id}, { call, put }) {
      const { data } = yield call(movieService.fetchMovie, id);
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
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        //let regex = new RegExp("/movie/");
        if (pathname.indexOf('/movie/') === 0) {
          let id = pathname.split('/movie/')[1];
          //console.log(pathname.split('/movie/'));
          dispatch({type: 'fetchMovie', payload: id});
        }
      });
    },
  },
};
