import React from 'react';
import { connect } from 'dva';
import { Icon, Pagination } from 'antd';


import { SEARCH_PREVIEW_MOVIE_SIZE, SEARCH_MOVIE_SIZE, SEARCH_STATUS } from '../../constants'

import MovieSearchInput from '../Movies/MovieSearchInput'
import MovieListLarge from '../MovieList/MovieListLarge';
import RecentSearch from '../Movies/RecentSearch';

import styles from './MoviePage.css';

function MovieSearchPage({ dispatch, keyword, recent, status, result, page, totalCount }) {

  function onRecentClick(keyword) {
    dispatch({
      type: 'movies/fetchMoviesByKeyword',
      payload: {
        keyword,
        size: SEARCH_MOVIE_SIZE,
        page,
      }
    })
  }

  function onInputChange(keyword) {
    //console.log(keyword);
    dispatch({
      type: 'movies/fetchMoviesByKeyword',
      payload: {
        keyword,
        size: SEARCH_MOVIE_SIZE,
        page: 1,
      }
    });
  }

  function onMoreClick(status) {
    console.log(status);
    dispatch({
      type: 'movies/saveStatus',
      payload: status
    })
  }

  function onPageChange(pageNumber) {

  }


  return (
    <div className={styles.search_page}>
      <div className={styles.part}>
        <MovieSearchInput
          keyword={keyword}
          onChange={onInputChange}/>
      </div>

      <div className={styles.recent_search}>
        <RecentSearch
          recent={recent}
          onClick={onRecentClick}/>
      </div>


      <div className={styles.part}>
        <div className={styles.title}>
          <h3>Movie</h3>
          {status === SEARCH_STATUS[0] ?
            <a className={styles.title_right} onClick={() => onMoreClick("Movies")}>
              More<Icon type="double-right"/>
            </a> :
            null
          }
        </div>
        <MovieListLarge num={ status === 'Movies' ? SEARCH_MOVIE_SIZE : SEARCH_PREVIEW_MOVIE_SIZE}/>
        { status === SEARCH_STATUS[0] ? null :
          <Pagination
            className={styles.page}
            showQuickJumper
            defaultCurrent={1}
            pageSize={ SEARCH_MOVIE_SIZE }
            total={100}
            onChange={onPageChange}/>
        }
      </div>

    </div>
  );
}


function mapStateToProps(state) {
  const { search } = state.movies;
  return {
    keyword: search.keyword,
    recent: search.recent,
    status: search.status,
    result: search.result,
    page: search.page,
    totalCount: search.totalCount,
  };
}

export default connect(mapStateToProps)(MovieSearchPage);
