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
    //saveStatus(state, { payload: status }) {
    //  return {
    //    ...state,
    //    movie: {
    //      ...state.movie,
    //      status,
    //    }
    //  }
    //},
    //saveCollect(state, { payload: collect }) {
    //  return {
    //    ...state,
    //    movie: {
    //      ...state.movie,
    //      result: {
    //        ...state.result,
    //        collect,
    //      }
    //    }
    //  }
    //}
  },
  effects: {
    *refresh(action, { put, select }) {
      //const user = yield select(state => state.user.user);
      const token = localStorage.getItem('token');
      if (token !== null) {
        yield put({type: 'fetch'});
      }
    },
    *fetch(action, { call, put }) {
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
    //*fetchUserMovie({ payload: status }, { call, put }) {
    //  yield put ({
    //    type: 'saveStatus',
    //    payload: status
    //  });
    //  // todo
    //},

  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        dispatch({type: 'refresh'});
      });
    },
  },
};
