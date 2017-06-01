import React from 'react';
import { Col, Row } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';

import styles from './PeopleList.css';

import PeopleCard from '../People/PeopleCard';

function PeopleList({ dispatch, type, num, list}) {

  function onCardClick(id) {
    console.log(id);
    dispatch(routerRedux.push({
      // todo
      pathname: '/' + type + '/' + id,
    }));
  }

  let cards = [];
  num = Math.min(num, list.length);
  for (let i = 0; i < num; i++) {
    cards.push(
      <Col key={i} span={6} className={styles.card}>
        <PeopleCard
          onClick={() => onCardClick(list[i].id)}
          people={list[i]}
        />
      </Col>
    );
  }

  return (
    <Row gutter={20}>
      {cards}
    </Row>
  );
}

export default connect()(PeopleList);
