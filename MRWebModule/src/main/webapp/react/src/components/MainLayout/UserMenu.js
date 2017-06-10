import React from 'react';
import { Menu, Icon } from 'antd';
import { Link } from 'dva/router';
import styles from './SubMenu.css';

function UserMenu({location, user }) {
  const MenuItem = Menu.Item;
  return user ? (
    <Menu
      className={styles.sub_menu}
      selectedKeys={[location.pathname]}
      mode="horizontal"
      theme="light"
    >
      <MenuItem key={"/user/" + user.id + "/movie"}>
        <Link to={"/user/" + user.id + "/movie"}>
          <Icon type="inbox" />Movies
        </Link>
      </MenuItem>
      <MenuItem key={"/user/" + user.id + "/friend"}>
        <Link to={"/user/" + user.id + "/friend"}>
          <Icon type="team" />Friends
        </Link>
      </MenuItem>
      {/*<MenuItem key={"/user/" + user.id + "/analysis"}>*/}
        {/*<Link to={"/user/" + user.id + "/analysis"}>*/}
          {/*<Icon type="line-chart" />Analysis*/}
        {/*</Link>*/}
      {/*</MenuItem>*/}
    </Menu>
  ) : null;
}

export default UserMenu;
