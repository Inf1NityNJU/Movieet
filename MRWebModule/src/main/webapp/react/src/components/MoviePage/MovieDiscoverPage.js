import React from 'react';

import MovieListSmall from '../MovieList/MovieListSmall';
import MovieListLarge from '../MovieList/MovieListLarge';

import styles from './MoviePage.css';

function MovieDiscoverPage() {
  return (
    <div>
      <div className={styles.part}>
        <h3 className={styles.title}>New Released</h3>
        <MovieListSmall num={4} />
      </div>
      <div className={styles.part}>
        <h3 className={styles.title}>Recommend</h3>
        <MovieListLarge num={4} />
      </div>
    </div>
  )
}

export default MovieDiscoverPage;
