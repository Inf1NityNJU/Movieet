import React, { Component } from 'react';
import { Card, Rate, Tag } from 'antd';
import styles from './MovieCard.css';

import example from '../../assets/img/example.png';

function MovieCardLarge({ movie, onClick }) {


  return (
    <Card className={styles.card + ' ' + styles.card_large}
          onClick={onClick}>
      <div className={styles.img_wrapper}>
        <div className={styles.img}
             style={movie.poster ?{ backgroundImage: `url(${movie.poster})`} : {} }
        ></div>
      </div>
      <div className={styles.text}>
        <h3>{movie.originTitle}</h3>
        <div className={styles.tags}>
          {
            movie.genre.map((genre) =>
              <Tag key={genre.id}>{genre.value}</Tag>
            )
          }
        </div>
        <div className={styles.bottom}>
          <p className={styles.date}>{movie.releaseDate}</p>
          <Rate
            className={styles.rate}
            disabled
            allowHalf
            value={movie.scoreFR%2 > 1 ?
              Math.floor(movie.scoreFR/2) + 0.5 :
              Math.floor(movie.scoreFR/2)}
          />
          <span className={styles.score}>{movie.scoreFR}</span>
          <span className={styles.count}>({movie.votes})</span>
        </div>
      </div>
    </Card>
  );

}

export default MovieCardLarge;
