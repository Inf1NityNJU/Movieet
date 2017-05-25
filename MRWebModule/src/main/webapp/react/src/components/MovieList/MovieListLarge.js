import React from 'react';
import { Col, Row } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';
import styles from './MovieList.css';

import MovieCardLarge from '../Movie/MovieCardLarge';

function MovieListLarge({ dispatch, num, list }) {

  function onCardClick(id) {
    console.log(id);
    dispatch(routerRedux.push({
      pathname: '/movie/' + id,
    }));
  }

  let cards = [];
  num = Math.min(num, list.length);
  for (let i = 0; i < num; i++) {
    cards.push(
      <Col key={i} span={12} className={styles.card}>
        <MovieCardLarge
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

export default connect()(MovieListLarge);
