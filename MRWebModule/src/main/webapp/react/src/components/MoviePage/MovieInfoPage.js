import React from 'react';
import { connect } from 'dva';
import { Link } from 'dva/router';

import { Icon } from 'antd';

import { LIKE_SIZE, PREVIEW_REVIEW_SIZE } from '../../constants'


import ReviewList from '../ReviewList/ReviewList';
import MovieListSmall from '../MovieList/MovieListSmall';

import styles from './MoviePage.css';

function MovieInfoPage({movie, reviews, likeMovies, user }) {
  return (
    <div className={styles.normal}>
      <div className="container">

        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Storyline</h3>
          </div>
          <p className={styles.storyline}>
            {movie.plot}
          </p>
        </div>

        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Reviews</h3>
            <Link className={styles.title_right} to="/movie/1/review">
              More<Icon type="double-right"/>
            </Link>
          </div>
          <ReviewList num={PREVIEW_REVIEW_SIZE}/>
        </div>

      </div>
      {/*
      <div className="background">
        <div className="container">
          <div className={styles.part}>
            <div className={styles.title}>
              <h3>People who liked this also liked</h3>
            </div>
            <MovieListSmall
              num={LIKE_SIZE}
              list={null}
            />
          </div>
        </div>
      </div>
      */}

    </div>
  )
}

function mapStateToProps(state) {
  const { movie, reviews, likeMovies, user } = state.movie;
  return {
    movie,
    reviews,
    likeMovies,
    user
  };
}

export default connect(mapStateToProps)(MovieInfoPage);
