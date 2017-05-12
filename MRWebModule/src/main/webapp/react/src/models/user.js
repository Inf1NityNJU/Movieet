import * as userService from '../services/user';

export default {
  namespace: 'user',
  state: {
    user: null,
    //token: null,
    //list: [],
    //total: null,
    //page: null,
  },
  reducers: {
    save(state, { payload: user }) {
      return {...state, user};
    },
  },
  effects: {
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
    *signIn({ payload: user , onComplete}, { call, put }) {
      const token = yield call(userService.signIn, user);

      if (token !== null) {
        localStorage.setItem("token", token);
        //console.log("local!!!" + localStorage.getItem('token'));
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
