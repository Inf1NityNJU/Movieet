import React from 'react';
import styles from './MainLayout.css';
import { Menu, Icon } from 'antd';
import { Row, Col } from 'antd';
import { Link } from 'dva/router';

import icon from '../../assets/img/icon.png';

function Header({ location }) {
  const MenuItem = Menu.Item;
  return (
    <div className={styles.header}>
      <div className="container">
        <Row>
          <Col className={styles.title_wrapper} span={6}>
            <img className={styles.icon} src={icon}/>
            <div className={styles.title_text}>
              <Link className={styles.main_title}>Movieet</Link>
              <p className={styles.sub_title}>meet the movie</p>
            </div>
          </Col>
          <Col span={18}>
            <Menu
              className={styles.menu}
              selectedKeys={[location.pathname]}
              mode="horizontal"
              theme="light"
            >
              <MenuItem className={styles.menu_item} key="/">
                <Link to="/">
                  Movies
                </Link>
              </MenuItem>
              <MenuItem className={styles.menu_item} key="/prediction">
                <Link to="/prediction">
                  Prediction
                </Link>
              </MenuItem>
              <MenuItem className={styles.menu_item} key="/analysis">
                <Link to="/analysis">
                  Analysis
                </Link>
              </MenuItem>
            </Menu>
          </Col>
        </Row>
      </div>
    </div>
  );
}

export default Header;
