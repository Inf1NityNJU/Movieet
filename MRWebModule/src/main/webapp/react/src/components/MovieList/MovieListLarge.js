import React from 'react';
import { Card, Col, Row } from 'antd';
import styles from './MovieList.css';

import MovieCardLarge from '../Movie/MovieCardLarge';

function MovieListLarge() {
  return (
    <Row gutter={20}>
      <Col span="12" className={styles.card}>
        <MovieCardLarge/>
      </Col>
      <Col span="12" className={styles.card}>
        <MovieCardLarge/>
      </Col>
      <Col span="12" className={styles.card}>
        <MovieCardLarge/>
      </Col>
      <Col span="12" className={styles.card}>
        <MovieCardLarge/>
      </Col>
    </Row>
  );
}

export default MovieListLarge;
