import React from 'react';
import { Row, Col, Icon, Button, Rate, Tag } from 'antd';

import styles from './MovieBrief.css';

import example from '../../assets/img/example.png';

function MovieBrief({ movie }) {
  return (
    <div className={styles.normal}>
      {/*
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
      */}
      <div className={styles.main}>
        <div className="container">
          <Row>
            <Col span={7}>
              <div className={styles.poster_wrapper}>
                <div className={styles.poster} style={{ backgroundImage: `url(${movie.poster})`}}></div>
              </div>
            </Col>
            <Col offset={1} span={8} className={styles.col_2}>
              <div className={styles.people_info}>
                {movie.directors ?
                  <div className={styles.info_item + ' ' + styles.horizontal}>
                    <span>Director</span>
                  <span>
                    {movie.directors.slice(0, 5).join(', ') }
                  </span>
                  </div> : null}
                {/*
                 <div className={styles.info_item + ' ' + styles.horizontal}>
                 <span>Writer</span>
                 <span>some writers</span>
                 </div>
                 */}
                {movie.actors ?
                  <div className={styles.info_item + ' ' + styles.horizontal}>
                    <span>Actor</span>
                    <span> {movie.actors.sort(() => .5 - Math.random()).slice(0, 5).join(', ')}</span>
                  </div> : null}
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
                {/*
                 <Row>
                 <Col span={24}>
                 <Button type="primary" icon="star-o" ghost className={styles.button_large}>Add to list</Button>
                 </Col>
                 </Row>
                 */}
              </div>
            </Col>
            <Col offset={1} span={7}>
              <div className={styles.info_item + ' ' + styles.vertical}>
                <span>Score</span>
                <div>
                  <Rate className={styles.rate} disabled allowHalf value={movie.rank / 2}/>
                  <span className={styles.score}>{movie.rank}</span>
                  <span className={styles.count}>From {movie.votes} people</span>
                </div>
              </div>
              { movie.keywords ?
              <div className={styles.info_item + ' ' + styles.vertical}>
                <span>Tags</span>
                <div className={styles.tags}>
                  {
                    movie.keywords.sort(() => .5 - Math.random()).slice(0, 7).map((tag) =>
                      <Tag key={tag}>{tag}</Tag>
                    )
                  }
                </div>
              </div> : null }
            </Col>
          </Row>
        </div>
      </div>
    </div>
  );
}

export default MovieBrief;
