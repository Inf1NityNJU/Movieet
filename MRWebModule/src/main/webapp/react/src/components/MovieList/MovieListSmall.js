import React from 'react';
import { Card, Col, Row } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';
import MovieCardSmall from '../Movie/MovieCardSmall';

function MovieListSmall({ dispatch, list, num }) {

  function onCardClick(id) {
    dispatch(routerRedux.push({
      pathname: '/movie/' + id,
    }));
  }

  var cards = [];
  for (let i = 0; i < num; i++) {
    cards.push(
      <Col key={i} span={6}>
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
