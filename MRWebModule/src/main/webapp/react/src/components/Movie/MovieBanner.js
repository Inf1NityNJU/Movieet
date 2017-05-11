import React from 'react';
import { Rate, Tag } from 'antd';
import styles from './MovieBanner.css';

import example from '../../assets/img/bg1.png';

function MovieBanner() {
  return (
    <div className={styles.banner}>
      <div className={styles.bg}>
        <div className={styles.bg_wrapper + ' ' + styles.overlay}></div>
        <div className={styles.bg_wrapper}>
          <div className={styles.bg_img} style={{ backgroundImage: `url(${example})`}}></div>
        </div>
      </div>

      <div className={styles.text}>
        <div className="container">
          <p className={styles.date}>2017.02.06</p>
          <h1>Movie Name</h1>
          <div className={styles.genre_tags}>
            <Tag>Animation</Tag>
            <Tag>Drama</Tag>
            <Tag>Fantasy</Tag>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MovieBanner;
