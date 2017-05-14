import React from 'react';
import { Icon } from 'antd';

import { LIKE_SIZE, PRVIEW_REVIEW_SIZE } from '../../constants'

import MovieBanner from '../Movie/MovieBanner';
import MovieInfo from '../Movie/MovieInfo';
import ReviewList from '../ReviewList/ReviewList';
import MovieListSmall from '../MovieList/MovieListSmall';

import styles from './MoviePage.css';

function MovieInfoPage() {
  return (
    <div className={styles.normal}>
      <MovieBanner/>
      <MovieInfo/>
      <div className="container">

        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Storyline</h3>
          </div>
          <p className={styles.storyline}>Mitsuha is the daughter of the mayor of a small mountain town. She's a straightforward high school girl who lives with her sister and her grandmother and has no qualms about letting it be known that she's uninterested in Shinto rituals or helping her father's electoral campaign. Instead she dreams of leaving the boring town and trying her luck in Tokyo. Taki is a high school boy in Tokyo who works part-time in an Italian restaurant and aspires to become an architect or an artist. Every night he has a strange dream where he becomes...a high school girl in a small mountain town.</p>
        </div>

        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Reviews</h3>
            <a className={styles.title_right}>More<Icon type="double-right"/></a>
          </div>
          <ReviewList num={PRVIEW_REVIEW_SIZE}/>
        </div>

      </div>

      <div className="background">
        <div className="container">
          <div className={styles.part}>
            <div className={styles.title}>
              <h3>People who liked this also liked</h3>
            </div>
            <MovieListSmall num={LIKE_SIZE}/>
          </div>
        </div>
      </div>
    </div>
  )
}

export default MovieInfoPage;
