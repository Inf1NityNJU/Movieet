import React from 'react';
import {connect} from 'dva';
import {Row, Col, Button} from 'antd';

import Avatar from '../User/Avatar';

import styles from './UserBanner.css';

import avatar from '../../assets/img/avatar.png';

function UserBanner({dispatch, user, currentUser, userFollow, userSimilarity, followLoading, unfollowLoading}) {

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
                    {/*<div className={styles.avatar} style={{backgroundImage: `url(${avatar})`}}/>*/}
                    <Avatar
                        className={styles.avatar}
                        size="large"
                        name={user.username}
                        level={user.level}
                    />
                </div>
                <div className={styles.text_wrapper}>
                    <h3>{user.username}</h3>
                    <span className={styles.level}>Lv {user.level}</span>
                    {user.id !== currentUser.id ?
                        <p className={styles.similarity}>Similarity: {userSimilarity} %</p> : null
                    }

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
                            {!currentUser || currentUser.id !== user.id && userFollow === null ?
                                <Col span={12}>
                                    <Button type="primary"
                                            icon="plus"
                                            className={styles.button_small}
                                            onClick={onFollowClick}
                                            loading={followLoading}
                                    >
                                        Follow
                                    </Button>
                                </Col> : null
                            }
                            {currentUser && currentUser.id !== user.id && userFollow === 'following' ?
                                <Col span={12}>
                                    <Button type="primary"
                                            icon="retweet"
                                            className={styles.button_small}
                                            onClick={onUnfollowClick}
                                            loading={unfollowLoading}
                                    >
                                        Both following
                                    </Button>
                                </Col> : null
                            }
                            {currentUser && currentUser.id !== user.id && userFollow === 'follow' ?
                                <Col span={12}>
                                    <Button type="primary"
                                            icon="check"
                                            className={styles.button_small}
                                            onClick={onUnfollowClick}
                                            loading={unfollowLoading}
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

function mapStateToProps(state) {
    const {user, currentUser, userFollow, userSimilarity} = state.user;
    return {
        user,
        currentUser,
        userFollow,
        userSimilarity,
        followLoading: state.loading.effects['user/followUser'],
        unfollowLoading: state.loading.effects['user/unfollowUser'],
    };
}

export default connect(mapStateToProps)(UserBanner);
