import React from 'react';
import { connect } from 'dva';

import { NEW_RELEASED_SIZE, RECOMMEND_SIZE } from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';
import MovieListLarge from '../MovieList/MovieListLarge';

import styles from './MoviePage.css';

function MovieDiscoverPage({ newReleased, recommend }) {
  return (
    <div className={styles.discover_page}>
      { newReleased && newReleased.length > 0 ?
        <div className={styles.part}>
          <div className={styles.title}>
            <h3>New Released</h3>
          </div>
          <MovieListSmall
            num={NEW_RELEASED_SIZE}
            list={newReleased}
          />
        </div> : null
      }
      {recommend && recommend.length > 0 ?
        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Recommend</h3>
          </div>
          <MovieListLarge
            num={RECOMMEND_SIZE}
            list={recommend}/>
        </div> : null
      }

    </div>
  )
}

function mapStateToProps(state) {
  const { discover } = state.movies;
  return {
    newReleased: discover.newReleased,
    recommend: discover.recommend,
  };
}

export default connect(mapStateToProps)(MovieDiscoverPage);
