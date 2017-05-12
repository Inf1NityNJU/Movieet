import * as userService from '../services/user';

export default {
  namespace: 'user',
  state: {
    id: null,
    username: null,
    //list: [],
    //total: null,
    //page: null,
  },
  reducers: {
    save(state, { payload: { id, username } }) {
      return {...state, id, username};
    },
  },
  effects: {
    *fetch(action, { call, put }) {
      const { data, headers } = yield call(userService.fetch);
      console.log('fetch');
      console.log(data);
      yield put({
        type: 'save',
        payload: {
          id: data.id,
          username: data.username,
        },
      });
    },
    *signUp({ payload: user , onComplete}, { call, put }) {
      const result = yield call(userService.signUp, user);
      //onComplete();
      yield put({type: 'fetch'});
    },
    *signIn({ payload: user , onComplete}, { call, put }) {
      const data = yield call(userService.signIn, user);

      console.log(data.result);
      if (data.result === true) {
        yield put({type: 'fetch'});
      }
      //onComplete();

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
    //setup({ dispatch, history }) {
    //  return history.listen(({ pathname, query }) => {
    //    if (pathname === '/users') {
    //      //console.log(query);
    //      dispatch({type: 'fetch', payload: query});
    //    }
    //  });
    //},
  },
};
