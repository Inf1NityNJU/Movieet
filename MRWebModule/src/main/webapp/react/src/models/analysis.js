import * as analysisService from '../services/analysis';

import {GENRES, COUNTRY, RANK_MOVIE_SIZE, RANK_PEOPLE_SIZE} from '../constants'

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
            currentGenre: GENRES[1].id,

            genreCount: null,
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
        saveRankDirector(state, {payload: director}){
            return {
                ...state,
                rank: {
                    ...state.rank,
                    director: director.result,
                }
            }
        },
        saveRankActor(state, {payload: actor}){
            return {
                ...state,
                rank: {
                    ...state.rank,
                    actor: actor.result,
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
        saveGenreCount(state, {payload: genreCount}) {
            return {
                ...state,
                data: {
                    ...state.data,
                    genreCount,
                }
            };
        },
        saveGenreInYear(state, {payload: genreInYear}) {
            return {
                ...state,
                data: {
                    ...state.data,
                    genreInYear,
                }
            };
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
        saveGenre(state, {payload: currentGenre}) {
            return {
                ...state,
                data: {
                    ...state.data,
                    currentGenre,
                }
            };
        },
    },
    effects: {
        *fetchRankMovieFR(action, {call, put, select}) {
            const {moviesFR} = yield select(state => state.analysis.rank);
            if (moviesFR && moviesFR.length === RANK_MOVIE_SIZE) {
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
            const {moviesCN} = yield select(state => state.analysis.rank);
            if (moviesCN && moviesCN.length === RANK_MOVIE_SIZE) {
                return;
            }

            const {data} = yield call(analysisService.fetchRankMovieCN);

            console.log('rank movie cn', data);

            yield put({
                type: 'saveRankMovieCN',
                payload: data,
            });
        },
        *fetchRankDirector(action, {call, put, select}) {
            const {director} = yield select(state => state.analysis.rank);
            if (director && director.length === RANK_PEOPLE_SIZE) {
                return;
            }

            const {data} = yield call(analysisService.fetchRankDirector);

            console.log('rank director', data);

            yield put({
                type: 'saveRankDirector',
                payload: data,
            });
        },

        *fetchRankActor(action, {call, put, select}) {
            const {actor} = yield select(state => state.analysis.rank);
            if (actor && actor.length === RANK_PEOPLE_SIZE) {
                return;
            }

            const {data} = yield call(analysisService.fetchRankActor);

            console.log('rank actor', data);

            yield put({
                type: 'saveRankActor',
                payload: data,
            });
        },
        // data
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
        *fetchGenreCount(action, {call, put}) {

            const {data} = yield call(analysisService.fetchGenreCount);

            console.log('genreCount', data);

            yield put({
                type: 'saveGenreCount',
                payload: data,
            });
        },
        fetchGenreInYear: [
            function*({payload: id}, {call, put}) {

                const {data} = yield call(analysisService.fetchGenreInYear, id);

                console.log('genreInYear', data);

                yield put({
                    type: 'saveGenreInYear',
                    payload: data,
                });
            },
            {type: 'takeLatest'}
        ],
        *changeGenre({payload: id}, {call, put}) {
            yield put({
                type: 'saveGenre',
                payload: id,
            });

            yield put({
                type: 'fetchGenreInYear',
                payload: id,
            });
        }
    },
    subscriptions: {
        setup({dispatch, history}) {
            return history.listen(({pathname, query}) => {
                if (pathname === '/analysis/rank') {
                    dispatch({type: 'fetchRankMovieFR'});
                    dispatch({type: 'fetchRankMovieCN'});
                    dispatch({type: 'fetchRankDirector'});
                    dispatch({type: 'fetchRankActor'});

                } else if (pathname === '/analysis/data') {
                    window.scrollTo(0, 0);
                    dispatch({type: 'fetchGenreCount'});
                    dispatch({type: 'fetchGenreInYear', payload: 12});
                    dispatch({type: 'fetchCountryCount'});
                }
            });
        },
    },
};
