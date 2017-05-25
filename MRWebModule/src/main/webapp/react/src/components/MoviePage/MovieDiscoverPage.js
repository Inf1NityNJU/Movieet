import React from 'react';
import {connect} from 'dva';
import {Spin} from 'antd';

import {NEW_RELEASED_SIZE, RECOMMEND_SIZE} from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';
import MovieListLarge from '../MovieList/MovieListLarge';

import styles from './MoviePage.css';

function MovieDiscoverPage({newReleased, recommend, newReleasedLoading, recommendLoading}) {
  return (
    <div className={styles.discover_page}>

      <div className={styles.part}>
        <div className={styles.title}>
          <h3>New Released</h3>
        </div>
        {newReleasedLoading ?
          <div className={styles.spin}>
            <Spin/>
          </div> : null
        }
        {!newReleasedLoading && newReleased && newReleased.length > 0 ?
          <MovieListSmall
            num={NEW_RELEASED_SIZE}
            list={newReleased}
          /> : null
        }
      </div>

      <div className={styles.part}>
        <div className={styles.title}>
          <h3>Recommend</h3>
        </div>
        {recommendLoading ?
          <div className={styles.spin}>
            <Spin/>
          </div> : null
        }
        {!recommendLoading && recommend && recommend.length > 0 ?
          <MovieListLarge
            num={RECOMMEND_SIZE}
            list={recommend}
          /> : null
        }
      </div>


    </div>
  )
}

function mapStateToProps(state) {
  const {discover} = state.movies;
  return {
    newReleased: discover.newReleased,
    recommend: discover.recommend,
    newReleasedLoading: state.loading.effects['movies/fetchLatestMovies'],
    recommendLoading: state.loading.effects['movies/fetchRecommendMovies']
  };
}

export default connect(mapStateToProps)(MovieDiscoverPage);
