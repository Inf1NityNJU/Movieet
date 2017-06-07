import React, { Component } from 'react';
import { Card, Rate } from 'antd';
import styles from './MovieCard.css';

import example from '../../assets/img/example.png';

function MovieCardSmall({ movie, onClick }) {

  return (
    <Card className={styles.card + ' ' + styles.card_small}
          onClick={onClick}>
      <div className={styles.img_wrapper}>
        <div className={styles.img}
             style={ movie.poster ? { backgroundImage: `url(${movie.poster})`} : {} }
        />
      </div>
      <div className={styles.text}>
        <h3 className={styles.title}>{movie.originTitle}</h3>
        <Rate
          className={styles.rate}
          disabled
          allowHalf
          value={movie.scoreFR%2 > 1 ?
            Math.floor(movie.scoreFR/2) + 0.5 :
            Math.floor(movie.scoreFR/2)}
        />
        <span className={styles.score}>{movie.scoreFR}</span>
      </div>
    </Card>
  );
}

export default MovieCardSmall;
