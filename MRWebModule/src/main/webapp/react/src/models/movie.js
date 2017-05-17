import * as movieService from '../services/movie';

import { USER_MOVIE_STATUS } from '../constants'

export default {
  namespace: 'movie',
  state: {
    movie: {},
    reviews: [],
    similarMovies: [],
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
    saveSimilarMovie(state, {payload: similarMovies}) {
      return {
        ...state,
        similarMovies,
      }
    }
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
    *fetchUserMovie({payload: id}, { call, put }) {
      if (localStorage.getItem('token') === null) {
        return;
      }
      const { data } = yield call(movieService.fetchUserMovie, id);
      console.log(data);
      yield put({
        type: 'saveUserMovie',
        payload: data,
      });
    },
    *fetchSimilarMovies({payload: id}, {call, put}) {
      const {data} = yield call(movieService.fetchSimilarMovie, id);
      console.log(data);
      yield put({
        type: 'saveSimilarMovie',
        payload: data,
      });
    },
    *collectMovie({payload: id},{ call, put}) {
      console.log(id);
      const { data } = yield call(movieService.collectMovie, id);
      console.log("collect");
      console.log(data);
      yield put({
        type: 'fetchUserMovie',
        payload: id,
      })
    },
    *removeCollectMovie({payload: id},{ call, put}) {
      console.log(id);
      const { data } = yield call(movieService.removeCollectMovie, id);
      console.log("remove collect");
      console.log(data);
      yield put({
        type: 'fetchUserMovie',
        payload: id,
      })
    },
    *evaluateMovie({payload: {id, evaluate }, onComplete},{ call, put}) {
      console.log(id);
      console.log(evaluate);
      const { data } = yield call(movieService.evaluateMovie, id, evaluate);
      console.log("evaluate");
      console.log(data);
      yield put({
        type: 'fetchUserMovie',
        payload: id,
      });
      onComplete();
    },
    *removeEvaluateMovie({payload: id},{ call, put}) {
      console.log(id);
      const { data } = yield call(movieService.removeEvaluateMovie, id);
      console.log("remove evaluate");
      console.log(data);
      yield put({
        type: 'fetchUserMovie',
        payload: id,
      })
    },
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        //let regex = new RegExp("/movie/");
        if (pathname.indexOf('/movie/') === 0) {
          let id = pathname.split('/movie/')[1];
          //console.log(pathname.split('/movie/'));
          window.scrollTo(0, 0);
          dispatch({type: 'fetchMovie', payload: id});
          dispatch({type: 'fetchUserMovie', payload: id});
          dispatch({type: 'fetchSimilarMovies', payload: id});
        }
      });
    },
  },
};
