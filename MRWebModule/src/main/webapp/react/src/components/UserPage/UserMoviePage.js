import React from 'react';
import { Icon } from 'antd';

import { COLLECT_SIZE, EVALUATE_SIZE } from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';

import styles from './UserPage.css';

function UserMoviePage() {
  return (
    <div>

      <div className={styles.part}>
        <div className={styles.title}>
          <h3>Want to watch</h3>
          <a className={styles.title_right}>More<Icon type="double-right"/></a>
        </div>
        <MovieListSmall num={COLLECT_SIZE}/>
      </div>

      <div className={styles.part}>
        <div className={styles.title}>
          <h3>Had watched</h3>
          <a className={styles.title_right}>More<Icon type="double-right"/></a>
        </div>
        <MovieListSmall num={EVALUATE_SIZE}/>
      </div>

    </div>
  )
}

export default UserMoviePage;
