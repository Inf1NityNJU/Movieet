import * as peopleService from '../services/people';

export default {
  namespace: 'people',
  state: {
    people: null,
  },
  reducers: {
    savePeople(state, {payload: people}) {
      return {
        ...state,
        people,
      }
    }
  },
  effects: {
    *fetchDirector({payload: id}, {put, call}) {
      const {data} = yield call(peopleService.fetchDirector, id);
      console.log('director', data);

      yield put({
        type:'savePeople',
        payload: data,
      })
    },
    *fetchActor({payload: id}, {put, call}) {
      const {data} = yield call(peopleService.fetchActor, id);
      console.log('actor', data);

      yield put({
        type:'savePeople',
        payload: data,
      })
    },
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {

        if (pathname.indexOf('/director/') === 0) {
          let id = pathname.split('/director/')[1];
          // console.log(pathname.split('/director/'));

          window.scrollTo(0, 0);
          dispatch({type: 'fetchDirector', payload: id});

        } else if (pathname.indexOf('/actor/') === 0) {
          let id = pathname.split('/actor/')[1];
          // console.log(pathname.split('/actor/'));

          window.scrollTo(0, 0);
          dispatch({type: 'fetchActor', payload: id});
        }
      });
    },
  },
};
