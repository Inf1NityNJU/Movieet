import React from 'react';
import {Row, Col, Button} from 'antd';

import styles from './UserBanner.css';

import avatar from '../../assets/img/avatar.png';

function UserBanner({dispatch, user, currentUser, userFollow}) {

    function onFollowClick() {
        dispatch({
            type: 'user/followUser',
            payload: user.id,
        })

    }

    function onUnfollowClick() {
        dispatch({
            type: 'user/unfollowUser',
            payload: user.id,
        })

    }

    return (
        <div className={styles.banner + ' background'}>
            <div className="container">
                <div className={styles.avatar_wrapper}>
                    <div className={styles.avatar} style={{backgroundImage: `url(${avatar})`}}/>
                </div>
                <div className={styles.text_wrapper}>
                    <h3>{user.username}</h3>
                    <div className={styles.buttons}>
                        <Row gutter={10}>
                            {/*currentUser.id === user.id ?
                                <Col span={12}>
                                    <Button type="primary"
                                            icon="edit"
                                            ghost
                                            className={styles.button_small}
                                    >
                                        Edit
                                    </Button>
                                </Col> : null
                            */}
                            {currentUser.id !== user.id && userFollow === null ?
                                <Col span={12}>
                                    <Button type="primary"
                                            icon="plus"
                                            className={styles.button_small}
                                            onClick={onFollowClick}
                                    >
                                        Follow
                                    </Button>
                                </Col> : null
                            }
                            {currentUser.id !== user.id && userFollow === 'following' ?
                                <Col span={12}>
                                    <Button type="primary"
                                            icon="retweet"
                                            className={styles.button_small}
                                            onClick={onUnfollowClick}
                                    >
                                        Both following
                                    </Button>
                                </Col> : null
                            }
                            {currentUser.id !== user.id && userFollow === 'follow' ?
                                <Col span={12}>
                                    <Button type="primary"
                                            icon="check"
                                            className={styles.button_small}
                                            onClick={onUnfollowClick}
                                    >
                                        Following
                                    </Button>
                                </Col> : null
                            }
                        </Row>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UserBanner;
