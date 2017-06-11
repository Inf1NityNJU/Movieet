import * as moviesService from '../services/movies';
import * as peopleService from '../services/people';

import {
    GENRES, MOVIE_SORT, ORDER, CATEGORY_SIZE, SEARCH_STATUS,
    SEARCH_PREVIEW_MOVIE_SIZE, SEARCH_PREVIEW_DIRECTOR_SIZE, SEARCH_PREVIEW_ACTOR_SIZE,
    SEARCH_MOVIE_SIZE, SEARCH_DIRECTOR_SIZE, SEARCH_ACTOR_SIZE
} from '../constants'

export default {
    namespace: 'movies',
    state: {
        discover: {
            newReleased: [],
            recommend: [],
        },
        category: {
            filter: {
                genres: [GENRES[0].id],
            },
            sort: {
                name: MOVIE_SORT[0],
                order: ORDER[1],
            },
            result: {
                movies: [],
            },
            page: null,
            totalCount: null,
        },
        search: {
            keyword: null,
            recent: [],
            status: SEARCH_STATUS[0],
            result: null,
            page: null,
            totalCount: null,
        }
    },
    reducers: {
        saveGenres(state, {payload: genres}) {
            return {
                ...state,
                category: {
                    ...state.category,
                    filter: {
                        genres
                    }
                }
            };
        },
        saveSort(state, {payload: sort}) {
            return {
                ...state,
                category: {
                    ...state.category,
                    sort: sort
                }
            };
        },
        saveKeyword(state, {payload: keyword}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    keyword,
                }
            }
        },
        saveStatus(state, {payload: status}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    status,
                }
            }
        },
        saveSearch(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    result,
                    page,
                    totalCount,
                }
            }
        },
        saveSearchMovies(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    result: {
                        ...state.search.result,
                        movies: result,
                    },
                    page,
                    totalCount,
                }
            }
        },
        saveSearchDirectors(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    result: {
                        ...state.search.result,
                        directors: result,
                    },
                    page,
                    totalCount,
                }
            }
        },
        saveSearchActors(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    result: {
                        ...state.search.result,
                        actors: result,
                    },
                    page,
                    totalCount,
                }
            }
        },
        saveCategoryMovies(state, {payload: {result, page, totalCount}}) {
            return {
                ...state,
                category: {
                    ...state.category,
                    result: {
                        ...state.category.result,
                        movies: result,
                    },
                    page: page,
                    totalCount,
                }
            }
        },
        saveCategoryPage(state, {payload: page}) {
            return {
                ...state,
                category: {
                    ...state.category,
                    page: page,
                }
            }
        },
        saveLatestMovies(state, {payload: newReleased}) {
            return {
                ...state,
                discover: {
                    ...state.discover,
                    newReleased,
                }
            }
        },
        saveRecommendMovies(state, {payload: recommend}) {
            return {
                ...state,
                discover: {
                    ...state.discover,
                    recommend,
                }
            }
        },
        addRecentKeyword(state, {payload: keyword}) {
            const newArray = [keyword, ...state.search.recent.filter(k => k !== keyword)];
            return {
                ...state,
                search: {
                    ...state.search,
                    recent: newArray,
                }
            }
        },
        saveSearchPage(state, {payload: page}) {
            return {
                ...state,
                search: {
                    ...state.search,
                    page: page,
                }
            }
        },
    },
    effects: {
        *changeGenres({payload: {genres}}, {call, put}) {
            //const sort = yield select(state => state.movies.category.sort);
            console.log(genres);
            yield put({
                type: 'saveGenres',
                payload: genres,
            });
            yield put({
                type: 'fetchMoviesByCategory',
                payload: {}
            });
        },
        *changeSort({payload: {name, order}}, {call, put}) {
            //const category = yield select(state => state.movies.category);
            console.log(name + ' ' + order);
            yield put({
                type: 'saveSort',
                payload: {
                    name,
                    order,
                }
            });
            yield put({
                type: 'fetchMoviesByCategory',
                payload: {}
            });
        },
        *changeCategoryPage({payload: page}, {call, put}){
            console.log(page);
            yield put({
                type: 'saveCategoryPage',
                payload: page,
            });
            yield put({
                type: 'fetchMoviesByCategory',
                payload: {
                    page,
                }
            });
        },
        fetchMoviesByCategory: [
            function*({payload: {size = CATEGORY_SIZE, page = 1}}, {call, put, select}) {
                const category = yield select(state => state.movies.category);
                const {data} = yield call(moviesService.fetchMoviesByGenre, category.filter.genres, category.sort.name, category.sort.order, size, page);
                console.log('category');
                console.log(data);

                yield put({
                    type: 'saveCategoryMovies',
                    payload: data,
                });
            },
            {type: 'takeLatest'}
        ],

        *changeSearchStatus({payload: status}, {call, put}) {
            yield put({
                type: 'saveStatus',
                payload: status
            });
            yield put({
                type: 'saveSearchPage',
                payload: 1,
            });
            yield put({
                type: 'searchKeyword',
                payload: {}
            });
        },
        *changeSearchKeyword({payload: keyword}, {put}) {
            yield put({
                type: 'saveKeyword',
                payload: keyword,
            });
            yield put({
                type: 'saveSearchPage',
                payload: 1,
            });
            yield put({
                type: 'searchKeyword',
                payload: {}
            });
        },
        *changeSearchPage({payload: page}, {call, put}){
            console.log(page);
            yield put({
                type: 'saveSearchPage',
                payload: page,
            });
            yield put({
                type: 'searchKeyword',
                payload: {}
            });
        },
        searchKeyword: [
            function*(action, {call, put, select}) {

                const keyword = yield select(state => state.movies.search.keyword);

                if (keyword === '') {
                    yield put({
                        type: 'saveSearch',
                        payload: {
                            result: null
                        }
                    });
                    return;
                }

                yield put({
                    type: 'addRecentKeyword',
                    payload: keyword,
                });

                const status = yield select(state => state.movies.search.status);
                let page = yield select(state => state.movies.search.page);
                page = page ? page : 1;

                console.log('status: ' + status + ' page: ' + page);

                switch (status) {
                    case 'All':
                        yield put({
                            type: 'fetchMoviesByKeyword',
                            payload: {
                                keyword,
                                size: SEARCH_PREVIEW_MOVIE_SIZE,
                                page,
                            }
                        });
                        yield put({
                            type: 'fetchDirectorsByKeyword',
                            payload: {
                                keyword,
                                size: SEARCH_PREVIEW_DIRECTOR_SIZE,
                                page,
                            }
                        });
                        yield put({
                            type: 'fetchActorsByKeyword',
                            payload: {
                                keyword,
                                size: SEARCH_PREVIEW_ACTOR_SIZE,
                                page,
                            }
                        });
                        break;
                    case 'Movie':
                        yield put({
                            type: 'fetchMoviesByKeyword',
                            payload: {
                                keyword,
                                size: SEARCH_MOVIE_SIZE,
                                page,
                            }
                        });
                        break;
                    case 'Director':
                        yield put({
                            type: 'fetchDirectorsByKeyword',
                            payload: {
                                keyword,
                                size: SEARCH_DIRECTOR_SIZE,
                                page,
                            }
                        });
                        break;
                    case 'Actor':
                        yield put({
                            type: 'fetchActorsByKeyword',
                            payload: {
                                keyword,
                                size: SEARCH_ACTOR_SIZE,
                                page,
                            }
                        });
                        break;
                }
            },
            {type: 'takeLatest'}
        ],
        fetchMoviesByKeyword: [
            function*({payload: {keyword, size, page}}, {call, put}) {
                const {data} = yield call(moviesService.fetchMoviesByKeyword, keyword, size, page);
                console.log('search movie ' + size);
                console.log(data);
                yield put({
                    type: 'saveSearchMovies',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        fetchDirectorsByKeyword: [
            function*({payload: {keyword, size, page}}, {call, put}) {
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

        fetchLatestMovies: [
            function*(action, {call, put}) {
                const {data} = yield call(moviesService.fetchLatestMovies);
                console.log("latest");
                console.log(data);
                yield put({
                    type: 'saveLatestMovies',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
        fetchRecommendMovies: [
            function*(action, {call, put}) {
                if (localStorage.getItem('token') === null) {
                    return;
                }
                const {data} = yield call(moviesService.fetchRecommendMovies);
                console.log("recommend");
                console.log(data);
                yield put({
                    type: 'saveRecommendMovies',
                    payload: data
                });
            },
            {type: 'takeLatest'}
        ],
    },
    subscriptions: {
        setup({dispatch, history}) {
            return history.listen(({pathname, query}) => {
                if (pathname === '/movies/discover') {
                    dispatch({type: 'fetchLatestMovies', payload: {}});
                    dispatch({type: 'fetchRecommendMovies', payload: {}});
                } else if (pathname === '/movies/category') {
                    dispatch({type: 'fetchMoviesByCategory', payload: {}});
                }
            });
        },
    },
};
