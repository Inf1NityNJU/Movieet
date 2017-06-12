import * as movieService from '../services/movie';

import {USER_MOVIE_STATUS} from '../constants'

export default {
    namespace: 'movie',
    state: {
        movie: {},
        reviews: [],
        similarMovies: [],
        user: {
            status: null
        },
        evaluateMovies: null,
    },
    reducers: {
        saveMovie(state, {payload: movie}) {
            return {
                ...state,
                movie,
            }
        },
        saveUserMovie(state, {payload: user}) {
            return {
                ...state,
                user,
            }
        },
        saveSimilarMovie(state, {payload: similarMovies}) {
            return {
                ...state,
                similarMovies,
            }
        },
        saveEvaluateMovie(state, {payload: evaluateMovies}) {
            return {
                ...state,
                evaluateMovies,
            }
        },
    },
    effects: {
        *fetchMovie({payload: id}, {call, put, select}) {
            yield put({
                type: 'saveMovie',
                payload: null,
            });


            const {currentUser} = yield select(state => state.user);
            if (currentUser && currentUser.id) {
                yield call(movieService.browseMovie, currentUser.id, id);
            }

            const {data} = yield call(movieService.fetchMovie, id);
            console.log("movie");
            console.log(data);
            yield put({
                type: 'saveMovie',
                payload: data,
            });
            yield put({
                type: 'saveEvaluateMovies',
                payload: null,
            });
        },
        *fetchUserMovie({payload: id}, {call, put}) {
            if (localStorage.getItem('token') === null) {
                return;
            }
            yield put({
                type: 'saveUserMovie',
                payload: null,
            });
            const {data} = yield call(movieService.fetchUserMovie, id);
            console.log("user status");
            console.log(data);
            yield put({
                type: 'saveUserMovie',
                payload: data,
            });
        },
        *fetchSimilarMovies({payload: id}, {call, put}) {
            yield put({
                type: 'saveSimilarMovie',
                payload: null,
            });
            const {data} = yield call(movieService.fetchSimilarMovie, id);
            console.log("similar");
            console.log(data);
            yield put({
                type: 'saveSimilarMovie',
                payload: data,
            });
        },
        *collectMovie({payload: id}, {call, put}) {
            console.log(id);
            const {data} = yield call(movieService.collectMovie, id);
            console.log("collect");
            console.log(data);
            yield put({
                type: 'fetchUserMovie',
                payload: id,
            })
        },
        *removeCollectMovie({payload: id}, {call, put}) {
            console.log(id);
            const {data} = yield call(movieService.removeCollectMovie, id);
            console.log("remove collect");
            console.log(data);
            yield put({
                type: 'fetchUserMovie',
                payload: id,
            })
        },
        *evaluateMovie({payload: {id, evaluate}, onComplete}, {call, put}) {
            console.log(id);
            console.log(evaluate);
            const {data} = yield call(movieService.evaluateMovie, id, evaluate);
            console.log("evaluate");
            console.log(data);
            yield put({
                type: 'fetchUserMovie',
                payload: id,
            });
            yield put({
                type: 'saveEvaluateMovie',
                payload: data.recommend.result,
            });
            onComplete();
        },
        *removeEvaluateMovie({payload: id}, {call, put}) {
            console.log(id);
            const {data} = yield call(movieService.removeEvaluateMovie, id);
            console.log("remove evaluate");
            console.log(data);
            yield put({
                type: 'fetchUserMovie',
                payload: id,
            })
        },
    },
    subscriptions: {
        setup({dispatch, history}) {
            return history.listen(({pathname, query}) => {

                if (pathname.indexOf('/movie/') === 0) {
                    let id = pathname.split('/movie/')[1];
                    //console.log(pathname.split('/movie/'));
                    window.scrollTo(0, 0);
                    dispatch({type: 'fetchMovie', payload: id});
                    dispatch({type: 'fetchUserMovie', payload: id});
                    dispatch({type: 'fetchSimilarMovies', payload: id});
                }
            });
        },
    },
};
