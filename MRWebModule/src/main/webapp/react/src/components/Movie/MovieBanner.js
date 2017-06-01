import React from 'react';
import { Rate, Tag } from 'antd';
import styles from './MovieBanner.css';

import example from '../../assets/img/bg1.png';

function MovieBanner({ movie }) {
  return (
    <div className={styles.banner}>
      <div className={styles.bg}>
        <div className={styles.bg_wrapper + ' ' + styles.overlay}/>
        <div className={styles.bg_wrapper}>
          <div className={styles.bg_img} style={{ backgroundImage: `url(${example})`}}/>
        </div>
      </div>

      <div className={styles.text}>
        <div className="container">
          <p className={styles.date}>{movie.releaseDate}</p>
          <h1>{movie.originTitle}</h1>
          <div className={styles.genre_tags}>
            {movie.genre ?
              movie.genre.map((genre) =>
                <Tag key={genre.id}>{genre.value}</Tag>
              ) : null
            }
          </div>
        </div>
      </div>
    </div>
  );
}

export default MovieBanner;
