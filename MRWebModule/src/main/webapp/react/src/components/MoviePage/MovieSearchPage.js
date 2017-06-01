import React from 'react';
import {connect} from 'dva';
import {Icon, Pagination, Spin} from 'antd';


import {
  SEARCH_STATUS,
  SEARCH_PREVIEW_MOVIE_SIZE, SEARCH_MOVIE_SIZE,
  SEARCH_PREVIEW_DIRECTOR_SIZE, SEARCH_DIRECTOR_SIZE,
  SEARCH_PREVIEW_ACTOR_SIZE, SEARCH_ACTOR_SIZE
} from '../../constants'

import RecentSearch from '../Movies/RecentSearch';
import MovieSearchInput from '../Movies/MovieSearchInput'
import MovieListLarge from '../MovieList/MovieListLarge';
import PeopleList from '../PeopleList/PeopleList';


import styles from './MoviePage.css';

function MovieSearchPage({dispatch, keyword, recent, status, result, page, totalCount, movieLoading, directorLoading, actorLoading}) {


  function onInputChange(keyword) {
    //console.log(keyword);
    dispatch({
      type: 'movies/saveKeyword',
      payload: keyword,
    });
  }

  function onInputSubmit(keyword) {
    search(keyword);
  }

  function onRecentClick(keyword) {
    search(keyword);
  }

  function search(keyword) {
    dispatch({
      type: 'movies/changeSearchKeyword',
      payload: keyword,
    })
  }

  function onPageChange(page) {
    dispatch({
      type: 'movies/changeSearchPage',
      payload: page,
    })
  }

  function onStatusClick(status) {
    dispatch({
      type: 'movies/changeSearchStatus',
      payload: status
    })
  }

  function onMoreClick(status) {
    dispatch({
      type: 'movies/changeSearchStatus',
      payload: status
    })
  }

  return (
    <div className={styles.search_page}>
      <div className={styles.part}>
        <MovieSearchInput
          keyword={keyword}
          onChange={onInputChange}
          onEnter={onInputSubmit}
        />
      </div>

      {result ?
        <div>
          <div className={styles.search_status}>
            {SEARCH_STATUS.map(s =>
              <a
                key={s}
                className={styles.status + (status === s ? ' ' + styles.status_active : ' ')}
                onClick={() => onStatusClick(s)}
              >
                {s}
              </a>
            )}
          </div>

          { !movieLoading && (status === 'All' || status === 'Movie') && result.movies && result.movies.length > 0 ?
            <div className={styles.part}>
              <div className={styles.title}>
                <h3>Movie</h3>
                {status === SEARCH_STATUS[0] ?
                  <a className={styles.title_right} onClick={() => onMoreClick("Movie")}>
                    More<Icon type="double-right"/>
                  </a> :
                  null
                }
              </div>
              <MovieListLarge
                num={ status === 'Movie' ? SEARCH_MOVIE_SIZE : SEARCH_PREVIEW_MOVIE_SIZE}
                list={result.movies}
              />
              { status === 'All' ? null :
                <Pagination
                  className={styles.page}
                  showQuickJumper
                  defaultCurrent={1}
                  pageSize={ SEARCH_MOVIE_SIZE }
                  total={totalCount}
                  current={page}
                  onChange={onPageChange}/>
              }
            </div> : null
          }
          { !directorLoading && (status === 'All' || status === 'Director') && result.directors && result.directors.length > 0 ?
            <div className={styles.part}>
              <div className={styles.title}>
                <h3>Director</h3>
                {status === 'All' ?
                  <a className={styles.title_right} onClick={() => onMoreClick("Director")}>
                    More<Icon type="double-right"/>
                  </a> :
                  null
                }
              </div>
              <PeopleList
                type="director"
                num={ status === 'Director' ? SEARCH_DIRECTOR_SIZE : SEARCH_PREVIEW_DIRECTOR_SIZE}
                list={result.directors}
              />
              { status === 'All' ? null :
                <Pagination
                  className={styles.page}
                  showQuickJumper
                  defaultCurrent={1}
                  pageSize={ SEARCH_DIRECTOR_SIZE }
                  total={totalCount}
                  current={page}
                  onChange={onPageChange}/>
              }
            </div> : null
          }
          { !actorLoading && (status === 'All' || status === 'Actor') && result.actors && result.actors.length > 0 ?
            <div className={styles.part}>
              <div className={styles.title}>
                <h3>Actor</h3>
                {status === 'All' ?
                  <a className={styles.title_right} onClick={() => onMoreClick("Actor")}>
                    More<Icon type="double-right"/>
                  </a> :
                  null
                }
              </div>
              <PeopleList
                type="actor"
                num={ status === 'Actor' ? SEARCH_ACTOR_SIZE : SEARCH_PREVIEW_ACTOR_SIZE}
                list={result.actors}
              />
              { status === 'All' ? null :
                <Pagination
                  className={styles.page}
                  showQuickJumper
                  defaultCurrent={1}
                  pageSize={ SEARCH_ACTOR_SIZE }
                  total={totalCount}
                  current={page}
                  onChange={onPageChange}/>
              }
            </div> : null
          }
        </div> :
        <div className={styles.recent_search}>
          <RecentSearch
            recent={recent}
            onClick={onRecentClick}/>
        </div>
      }
      {movieLoading || directorLoading || actorLoading ?
        <div className={styles.spin}>
          <Spin/>
        </div> : null
      }
    </div>
  );
}


function mapStateToProps(state) {
  const {search} = state.movies;
  return {
    keyword: search.keyword,
    recent: search.recent,
    status: search.status,
    result: search.result,
    page: search.page,
    totalCount: search.totalCount,
    movieLoading: state.loading.effects['movies/fetchMoviesByKeyword'],
    directorLoading: state.loading.effects['movies/fetchDirectorsByKeyword'],
    actorLoading: state.loading.effects['movies/fetchActorsByKeyword'],

  };
}

export default connect(mapStateToProps)(MovieSearchPage);
