import * as peopleService from '../services/people';
import * as predictionService from '../services/prediction';

import {GENRES, PREDICTION_SEARCH_SIZE} from '../constants';

export default {
    namespace: 'prediction',
    state: {
        keyword: null,
        current: {
            genres: GENRES.slice(1, 11),
            directors: [],
            actors: [],
            keywords: [],
        },
        search: {
            genres: {
                result: [],
                page: null,
                totalCount: null,
            },
            directors: {
                result: [],
                page: null,
                totalCount: null,
            },
            actors: {
                result: [],
                page: null,
                totalCount: null,
            },
            keywords: {
                result: [],
                page: null,
                totalCount: null,
            },
        },
        predict: null,
        estimate: null,
        estimateStatus: null,
    },
    reducers: {
        saveSearchKeyword(state, {payload: keyword}) {
            return {
                ...state,
                keyword,
            }
        },
        saveSearchGenres(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    genres: {
                        result,
                        page,
                        totalCount,
                    }
                }
            }
        },
        saveSearchDirectors(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    directors: {
                        result,
                        page,
                        totalCount,
                    }
                }
            }
        },
        saveSearchActors(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    actors: {
                        result,
                        page,
                        totalCount,
                    }
                }
            }
        },
        addSearchGenres(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    genres: {
                        result: result.concat(state.search.genres.result),
                        page,
                        totalCount,
                    }
                }
            }
        },
        addSearchDirectors(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    directors: {
                        result: result.concat(state.search.directors.result),
                        page,
                        totalCount,
                    }
                }
            }
        },
        addSearchActors(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    actors: {
                        result: result.concat(state.search.actors.result),
                        page,
                        totalCount,
                    }
                }
            }
        },
        saveCurrentGenres(state, {payload: genres}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    genres,
                }
            }
        },
        addCurrentGenre(state, {payload: genre}) {
            const newArray = [
                {
                    id: genre.id,
                    value: genre.value,
                    checked: true,
                },
                ...state.current.genres.filter(g => g.id !== genre.id)
            ];
            return {
                ...state,
                current: {
                    ...state.current,
                    genres: newArray,
                }
            }
        },
        checkCurrentGenre(state, {payload: {id, checked}}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    genres: state.current.genres.map(genre =>
                        genre.id === id ?
                            {
                                ...genre,
                                checked: checked
                            } : genre
                    ),
                }
            }
        },
        removeCurrentGenre(state, {payload: {id}}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    genres: state.current.genres.filter(genre =>
                        genre.id !== id
                    ),
                }
            }
        },
        saveCurrentDirectors(state, {payload: directors}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    directors,
                }
            }
        },
        addCurrentDirector(state, {payload: director}) {
            const newArray = [
                {
                    id: director.id,
                    value: director.name,
                    photo: director.photo,
                    checked: true,
                },
                ...state.current.directors.filter(d => d.id !== director.id)
            ];
            return {
                ...state,
                current: {
                    ...state.current,
                    directors: newArray,
                }
            }
        },
        checkCurrentDirector(state, {payload: {id, checked}}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    directors: state.current.directors.map(director =>
                        director.id === id ?
                            {
                                ...director,
                                checked: checked
                            } : director
                    ),
                }
            }
        },
        removeCurrentDirector(state, {payload: {id}}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    directors: state.current.directors.filter(director =>
                        director.id !== id
                    ),
                }
            }
        },
        saveCurrentActors(state, {payload: actors}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    actors,
                }
            }
        },
        addCurrentActor(state, {payload: actor}) {
            const newArray = [
                {
                    id: actor.id,
                    value: actor.name,
                    photo: actor.photo,
                    checked: true,
                },
                ...state.current.actors.filter(a => a.id !== actor.id)
            ];
            return {
                ...state,
                current: {
                    ...state.current,
                    actors: newArray,
                }
            }
        },
        checkCurrentActor(state, {payload: {id, checked}}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    actors: state.current.actors.map(actor =>
                        actor.id === id ?
                            {
                                ...actor,
                                checked: checked
                            } : actor
                    ),
                }
            }
        },
        removeCurrentActor(state, {payload: {id}}) {
            return {
                ...state,
                current: {
                    ...state.current,
                    actors: state.current.actors.filter(actor =>
                        actor.id !== id
                    ),
                }
            }
        },
        savePredict(state, {payload: predict}) {
            return {
                ...state,
                predict,
            }
        },
        saveEstimate(state, {payload: estimate}) {
            return {
                ...state,
                estimate,
            }
        },
        saveEstimateStatus(state, {payload: estimateStatus}) {
            return {
                ...state,
                estimateStatus,
            }
        }
    },
    effects: {
        *changeKeyword({payload: keyword}, {call, put}) {


            yield put({
                type: 'saveSearchKeyword',
                payload: keyword
            });


            // fetch
            yield put({
                type: 'fetchGenresByKeyword',
                payload: {
                    keyword,
                    page: 1,
                    size: PREDICTION_SEARCH_SIZE,
                }
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
        fetchGenresByKeyword: [
            function*({payload: {keyword, size, page}}, {call, put}) {

                yield put({
                    type: 'saveSearchGenres',
                    payload: {
                        result: [],
                        page: null,
                        totalCount: null
                    }
                });
                if (keyword === null || keyword === '') {
                    return;
                }

                const {data} = yield call(predictionService.fetchGenresByKeyword, keyword, size, page);
                console.log('search genre ' + size);
                console.log(data);
                yield put({
                    type: 'saveSearchGenres',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        fetchDirectorsByKeyword: [
            function*({payload: {keyword, size, page}}, {call, put}) {

                yield put({
                    type: 'saveSearchDirectors',
                    payload: {
                        result: [],
                        page: null,
                        totalCount: null
                    }
                });
                if (keyword === null || keyword === '') {
                    return;
                }

                const {data} = yield call(peopleService.fetchDirectorsByKeyword, keyword, size, page);
                console.log('search director ' + size);
                console.log(data);
                yield put({
                    type: 'saveSearchDirectors',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        fetchActorsByKeyword: [
            function*({payload: {keyword, size, page}}, {call, put}) {

                yield put({
                    type: 'saveSearchActors',
                    payload: {
                        result: [],
                        page: null,
                        totalCount: null
                    }
                });
                if (keyword === null || keyword === '') {
                    return;
                }

                const {data} = yield call(peopleService.fetchActorsByKeyword, keyword, size, page);
                console.log('search actor ' + size);
                console.log(data);
                yield put({
                    type: 'saveSearchActors',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        fetchMoreGenres: [
            function*(action, {call, put, select}) {
                const {keyword} = yield select(state => state.prediction);
                let {page} = yield select(state => state.prediction.search.genres);
                page++;

                const {data} = yield call(predictionService.fetchGenresByKeyword, keyword, PREDICTION_SEARCH_SIZE, page);
                console.log('search genre more');
                console.log(data);
                yield put({
                    type: 'addSearchGenres',
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
                    type: 'addSearchDirectors',
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
                    type: 'addSearchActors',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        predict: [
            function*(action, {put, call, select}) {
                const {current} = yield select(state => state.prediction);
                const combination = {
                    genre: current.genres.filter(g => g.checked).map(g => g.id),
                    director: current.directors.filter(d => d.checked).map(d => d.id),
                    actor: current.actors.filter(a => a.checked).map(a => a.id),
                };
                const {data} = yield call(predictionService.predict, combination);
                console.log('predict', data);

                yield put({
                    type: 'savePredict',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        estimate: [
            function*(action, {put, call, select}) {
                const {current} = yield select(state => state.prediction);
                const combination = {
                    genre: current.genres.filter(g => g.checked).map(g => g.id),
                    director: current.directors.filter(d => d.checked).map(d => d.id),
                    actor: current.actors.filter(a => a.checked).map(a => a.id),
                };
                const {data} = yield call(predictionService.estimate, combination);
                console.log('estimate', data);

                yield put({
                    type: 'saveEstimate',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        *initCurrentGenres(action, {put}) {
            const genres = GENRES.slice(1, GENRES.length).map(g => {
                return {
                    id: g.id,
                    value: g.value,
                    checked: false,
                }
            });

            yield put({
                type: 'saveCurrentGenres',
                payload: genres
            })
        },
        *initCurrentDirectors(action, {call, put, select}) {
            const {current} = yield select(state => state.prediction);
            if (current.directors && current.directors.length > 0) {
                return;
            }

            const {data} = yield call(predictionService.fetchDefaultDirectors);
            console.log('init director ');
            console.log(data);

            const directors = data.result.map(d => {
                return {
                    id: d.id,
                    value: d.name,
                    photo: d.photo,
                    checked: false,
                }
            });

            yield put({
                type: 'saveCurrentDirectors',
                payload: directors
            })
        },
        *initCurrentActors(action, {call, put, select}) {
            const {current} = yield select(state => state.prediction);
            if (current.actors && current.actors.length > 0) {
                return;
            }

            const {data} = yield call(predictionService.fetchDefaultActors);
            console.log('init actor ');
            console.log(data);

            const actors = data.result.map(a => {
                return {
                    id: a.id,
                    value: a.name,
                    photo: a.photo,
                    checked: false,
                }
            });

            yield put({
                type: 'saveCurrentActors',
                payload: actors
            })
        },

    },
    subscriptions: {
        setup({dispatch, history}) {
            return history.listen(({pathname, query}) => {
                if (pathname === '/prediction') {
                    dispatch({type: 'savePredict', payload: null});
                    dispatch({type: 'saveEstimate', payload: null});
                    dispatch({type: 'initCurrentGenres', payload: {}});
                    dispatch({type: 'initCurrentDirectors', payload: {}});
                    dispatch({type: 'initCurrentActors', payload: {}});
                }
            });
        },
    },
};
