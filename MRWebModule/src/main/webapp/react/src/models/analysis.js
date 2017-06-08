import * as analysisService from '../services/analysis';

import {COUNTRY} from '../constants'

export default {
    namespace: 'analysis',
    state: {
        rank: {
            moviesFR: [],
            moviesCN: [],
            director: [],
            actor: [],
            status: {
                moreFR: false,
                moreCN: false,
            }
        },
        data: {
            quantityInGenre: [],
            genreQuantityScoreInYear: [],
            countryScoreInYear: null,
            countryCount: null,
        }
    },
    reducers: {
        saveRankMovieFR(state, {payload: moviesFR}){
            return {
                ...state,
                rank: {
                    ...state.rank,
                    moviesFR: moviesFR.result,
                }
            }
        },
        saveRankMovieCN(state, {payload: moviesCN}){
            return {
                ...state,
                rank: {
                    ...state.rank,
                    moviesCN: moviesCN.result,
                }
            }
        },
        saveMoreFR(state, {payload: moreFR}) {
            return {
                ...state,
                rank: {
                    ...state.rank,
                    status: {
                        ...state.rank.status,
                        moreFR
                    },
                },
            }
        },
        saveMoreCN(state, {payload: moreCN}) {
            return {
                ...state,
                rank: {
                    ...state.rank,
                    status: {
                        ...state.rank.status,
                        moreCN,
                    },
                },
            }
        },
        saveCountryScoreInYear(state, {payload: countryScoreInYear}) {
            return {
                ...state,
                data: {
                    ...state.data,
                    countryScoreInYear,
                }
            };
        },
        saveCountryCount(state, {payload: countryCount}) {
            return {
                ...state,
                data: {
                    ...state.data,
                    countryCount,
                }
            };
        },
        saveGenreQuantityScoreInYear(state, {payload: genreQuantityScoreInYear}) {
            return {...state, genreQuantityScoreInYear};
        },
    },
    effects: {
        *fetchRankMovieFR(action, {call, put, select}) {
            const{moviesFR} = yield select(state => state.analysis.rank);
            if (moviesFR && moviesFR.length === 50) {
                return;
            }
            const {data} = yield call(analysisService.fetchRankMovieFR);

            console.log('rank movie fr', data);

            yield put({
                type: 'saveRankMovieFR',
                payload: data,
            });
        },
        *fetchRankMovieCN(action, {call, put, select}) {
            const{moviesCN} = yield select(state => state.analysis.rank);
            if (moviesCN && moviesCN.length === 50) {
                return;
            }

            const {data} = yield call(analysisService.fetchRankMovieCN);

            console.log('rank movie cn', data);

            yield put({
                type: 'saveRankMovieCN',
                payload: data,
            });
        },
        *fetchCountryScoreInYear(action, {call, put}) {

            const countries = COUNTRY.map(country => country.id);

            const {data} = yield call(analysisService.fetchCountryScoreInYear, countries);

            console.log('countryScoreInYear', data);

            yield put({
                type: 'saveCountryScoreInYear',
                payload: data,
            });
        },
        *fetchCountryCount(action, {call, put}) {

            const countries = COUNTRY.map(country => country.id);

            const {data} = yield call(analysisService.fetchCountryCount, countries);

            console.log('countryCount', data);

            yield put({
                type: 'saveCountryCount',
                payload: data,
            });
        },
        *fetchGenreQuantityScoreInYear(action, {call, put}) {
            const {data} = yield call(analysisService.fetchGenreQuantityScoreInYear);
            yield put({
                type: 'saveGenreQuantityScoreInYear',
                payload: data,
            });
        },
    },
    subscriptions: {
        setup({dispatch, history}) {
            return history.listen(({pathname, query}) => {
                if (pathname === '/analysis/rank') {
                    dispatch({type: 'fetchRankMovieFR'});
                    dispatch({type: 'fetchRankMovieCN'});

                } else if (pathname === '/analysis/data') {
                    // dispatch({type: 'fetchCountryScoreInYear'});
                    // dispatch({type: 'fetchCountryCount'});
                    dispatch({type: 'fetchGenreQuantityScoreInYear'});
                }
            });
        },
    },
};
