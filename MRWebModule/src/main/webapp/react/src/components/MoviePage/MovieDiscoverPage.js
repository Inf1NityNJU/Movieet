import React from 'react';

import { NEW_RELEASED_SIZE, RECOMMEND_SIZE } from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';
import MovieListLarge from '../MovieList/MovieListLarge';

import styles from './MoviePage.css';

function MovieDiscoverPage() {
  return (
    <div>
      <div className={styles.part}>
        <h3 className={styles.title}>New Released</h3>
        <MovieListSmall num={NEW_RELEASED_SIZE} />
      </div>
      <div className={styles.part}>
        <h3 className={styles.title}>Recommend</h3>
        <MovieListLarge num={RECOMMEND_SIZE} />
      </div>
    </div>
  )
}

export default MovieDiscoverPage;
