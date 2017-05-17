import React, { Component } from 'react';
import { Card, Rate } from 'antd';
import styles from './MovieCard.css';

import example from '../../assets/img/example.png';

function MovieCardSmall({ movie, onClick }) {

  return (
    <Card className={styles.card + ' ' + styles.card_small}
          onClick={onClick}>
      <div className={styles.img_wrapper}>
        <div className={styles.img} style={{ backgroundImage: `url(${example})`}}></div>
      </div>
      <div className={styles.text}>
        <h3>{movie.title}</h3>
        <Rate className={styles.rate} disabled allowHalf defaultValue={movie.rank/2}/>
        <span className={styles.score}>{movie.rank}</span>
      </div>
    </Card>
  );
}

export default MovieCardSmall;
