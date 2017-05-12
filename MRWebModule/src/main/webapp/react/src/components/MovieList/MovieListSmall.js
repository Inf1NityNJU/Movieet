import React from 'react';
import { Card, Col, Row } from 'antd';

import MovieCardSmall from '../Movie/MovieCardSmall';

function MovieListSmall() {
  return (
    <Row gutter={20}>
      <Col span={6}>
        <MovieCardSmall/>
      </Col>
      <Col span={6}>
        <MovieCardSmall/>
      </Col>
      <Col span={6}>
        <MovieCardSmall/>
      </Col>
      <Col span={6}>
        <MovieCardSmall/>
      </Col>
    </Row>
  );
}

export default MovieListSmall;
