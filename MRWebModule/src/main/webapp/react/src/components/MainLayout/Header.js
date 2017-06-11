import React from 'react';
import { connect } from 'dva';
import { Menu, Row, Col, Dropdown, message } from 'antd';
import { Link } from 'dva/router';
import icon from '../../assets/img/icon.png';
import styles from './Header.css';

import Avatar from '../User/Avatar';

import avatar from '../../assets/img/avatar.png';

function Header({ dispatch, location, user }) {
  const MenuItem = Menu.Item;

  function handleSignOut(e) {
    e.preventDefault();
    dispatch({
      type: "user/signOut",
      onSuccess: (username) => message.success('Goodbye!'),
      onError: (error) => message.error(error)
    });
  };

  const userMenu = user ? (

    <Menu className={styles.dropdown_menu}>
      <MenuItem key="username" disabled>
        <span>{user ? user.username : ''}</span>
      </MenuItem>
      <MenuItem key="profile">
        <Link to={"/user/" + user.id}>Profile</Link>
      </MenuItem>
      <Menu.Divider />
      <MenuItem key="signout">
        <a onClick={handleSignOut}>Sign out</a>
      </MenuItem>
    </Menu>
  ) : null;

  return (
    <div className={styles.header}>
      <div className="container">
        <Row>
          <Col className={styles.title_wrapper} span={6}>
            <img className={styles.icon} src={icon}/>
            <div className={styles.title_text}>
              <Link to="/" className={styles.main_title}>Movieet</Link>
              <p className={styles.sub_title}>meet the movie</p>
            </div>
          </Col>
          <Col span={18}>
            <Menu
              className={styles.menu}
              selectedKeys={['/' + location.pathname.split('/')[1]]}
              mode="horizontal"
              theme="light"
            >
              <MenuItem key="/movies">
                <Link to="/movies">
                  Movies
                </Link>
              </MenuItem>
              <MenuItem key="/analysis">
                <Link to="/analysis">
                  Analysis
                </Link>
              </MenuItem>
              <MenuItem key="/prediction">
                <Link to="/prediction">
                  Prediction
                </Link>
              </MenuItem>

              { user ?
                <MenuItem key="/user" className={styles.avatar_item}>
                  <Dropdown className={styles.dropdown} overlay={userMenu} placement="bottomRight">
                    <div>
                      <div className={styles.avatar_wrapper}>
                        {/*<div className={styles.avatar} style={{ backgroundImage: `url(${avatar})`}}/>*/}
                        <Avatar
                            className={styles.avatar}
                            size="mini"
                            name={user.username}
                            level={user.level}
                        />
                      </div>
                    </div>
                  </Dropdown>
                </MenuItem>
                :
                <MenuItem key="/signin">
                  <Link to="/">
                    Sign In
                  </Link>
                </MenuItem>
              }
            </Menu>
          </Col>
        </Row>
      </div>
    </div>
  );
}

function mapStateToProps(state) {
  const { currentUser } = state.user;
  return {
    user: currentUser,
  };
}

export default connect(mapStateToProps)(Header);
