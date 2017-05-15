import * as userService from '../services/user';

export default {
  namespace: 'user',
  state: {
    user: null,
  },
  reducers: {
    save(state, { payload: user }) {
      return {...state, user};
    },
  },
  effects: {
    *refresh(action, { put, select }) {
      //const user = yield select(state => state.user.user);
      const token = localStorage.getItem('token');
      console.log(token);
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
      const { data } = (yield call(userService.signIn, user));
      if (data.result !== undefined) {
        localStorage.setItem('token', data.result);
        yield put({type: 'fetch'});
        onSuccess(user.username);
      } else {
        onError(data.message.split(': ')[1]);
      }
    },
    *signOut({ onSuccess }, { call, put }) {
      localStorage.removeItem('token');
      //onComplete();
      yield put({
        type: 'save',
        payload: null,
      });
      onSuccess();
    },
    //*create({ payload: user , onComplete}, { call, put }) {
    //  yield call(usersService.create, user);
    //  onComplete();
    //  yield put({type: 'reload'});
    //},
    //*remove({ payload: id }, { call, put }) {
    //  yield call(usersService.remove, id);
    //  yield put({type: 'reload'});
    //},
    //*patch({ payload: { id, values }, onComplete }, { call, put }) {
    //  yield call(usersService.patch, id, values);
    //  onComplete();
    //  yield put({ type: 'reload' });
    //},
    //
    //*reload(action, { put, select }) {
    //  const page = yield select(state => state.users.page);
    //  yield put({type: 'fetch', payload: {page}});
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
