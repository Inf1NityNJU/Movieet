import React from 'react';
import {connect} from 'dva';
import {Link} from 'dva/router';

import {Icon} from 'antd';

import {LIKE_SIZE, PREVIEW_REVIEW_SIZE} from '../../constants'


import ReviewList from '../ReviewList/ReviewList';
import MovieListSmall from '../MovieList/MovieListSmall';
import MovieScoreChart from '../Movie/MovieScoreChart';

import styles from './MoviePage.css';

function MovieInfoPage({movie, reviews, similarMovies, user}) {
  return (
    <div className={styles.normal}>
      <div className="container">

        {movie ?
          <div className={styles.part}>
            <div className={styles.title}>
              <h3>Storyline</h3>
            </div>
            <p className={styles.storyline}>
              {movie.plot}
            </p>
          </div> : null
        }

        {/*
         <div className={styles.part}>
         <div className={styles.title}>
         <h3>Reviews</h3>
         <Link className={styles.title_right} to="/movie/1/review">
         More<Icon type="double-right"/>
         </Link>
         </div>
         <ReviewList num={PREVIEW_REVIEW_SIZE}/>
         </div>
         */}
        {movie ?
        <div className={styles.part}>
          <div className={styles.title}>
            <h3>IMDB Score Distribution</h3>
          </div>
          <MovieScoreChart
            movie={movie}
          />
        </div> : null
        }
      </div>
      { similarMovies && similarMovies.length > 0 ?
        <div className="background">
          <div className="container">
            <div className={styles.part}>
              <div className={styles.title}>
                <h3>Similar Movies</h3>
              </div>
              <MovieListSmall
                num={LIKE_SIZE}
                list={similarMovies}
              />
            </div>
          </div>
        </div> : null
      }


    </div>
  )
}

function mapStateToProps(state) {
  const {movie, reviews, similarMovies, user} = state.movie;
  return {
    movie,
    reviews,
    similarMovies,
    user
  };
}

export default connect(mapStateToProps)(MovieInfoPage);
