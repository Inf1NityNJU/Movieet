import React from 'react';
import { Card, Col, Row } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';
import MovieCardSmall from '../Movie/MovieCardSmall';

function MovieListSmall({ dispatch, num }) {

  function onCardClick() {
    dispatch(routerRedux.push({
      pathname: '/movie/1',
    }));
  }

  var cards = [];
  for (var i = 0; i < num; i++) {
    cards.push(
      <Col key={i} span={6}>
        <MovieCardSmall onClick={onCardClick}/>
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
