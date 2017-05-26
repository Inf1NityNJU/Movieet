import React from 'react';
import { Menu, Icon } from 'antd';
import { Link } from 'dva/router';
import styles from './SubMenu.css';

function MovieMenu() {
  const MenuItem = Menu.Item;
  return (
    <Menu
      className={styles.sub_menu}
      selectedKeys={[location.pathname]}
      mode="horizontal"
      theme="light"
    >
      <MenuItem key="discover">
        <Link to="/user/movie">
          <Icon type="inbox" />Movies
        </Link>
      </MenuItem>
      <MenuItem key="category">
        <Link to="/user/analysis">
          <Icon type="line-chart" />Analysis
        </Link>
      </MenuItem>
    </Menu>
  );
}

export default MovieMenu;
