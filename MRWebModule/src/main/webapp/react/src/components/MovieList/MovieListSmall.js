import React from 'react';
import { Card, Col, Row } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';

import styles from './MovieList.css';

import MovieCardSmall from '../Movie/MovieCardSmall';

function MovieListSmall({ dispatch, list, num, colCount = 4 }) {

  function onCardClick(id) {
    dispatch(routerRedux.push({
      pathname: '/movie/' + id,
    }));
  }

  let cards = [];
  num = Math.min(num, list.length);

  const col = 24 / colCount;

  for (let i = 0; i < num; i++) {
    cards.push(
      <Col key={i} span={col} className={styles.card}>
        <MovieCardSmall
          onClick={() => onCardClick(list[i].id)}
          movie={list[i]}/>
      </Col>
    );
  }

  return (
    <Row gutter={20}>
      {cards}
    </Row>
  );
}

export default connect()(MovieListSmall);
