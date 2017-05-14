import React from 'react';
import { connect } from 'dva';
import { Icon } from 'antd';


import { SEARCH_MOVIE_SIZE } from '../../constants'

import MovieSearchInput from '../Movies/MovieSearchInput'
import MovieListLarge from '../MovieList/MovieListLarge';
import RecentSearch from '../Movies/RecentSearch';

import styles from './MoviePage.css';

function MovieSearchPage({ dispatch, keyword, recent }) {
  function onRecentClick(keyword) {
    dispatch({
      type: 'movies/saveKeyword',
      payload: keyword
    })
  }

  function onInputChange(keyword) {
    dispatch({
      type: 'movies/saveKeyword',
      payload: keyword
    })
  }

  return (
    <div>
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
          <a className={styles.title_right}>More<Icon type="double-right"/></a>
        </div>
        <MovieListLarge num={SEARCH_MOVIE_SIZE}/>
      </div>

    </div>
  );
}


function mapStateToProps(state) {
  const { search } = state.movies;
  return {
    keyword: search.keyword,
    recent: search.recent,
  };
}

export default connect(mapStateToProps)(MovieSearchPage);
