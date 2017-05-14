import React from 'react';

import { NEW_RELEASED_SIZE, RECOMMEND_SIZE } from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';
import MovieListLarge from '../MovieList/MovieListLarge';

import styles from './MoviePage.css';

function MovieDiscoverPage() {
  return (
    <div>

      <div className={styles.part}>
        <div className={styles.title}>
          <h3>New Released</h3>
        </div>
        <MovieListSmall num={NEW_RELEASED_SIZE}/>
      </div>

      <div className={styles.part}>
        <div className={styles.title}>
          <h3>Recommend</h3>
        </div>
        <MovieListLarge num={RECOMMEND_SIZE}/>
      </div>

    </div>
  )
}

export default MovieDiscoverPage;
