import * as userService from '../services/user';

//import { USER_MOVIE_STATUS } from '../constants'

export default {
  namespace: 'user',
  state: {
    user: null,
    movie: {
      status: null,
      result: {
        collect: [],
        evaluate:[],
      },
    }
  },
  reducers: {
    save(state, { payload: user }) {
      return {...state, user};
    },

    saveCollectMovies(state, { payload: collect }) {
      return {
        ...state,
        movie: {
          ...state.movie,
          result: {
            ...state.movie.result,
            collect: collect.result,
          }
        }
      };
    },
    saveEvaluateMovies(state, { payload: evaluate }) {
      return {
        ...state,
        movie: {
          ...state.movie,
          result: {
            ...state.movie.result,
            evaluate: evaluate.result
          }
        }
      };
    },
  },
  effects: {
    *refresh({onComplete}, { call, put, select }) {
      const {user} = yield select(state => state.user);
      const token = localStorage.getItem('token');

      console.log("refresh");


      if (token !== null && user === null) {
        //yield put({type: 'fetch'});

        const { data } = yield call(userService.fetch);
        yield put({
          type: 'save',
          payload: data,
        });
        yield console.log("end fetch");
        //let u = yield [select(state => state.user.user);
        //yield console.log(u);
      }

      if ( onComplete ){
        onComplete();
      }
    },
    *fetch(action, { call, put,select }) {
      const { data } = yield call(userService.fetch);
      yield put({
        type: 'save',
        payload: data,
      });
    },
    *signUp({ payload: user , onComplete}, { call, put }) {
      const result = yield call(userService.signUp, user);
      //onComplete();
      yield put({type: 'fetch'});
    },
    *signIn({ payload: user , onSuccess, onError}, { call, put }) {
      const { data } = yield call(userService.signIn, user);
      if (data.result !== undefined) {
        localStorage.setItem('token', data.result);
        yield put({type: 'fetch'});
        onSuccess(user.username);
      } else {
        onError(data.message.split(': ')[1]);
      }
    },
    *signOut({ onSuccess }, { call, put }) {
      yield call(userService.signOut);
      //onComplete();
      yield put({
        type: 'save',
        payload: null,
      });
      onSuccess();
    },

    *fetchCollectMovies(action, { call, put, select }) {
      const {user} = yield select(state => state.user);

      if (user == null) {
        return;
      }
      const { data } = yield call(userService.fetchUserCollectMovies, user.id);
      console.log('collect movies');
      console.log(data);
      yield put ({
        type: 'saveCollectMovies',
        payload: data
      });
    },
    *fetchEvaluateMovies(action, { call, put, select }) {
      const {user} = yield select(state => state.user);
      console.log(user.id);
      if (user == null) {
        return;
      }
      const { data } = yield call(userService.fetchUserEvaluateMovies, user.id);
      console.log('evaluate movies');
      console.log(data);
      yield put ({
        type: 'saveEvaluateMovies',
        payload: data
      });
    },

  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        dispatch({
          type: 'refresh',
          onComplete: () => {
            if (pathname === '/user') {
              dispatch({type: 'fetchCollectMovies'});
              dispatch({type: 'fetchEvaluateMovies'});
            }
          },
        });
      });
    },
  },
};
