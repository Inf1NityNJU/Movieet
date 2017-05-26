import * as userService from '../services/user';

import {
  PREVIEW_COLLECT_SIZE, PREVIEW_EVALUATE_SIZE,
  COLLECT_SIZE, EVALUATE_SIZE
} from '../constants'

export default {
  namespace: 'user',
  state: {
    user: null,
    movie: {
      status: null,
      result: {
        collect: [],
        evaluate: [],
      },
      page: null,
      totalCount: null,
    }
  },
  reducers: {
    save(state, {payload: user}) {
      return {...state, user};
    },
    saveStatus(state, {payload: status}) {
      return {
        ...state,
        movie: {
          ...state.movie,
          status
        }
      };
    },
    savePage(state, {payload: page}) {
      return {
        ...state,
        movie: {
          ...state.movie,
          page: page,
        }
      }
    },
    saveCollectMovies(state, {payload: collect}) {
      return {
        ...state,
        movie: {
          ...state.movie,
          result: {
            ...state.movie.result,
            collect: collect.result,
          },
          page: collect.page,
          totalCount: collect.totalCount,
        }
      };
    },
    saveEvaluateMovies(state, {payload: evaluate}) {
      return {
        ...state,
        movie: {
          ...state.movie,
          result: {
            ...state.movie.result,
            evaluate: evaluate.result,
          },
          page: evaluate.page,
          totalCount: evaluate.totalCount,
        }
      };
    },
  },
  effects: {
    *refresh({onComplete}, {call, put, select}) {
      const {user} = yield select(state => state.user);
      const token = localStorage.getItem('token');

      console.log("refresh");

      if (token !== null && user === null) {
        yield put({
          type: 'fetch',
          onComplete: () => {
            if (onComplete) {
              console.log("end refresh");

              onComplete();
            }
          }
        });
      } else {
        if (onComplete) {
          console.log("end refresh");

          onComplete();
        }
      }

    },
    *fetch({onComplete}, {call, put, select}) {
      const {data} = yield call(userService.fetch);
      yield put({
        type: 'save',
        payload: data,
      });

      if (onComplete) {
        console.log('end fetch');
        onComplete();

        let {user} = yield select(state => state.user);
        console.log(user);
      }

    },
    *signUp({payload: user, onComplete}, {call, put}) {
      const result = yield call(userService.signUp, user);
      //onComplete();
      yield put({type: 'fetch'});
    },
    *signIn({payload: user, onSuccess, onError}, {call, put}) {
      const {data} = yield call(userService.signIn, user);
      console.log(data);
      if (data.result !== undefined) {
        localStorage.setItem('token', data.result);
        yield put({type: 'fetch'});
        onSuccess(user.username);
      } else {
        onError(data.message.split(': ')[1]);
      }
    },
    *signOut({onSuccess}, {call, put}) {
      yield call(userService.signOut);
      //onComplete();
      yield put({
        type: 'save',
        payload: null,
      });
      onSuccess();
    },
    *changeStatus({payload: status}, {put}) {
      console.log('status: ' + status);

      yield put({
        type: 'saveStatus',
        payload: status
      });
      yield put({
        type: 'savePage',
        payload: 1,
      });
      yield put({
        type: 'fetchUserMovies',
        payload: {}
      });

    },
    *changePage({payload: page}, {call, put}){
      console.log(page);
      yield put({
        type: 'savePage',
        payload: page,
      });
      yield put({
        type: 'fetchUserMovies',
        payload: {}
      });
    },
    *fetchUserMovies(action, {put, select}){

      const status = yield select(state => state.user.movie.status);
      let page = yield select(state => state.user.movie.page);
      page = page ? page : 1;

      switch (status) {
        case 'collect':
          yield put({
            type: 'fetchCollectMovies',
            payload: {
              size: COLLECT_SIZE,
              page,
            }
          });
          break;
        case 'evaluate':
          yield put({
            type: 'fetchEvaluateMovies',
            payload: {
              size: EVALUATE_SIZE,
              page,
            }
          });
          break;
        default:
          yield put({
            type: 'fetchCollectMovies',
            payload: {
              size: PREVIEW_COLLECT_SIZE,
              page,
            }
          });
          yield put({
            type: 'fetchEvaluateMovies',
            payload: {
              size: PREVIEW_EVALUATE_SIZE,
              page,
            }
          });
          break;
      }


    },

    fetchCollectMovies: [
      function*({payload: {size, page}}, {call, put, select}) {
        const {user} = yield select(state => state.user);
        if (user === null) {
          return;
        }
        const {data} = yield call(userService.fetchUserCollectMovies, user.id, size, page);
        console.log('collect movies ' + size);
        console.log(data);
        yield put({
          type: 'saveCollectMovies',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ],
    fetchEvaluateMovies: [
      function*({payload: {size, page}}, {call, put, select}) {
        const {user} = yield select(state => state.user);
        if (user === null) {
          return;
        }
        const {data} = yield call(userService.fetchUserEvaluateMovies, user.id, size, page);
        console.log('evaluate movies ' + size);
        console.log(data);
        yield put({
          type: 'saveEvaluateMovies',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ],
  },
  subscriptions: {
    setup({dispatch, history}) {
      return history.listen(({pathname, query}) => {
        dispatch({
          type: 'refresh',
          onComplete: () => {
            if (pathname === '/user/movie') {
              dispatch({
                type: 'changeStatus',
                payload: null
              });

            } else if (pathname.indexOf('/user/movie/') === 0) {
              let status = pathname.split('/user/movie/')[1];

              dispatch({
                type: 'changeStatus',
                payload: status
              });

            }
          },
        });
      });
    },
  },
};
