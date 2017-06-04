import React from 'react';
import { Col, Row } from 'antd';

import ReviewCard from '../Review/ReviewCard';

import styles from './ReviewList.css';

function ReviewList({ num }) {

  let cards = [];
  for (let i = 0; i < num; i++) {
    cards.push(
      <Col key={i} className={styles.card}>
        <ReviewCard/>
      </Col>
    );
  }

  return (
    <div>
      <Row>
        {cards}
      </Row>
    </div>
  );
}

export default ReviewList;
