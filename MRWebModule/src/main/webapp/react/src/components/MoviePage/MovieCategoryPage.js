import React from 'react';
import { connect } from 'dva';
import { Pagination, Spin } from 'antd';

import { CATEGORY_SIZE } from '../../constants'

import GenreFilter from '../Movies/GenreFilter';
import MovieSort from '../Movies/MovieSort';
import MovieListLarge from '../MovieList/MovieListLarge';

import styles from './MoviePage.css';

function MovieCategoryPage({ dispatch, loading, filter, currentSort, list, page, totalCount }) {


  function onGenresChange(genres) {
    dispatch({
      type: 'movies/changeGenres',
      payload: {
        genres
      },
    });
  }

  function onSortChange(name, order) {

    dispatch({
      type: 'movies/changeSort',
      payload: {
        name,
        order
      },
    })
  }

  function onPageChange(page) {
    dispatch({
      type: 'movies/changeCategoryPage',
      payload: page,
    });
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

      {loading ?
        <div className={styles.spin}>
          <Spin/>
        </div>: null
      }
      {!loading && list && list.length > 0 ?
        <div className={styles.part}>

          <MovieListLarge num={CATEGORY_SIZE} list={list}/>
          <Pagination
            className={styles.page}
            showQuickJumper
            current={page}
            defaultCurrent={1}
            pageSize={ CATEGORY_SIZE }
            total={totalCount}
            onChange={onPageChange}/>
        </div>
        : null
      }
    </div>
  );
}

function mapStateToProps(state) {
  const { category } = state.movies;
  return {
    loading: state.loading.effects['movies/fetchMoviesByCategory'],
    filter: category.filter,
    currentSort: category.sort,
    list: category.result.movies,
    page: category.page,
    totalCount: category.totalCount
  };
}


export default connect(mapStateToProps)(MovieCategoryPage);
