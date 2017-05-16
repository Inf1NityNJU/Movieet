import React from 'react';
import { Col, Row } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';
import styles from './MovieList.css';

import MovieCardLarge from '../Movie/MovieCardLarge';

function MovieListLarge({ dispatch, num, list }) {

  function onCardClick() {
    dispatch(routerRedux.push({
      pathname: '/movie/1',
    }));
  }

  var cards = [];
  num = Math.min(num, list.length);
  for (var i = 0; i < num; i++) {
    cards.push(
      <Col key={i} span={12} className={styles.card}>
        <MovieCardLarge onClick={onCardClick} movie={list[i]}/>
      </Col>
    );
  }

  return (
    <Row gutter={20}>
      {cards}
    </Row>
  );
}

export default connect()(MovieListLarge);
