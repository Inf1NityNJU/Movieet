import * as userService from '../services/user';

import {
  PREVIEW_COLLECT_SIZE, PREVIEW_EVALUATE_SIZE,
  COLLECT_SIZE, EVALUATE_SIZE,
  PREVIEW_FRIEND_SIZE, FRIEND_SIZE
} from '../constants'

export default {
  namespace: 'user',
  state: {
    surveyStatus: false,
    currentUser: null,
    user: null,
    userFollow: null,
    movie: {
      status: null,
      result: {
        collect: [],
        evaluate: [],
      },
      page: null,
      totalCount: null,
    },
    friend: {
      status: null,
      result: {
        following: [],
        follower: [],
      },
      page: null,
      totalCount: null,
    }
  },
  reducers: {
    saveSurveyStatus(state, {payload: surveyStatus}) {
      return {
        ...state,
        surveyStatus
      }
    },
    saveCurrentUser(state, {payload: currentUser}) {
      return {...state, currentUser};
    },
    saveUser(state, {payload: user}) {
      return {...state, user};
    },
    saveUserFollow(state, {payload: userFollow}) {
      return {...state, userFollow};
    },
    saveMovieStatus(state, {payload: status}) {
      return {
        ...state,
        movie: {
          ...state.movie,
          status
        }
      };
    },
    saveMoviePage(state, {payload: page}) {
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
    saveFriendStatus(state, {payload: status}) {
      return {
        ...state,
        friend: {
          ...state.friend,
          status
        }
      };
    },
    saveFriendPage(state, {payload: page}) {
      return {
        ...state,
        friend: {
          ...state.friend,
          page: page,
        }
      }
    },
    saveFollowing(state, {payload: following}) {
      return {
        ...state,
        friend: {
          ...state.friend,
          result: {
            ...state.friend.result,
            following: following.result,
          },
          page: following.page,
          totalCount: following.totalCount,
        }
      };
    },
    saveFollower(state, {payload: follower}) {
      return {
        ...state,
        friend: {
          ...state.friend,
          result: {
            ...state.friend.result,
            follower: follower.result,
          },
          page: follower.page,
          totalCount: follower.totalCount,
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
          type: 'fetchCurrent',
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
    *fetchCurrent({onComplete}, {call, put, select}) {
      const {data} = yield call(userService.fetch);
      yield put({
        type: 'saveCurrentUser',
        payload: data,
      });

      if (onComplete) {
        onComplete();

        let {currentUser} = yield select(state => state.user);
        console.log('currentUser', currentUser);
      }

    },
    *fetchUser({ payload:id, onComplete }, {call, put, select}) {
      const {data} = yield call(userService.fetchUser, id);
      yield put({
        type: 'saveUser',
        payload: data,
      });
      if (onComplete) {
        onComplete();

        let {user} = yield select(state => state.user);
        console.log('user', user);
      }
    },

    *signUp({payload: user, onSuccess, onError}, {call, put}) {
      const result = yield call(userService.signUp, user);

      // //onComplete();
      // yield put({
      //   type: 'signIn',
      //   payload: {
      //     user,
      //   },
      //   onSuccess,
      //   onError,
      // });

      const {data} = yield call(userService.signIn, user);
      console.log(data);
      if (data.result !== undefined) {
        localStorage.setItem('token', data.result);
        yield put({type: 'fetchCurrent'});
        onSuccess(user.username);
      } else {
        onError(data.message.split(': ')[1]);
      }


      yield put({
        type: 'saveSurveyStatus',
        payload: true,
      });
    },
    *signIn({payload: user, onSuccess, onError}, {call, put}) {
      const {data} = yield call(userService.signIn, user);
      console.log(data);
      if (data.result !== undefined) {
        localStorage.setItem('token', data.result);
        yield put({type: 'fetchCurrent'});
        onSuccess(user.username);
      } else {
        onError(data.message.split(': ')[1]);
      }
    },
    *signOut({onSuccess}, {call, put}) {
      yield call(userService.signOut);
      //onComplete();
      yield put({
        type: 'saveCurrentUser',
        payload: null,
      });
      onSuccess();
    },
    *postUserSurvey({payload: survey}, {call, put, select}) {
      const {currentUser} = yield select(state => state.user);
      if (currentUser === null) {
        return;
      }
      const {data} = yield call(userService.postUserSurvey, survey);
      console.log('survey', data);
      yield put({
        type: 'saveSurveyStatus',
        payload: false
      })
    },
    *changeMovieStatus({payload: status}, {put}) {
      console.log('status: ' + status);

      yield put({
        type: 'saveMovieStatus',
        payload: status
      });
      yield put({
        type: 'saveMoviePage',
        payload: 1,
      });
      yield put({
        type: 'fetchUserMovies',
        payload: {}
      });

    },
    *changeMoviePage({payload: page}, {call, put}){
      console.log(page);
      yield put({
        type: 'saveMoviePage',
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
    *changeFriendStatus({payload: status}, {put}) {
      console.log('status: ' + status);

      yield put({
        type: 'saveFriendStatus',
        payload: status
      });
      yield put({
        type: 'saveFriendPage',
        payload: 1,
      });
      yield put({
        type: 'fetchUserFriends',
        payload: {}
      });

    },
    *changeFriendPage({payload: page}, {call, put}){
      console.log(page);
      yield put({
        type: 'saveFriendPage',
        payload: page,
      });
      yield put({
        type: 'fetchUserFriends',
        payload: {}
      });
    },
    *fetchUserFriends(action, {put, select}){

      const status = yield select(state => state.user.friend.status);
      let page = yield select(state => state.user.friend.page);
      page = page ? page : 1;

      switch (status) {
        case 'following':
          yield put({
            type: 'fetchUserFollowing',
            payload: {
              size: FRIEND_SIZE,
              page,
            }
          });
          break;
        case 'follower':
          yield put({
            type: 'fetchUserFollower',
            payload: {
              size: FRIEND_SIZE,
              page,
            }
          });
          break;
        default:
          yield put({
            type: 'fetchUserFollowing',
            payload: {
              size: PREVIEW_FRIEND_SIZE,
              page,
            }
          });
          yield put({
            type: 'fetchUserFollower',
            payload: {
              size: PREVIEW_FRIEND_SIZE,
              page,
            }
          });
          break;
      }


    },
    fetchUserFollowing: [
      function*({payload: {size, page}},{call, put, select}) {
        const {user} = yield select(state => state.user);
        if (user === null) {
          return;
        }
        const {data} = yield call(userService.fetchUserFollowing, user.id, size, page);
        console.log('following ' + size);
        console.log(data);
        yield put({
          type: 'saveFollowing',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ],
    fetchUserFollower: [
      function*({payload: {size, page}},{call, put, select}) {
        const {user} = yield select(state => state.user);
        if (user === null) {
          return;
        }
        const {data} = yield call(userService.fetchUserFollower, user.id, size, page);
        console.log('follower ' + size);
        console.log(data);
        yield put({
          type: 'saveFollower',
          payload: data
        });
      },
      {type: 'takeLatest'}
    ],
    *fetchUserFollow({ payload:id }, {call, put, select}) {
      const {user, currentUser} = yield select(state => state.user);
      if (user === null || currentUser === null) {
        return;
      }

      const {data} = yield call(userService.fetchUserFollow, id);
      console.log('follow status', data.status);
      yield put({
        type: 'saveUserFollow',
        payload: data.status,
      });
    },
  },

  subscriptions: {
    setup({dispatch, history}) {
      return history.listen(({pathname, query}) => {
        dispatch({
          type: 'refresh',
        });


        let array = pathname.split('/');
        console.log('pathname', array);
        // if /user/:id
        if ( array[1] === 'user' ) {
          dispatch({
            type: 'fetchUser',
            payload: array[2],

            onComplete: () => {

              dispatch({
                type:'fetchUserFollow',
                payload: array[2],
              });
              //movie
              switch (array[3]) {
                case 'movie':
                  if (array.length === 4) {
                    dispatch({
                      type: 'changeMovieStatus',
                      payload: null
                    });

                  } else if (array.length === 5) {
                    let status = array[4];

                    dispatch({
                      type: 'changeMovieStatus',
                      payload: status
                    });
                  }
                  break;
                case 'friend':
                  if (array.length === 4) {
                    dispatch({
                      type: 'changeFriendStatus',
                      payload: null
                    });

                  } else if (array.length === 5) {
                    let status = array[4];

                    dispatch({
                      type: 'changeFriendStatus',
                      payload: status
                    });
                  }
                  break;
              }

            }
          });
        }
        //end if


      });
    },
  },
};
