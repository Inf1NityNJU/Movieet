import React from 'react';
import { Row, Col, Button } from 'antd';

import styles from './UserBanner.css';

import avatar from '../../assets/img/avatar.png';

function UserBanner({user}) {
  return (
    <div className={styles.banner + ' background'}>
      <div className="container">
        <div className={styles.avatar_wrapper}>
          <div className={styles.avatar} style={{ backgroundImage: `url(${avatar})`}}></div>
        </div>
        <div className={styles.text_wrapper}>
          <h3>{user.username}</h3>
          <div className={styles.buttons}>
            <Button type="primary" icon="edit" ghost>Edit</Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UserBanner;
