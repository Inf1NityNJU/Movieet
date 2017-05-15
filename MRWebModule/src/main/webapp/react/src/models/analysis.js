import * as analysisService from '../services/analysis';

export default {
  namespace: 'analysis',
  state: {
    quantityInGenre: [],
    genreQuantityScoreInYear: [],
  },
  reducers: {
    saveQuantityInGenre(state, { payload: quantityInGenre }) {
      return {...state, quantityInGenre};
    },
    saveGenreQuantityScoreInYear(state, { payload: genreQuantityScoreInYear }) {
      return {...state, genreQuantityScoreInYear};
    },
  },
  effects: {
    *fetchQuantityInGenre(action, { call, put }) {
      const { data } = yield call(analysisService.fetchQuantityInGenre);
      yield put({
        type: 'saveQuantityInGenre',
        payload: data,
      });
    },
    *fetchGenreQuantityScoreInYear(action, { call, put }) {
      const { data } = yield call(analysisService.fetchGenreQuantityScoreInYear);
      yield put({
        type: 'saveGenreQuantityScoreInYear',
        payload: data,
      });
    },
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/analysis') {
          dispatch({type: 'fetchQuantityInGenre'});
          dispatch({type: 'fetchGenreQuantityScoreInYear'});
        }
      });
    },
  },
};
