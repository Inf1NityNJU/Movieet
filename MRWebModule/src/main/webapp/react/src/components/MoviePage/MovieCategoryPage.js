import React from 'react';
import { connect } from 'dva';
import { Pagination } from 'antd';

import { CATEGORY_SIZE } from '../../constants'

import GenreFilter from '../Movies/GenreFilter';
import MovieSort from '../Movies/MovieSort';
import MovieListLarge from '../MovieList/MovieListLarge';

import styles from './MoviePage.css';

function MovieCategoryPage({ dispatch, filter, currentSort }) {

  function onGenresChange(genres) {
    dispatch({
      type: 'movies/changeGenres',
      payload: {
        genres,
        size: CATEGORY_SIZE,
      },
    });
  }

  function onSortChange(name, order) {
    dispatch({
      type: 'movies/saveSort',
      payload: {
        name,
        order,
      }
    })
  }

  function onPageChange(pageNumber) {
    console.log('Page: ', pageNumber);
  }

  return (
    <div className={styles.category_page}>
      <div className={styles.part}>
        <GenreFilter
          className={styles.genre_filter}
          genres={filter.genres}
          onChange={onGenresChange}/>

        <MovieSort
          className={styles.movie_sort}
          currentSort={currentSort}
          onChange={onSortChange}/>
      </div>

      <div className={styles.part}>
        <MovieListLarge num={CATEGORY_SIZE}/>
        <Pagination
          className={styles.page}
          showQuickJumper
          defaultCurrent={1}
          pageSize={ CATEGORY_SIZE }
          total={100}
          onChange={onPageChange}/>
      </div>
    </div>
  );
}

function mapStateToProps(state) {
  const { category } = state.movies;
  return {
    filter: category.filter,
    currentSort: category.sort,
  };
}


export default connect(mapStateToProps)(MovieCategoryPage);
