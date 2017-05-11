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
        <Link to="/movies/discover">
          <Icon type="eye-o" />Discover
        </Link>
      </MenuItem>
      <MenuItem key="category">
        <Link to="/movies/category">
          <Icon type="tags-o" />Category
        </Link>
      </MenuItem>
      <MenuItem key="/search">
        <Link to="/movies/search">
          <Icon type="search" />Search
        </Link>
      </MenuItem>
    </Menu>
  );
}

export default MovieMenu;
