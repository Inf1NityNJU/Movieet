import React from 'react';
import {Row, Col, Button} from 'antd';

import styles from './UserBanner.css';

import avatar from '../../assets/img/avatar.png';

function UserBanner({user}) {
  return (
    <div className={styles.banner + ' background'}>
      <div className="container">
        <div className={styles.avatar_wrapper}>
          <div className={styles.avatar} style={{backgroundImage: `url(${avatar})`}}></div>
        </div>
        <div className={styles.text_wrapper}>
          <h3>{user.username}</h3>
          <div className={styles.buttons}>
            <Row gutter={10}>
              <Col span={12}>
                <Button type="primary"
                        icon="star-o"
                        className={styles.button_small}
                >
                  Follow
                </Button>
              </Col>
              <Col span={12}>
                <Button type="primary"
                        icon="edit"
                        ghost
                        className={styles.button_small}
                >
                  Edit
                </Button>
              </Col>
            </Row>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UserBanner;
