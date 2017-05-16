import React, { Component } from 'react';
import { Card, Rate, Tag } from 'antd';
import styles from './MovieCard.css';

import example from '../../assets/img/example.png';

function MovieCardLarge({ movie }) {


  return (
    <Card className={styles.card + ' ' + styles.card_large}
          onClick={() => this.props.onClick()}>
      <div className={styles.img_wrapper}>
        <div className={styles.img} style={{ backgroundImage: `url(${example})`}}></div>
      </div>
      <div className={styles.text}>
        <h3>{movie.title}</h3>
        <div className={styles.tags}>
          {
            movie.genres.map((genre) =>
              <Tag key={genre}>{genre}</Tag>
            )
          }
        </div>
        <div className={styles.bottom}>
          <p className={styles.date}>{movie.year}</p>
          <Rate className={styles.rate} disabled allowHalf defaultValue={3.5}/>
          <span className={styles.score}>{movie.rank}</span>
          <span className={styles.count}>({movie.votes})</span>
        </div>
      </div>
    </Card>
  );

}

export default MovieCardLarge;
