import * as peopleService from '../services/people';

import {PREDICTION_SEARCH_SIZE} from '../constants';

export default {
  namespace: 'prediction',
  state: {
    keyword: null,
    current: {
      genres: [],
      directors: [],
      actors: [],
      keywords: [],
    },
    search: {
      genres: {
        result: [],
        page: null,
      },
      directors: {
        result: [],
        page: null,
      },
      actors: {
        result: [],
        page: null,
      },
      keywords: {
        result: [],
        page: null,
      },
    }
  },
  reducers: {
    saveKeyword(state, {payload: keyword}) {
      return {
        ...state,
        keyword,
      }
    },
    saveDirectors(state, {payload: {result, page}}) {
      return {
        ...state,
        search: {
          ...state.search,
          directors: {
            result,
            page,
          }
        }
      }
    },
    saveActors(state, {payload: {result, page}}) {
      return {
        ...state,
        search: {
          ...state.search,
          actors: {
            result,
            page,
          }
        }
      }
    },
    addDirectors(state, {payload: {result, page}}) {

      return {
        ...state,
        search: {
          ...state.search,
          directors: {
            result: result.concat(state.search.directors.result),
            page,
          }
        }
      }
    },
    addActors(state, {payload: {result, page}}) {

      return {
        ...state,
        search: {
          ...state.search,
          actors: {
            result: result.concat(state.search.actors.result),
            page,
          }
        }
      }
    },
  },
  effects: {
    *changeKeyword({payload: keyword}, {call, put}) {
      yield put({
        type: 'saveKeyword',
        payload: keyword
      });
      yield put({
        type: 'fetchDirectorsByKeyword',
        payload: {
          keyword,
          page: 1,
          size: PREDICTION_SEARCH_SIZE,
        }
      });
      yield put({
        type: 'fetchActorsByKeyword',
        payload: {
          keyword,
          page: 1,
          size: PREDICTION_SEARCH_SIZE,
        }
      });
    },
    fetchDirectorsByKeyword: [
      function*({payload: {keyword, size, page}}, {call, put}) {
        const {data} = yield call(peopleService.fetchDirectorsByKeyword, keyword, size, page);
        console.log('search director ' + size);
        console.log(data);
        yield put({
          type: 'saveDirectors',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ],
    fetchActorsByKeyword: [
      function*({payload: {keyword, size, page}}, {call, put}) {
        const {data} = yield call(peopleService.fetchActorsByKeyword, keyword, size, page);
        console.log('search actor ' + size);
        console.log(data);
        yield put({
          type: 'saveActors',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ],
    fetchMoreDirectors: [
      function*(action, {call, put, select}) {
        const {keyword} = yield select(state => state.prediction);
        let {page} = yield select(state => state.prediction.search.directors);
        page++;

        const {data} = yield call(peopleService.fetchDirectorsByKeyword, keyword, PREDICTION_SEARCH_SIZE, page);
        console.log('search director more');
        console.log(data);
        yield put({
          type: 'addDirectors',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ],
    fetchMoreActors: [
      function*(action, {call, put, select}) {
        const {keyword} = yield select(state => state.prediction);
        let {page} = yield select(state => state.prediction.search.actors);
        page++;

        const {data} = yield call(peopleService.fetchActorsByKeyword, keyword, PREDICTION_SEARCH_SIZE, page);
        console.log('search actor more');
        console.log(data);
        yield put({
          type: 'addActors',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ]
  },
  subscriptions: {},
};
