import React from 'react';
import { Col, Row } from 'antd';

import styles from './ElementItemList.css';

import ElementItem from './ElementItem';

function ElementItemList({ num, list }) {
  let items = [];
  // num = Math.min(num, list.length);
  for (let i = 0; i < num; i++) {
    items.push(
      <Col key={i} span={6} className={styles.item}>
        <ElementItem
          // onClick={() => onCardClick(list[i].id)}
          // item={list[i]}
        />
      </Col>
    );
  }

  return (
    <Row gutter={10}>
      {items}
    </Row>
  );
}

export default ElementItemList;
