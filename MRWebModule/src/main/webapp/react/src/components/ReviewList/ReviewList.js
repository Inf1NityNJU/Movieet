import React from 'react';
import { Col, Row } from 'antd';
import styles from './ReviewList.css';

import ReviewCard from '../Review/ReviewCard';

function ReviewList() {
  return (
    <div className="container">
      <Row>
        <Col className={styles.card}>
          <ReviewCard/>
        </Col>
        <Col className={styles.card}>
          <ReviewCard/>
        </Col>
        <Col className={styles.card}>
          <ReviewCard/>
        </Col>
      </Row>
    </div>
  );
}

export default ReviewList;
