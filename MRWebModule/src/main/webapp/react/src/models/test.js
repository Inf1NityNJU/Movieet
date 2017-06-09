import * as testService from '../services/test';

export default {
  namespace: 'test',
  state: {
    data: null
  },
  reducers: {
    save(state, {payload: data}) {
      return {
        ...state,
        data,
      }
    }
  },
  effects: {
    *fetch(action, {call, put}){
      const { data } = yield call(testService.estimate);
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
