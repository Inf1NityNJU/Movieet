import * as testService from '../services/test';

export default {
  namespace: 'test',
  state: {
    language: null
  },
  reducers: {
    save(state, {payload: language}) {
      return {
        ...state,
        language,
      }
    }
  },
  effects: {
    *fetch(action, {call, put}){
      const { data } = yield call(testService.fetch);
      console.log(data);
      yield put({
        type: 'save',
        payload: data,
      })
    }
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {

        if (pathname === '/test') {
          dispatch({type: 'fetch', payload: {}});
        }
      });
    },
  },
};
