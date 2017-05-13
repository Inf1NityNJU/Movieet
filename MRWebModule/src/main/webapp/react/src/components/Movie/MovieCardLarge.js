import React, { Component } from 'react';
import { Card, Rate, Tag } from 'antd';
import styles from './MovieCard.css';

import example from '../../assets/img/example.png';

class MovieCardLarge extends Component {


  render() {
    return (
      <Card className={styles.card + ' ' + styles.card_large}
            onClick={() => this.props.onClick()}>
        <div className={styles.img_wrapper}>
          <div className={styles.img} style={{ backgroundImage: `url(${example})`}}></div>
        </div>
        <div className={styles.text}>
          <h3>Europe Street beat</h3>
          <div className={styles.tags}>
            <Tag>Animation</Tag>
            <Tag>Drama</Tag>
            <Tag>Fantasy</Tag>
          </div>
          <div className={styles.bottom}>
            <p className={styles.date}>2017.02.03</p>
            <Rate className={styles.rate} disabled allowHalf defaultValue={3.5}/>
            <span className={styles.score}>7.1</span>
            <span className={styles.count}>(count)</span>
          </div>
        </div>
      </Card>
    );
  }
}

export default MovieCardLarge;
