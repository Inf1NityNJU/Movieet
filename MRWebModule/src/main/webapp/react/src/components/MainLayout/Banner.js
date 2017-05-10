import React from 'react';
import styles from './MainLayout.css';
import { Carousel } from 'antd';
import { Row, Col } from 'antd';
import logo from '../../assets/img/logo.png';
import Auth from '../Auth/Auth'

function Banner({ location }) {
  return (
    <div className={styles.banner}>
      <div className={styles.carousel}>
        <Carousel>
          <div>
            <div className={styles.bg_wrapper + ' ' + styles.overlay}></div>
            <div className={styles.bg_wrapper}>
              <div className={styles.bg_img + ' ' + styles.bg_img1}></div>
            </div>
          </div>
          <div>
            <div className={styles.bg_wrapper + ' ' + styles.overlay}></div>
            <div className={styles.bg_wrapper}>
              <div className={styles.bg_img + ' ' + styles.bg_img2}></div>
            </div>
          </div>
          <div>
            <div className={styles.bg_wrapper + ' ' + styles.overlay}></div>
            <div className={styles.bg_wrapper}>
              <div className={styles.bg_img + ' ' + styles.bg_img3}></div>
            </div>
          </div>
        </Carousel>
      </div>

      <div className="container">
        <Row>
          <Col span={5} offset={5}>
            <div className={styles.logo_wrapper}>
              <img src={logo} className={styles.logo}/>
              <p>Meet the movie<br/>Meet the world</p>
            </div>
          </Col>
          <Col span={6} offset={8} >
            <div className={styles.auth_wrapper}>
              <Auth/>
            </div>
          </Col>
        </Row>
      </div>
    </div>
  );
}

export default Banner;
