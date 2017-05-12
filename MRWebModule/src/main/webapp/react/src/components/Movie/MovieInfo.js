import React from 'react';
import { Row, Col, Icon, Button, Rate, Tag } from 'antd';

import styles from './MovieInfo.css';

import example from '../../assets/img/example.png';

function MovieInfo() {
  return (
    <div className={styles.normal}>
      <div className={styles.top}>
        <div className="container">
          <Row>
            <Col span={4}>
              <span>
                <Icon type="clock-circle-o"/>duration
              </span>
            </Col>
            <Col span={4}>
              <span>
                <Icon type="global"/>country
              </span>
            </Col>
            <Col span={4}>
              <span>
                <Icon type="message"/>language
              </span>
            </Col>
          </Row>
        </div>
      </div>
      <div className={styles.main}>
        <div className="container">
          <Row>
            <Col span={7}>
              <div className={styles.poster_wrapper}>
                <div className={styles.poster} style={{ backgroundImage: `url(${example})`}}></div>
              </div>
            </Col>
            <Col offset={1} span={8} className={styles.col_2}>
              <div className={styles.people_info}>
                <div className={styles.info_item + ' ' + styles.horizontal}>
                  <span>Director</span>
                  <span>some directors</span>
                </div>
                <div className={styles.info_item + ' ' + styles.horizontal}>
                  <span>Writer</span>
                  <span>some writers</span>
                </div>
                <div className={styles.info_item + ' ' + styles.horizontal}>
                  <span>Actor</span>
                  <span>some actors, some actors, some actors, some actors</span>
                </div>
              </div>
              <div className={styles.buttons}>
                <Row gutter={10}>
                  <Col span={12}>
                    <Button type="primary" icon="heart-o" ghost className={styles.button_small}>Want to watch</Button>
                  </Col>
                  <Col span={12}>
                    <Button type="primary" icon="star-o" className={styles.button_small}>Had watched</Button>
                  </Col>
                </Row>
                <Row>
                  <Col span={24}>
                    <Button type="primary" icon="star-o" ghost className={styles.button_large}>Add to list</Button>
                  </Col>
                </Row>
              </div>
            </Col>
            <Col offset={1} span={7}>
              <div className={styles.info_item + ' ' + styles.vertical}>
                <span>Score</span>
                <div>
                  <Rate className={styles.rate} disabled allowHalf defaultValue={3.5}/>
                  <span className={styles.score}>7.1</span>
                  <span className={styles.count}>From (count) people</span>
                </div>
              </div>
              <div className={styles.info_item + ' ' + styles.vertical}>
                <span>Tags</span>
                <div className={styles.tags}>
                  <Tag>Fantasy</Tag>
                  <Tag>Something</Tag>
                </div>
              </div>
            </Col>
          </Row>
        </div>
      </div>
    </div>
  );
}

export default MovieInfo;
