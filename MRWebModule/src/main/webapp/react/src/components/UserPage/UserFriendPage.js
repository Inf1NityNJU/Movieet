import React from 'react';
import {connect} from 'dva';
import {Icon, Pagination, Spin} from 'antd';
import {routerRedux} from 'dva/router';

import {PREVIEW_FRIEND_SIZE, FRIEND_SIZE} from '../../constants'


import FriendList from '../FriendList/FriendList'

import styles from './UserPage.css';


function UserFriendPage({dispatch, user, status, result, page, totalCount, followingLoading, followerLoading}) {

  function onMoreClick(status) {
    dispatch(routerRedux.push({
      pathname: '/user/' + user.id + '/friend/' + status
    }));
  }

  function onPageChange(page) {
    dispatch({
      type: 'user/changeFriendPage',
      payload: page
    });
  }

  return (
    <div className={styles.friend_page}>

      { (status === null || status === 'following') ?
        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Following</h3>
            {
              status === 'following' ? null :
                <a className={styles.title_right}
                   onClick={() => onMoreClick("following")}
                >
                  More<Icon type="double-right"/>
                </a>
            }
          </div>

          {followingLoading ?
            <div className={styles.spin}>
              <Spin/>
            </div> : null
          }

          { !followingLoading && result.following && result.following.length > 0 ?
            <FriendList
              num={status === 'following' ? FRIEND_SIZE : PREVIEW_FRIEND_SIZE}
              list={result.following}
            /> : null
          }

          { status === 'following' ?
            <Pagination
              className={styles.page}
              showQuickJumper
              defaultCurrent={1}
              pageSize={ FRIEND_SIZE }
              total={totalCount}
              current={page}
              onChange={onPageChange}/>
            : null
          }
        </div>
        : null
      }
      { (status === null || status === 'follower') ?
        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Follower</h3>
            {
              status === 'follower' ? null :
                <a className={styles.title_right}
                   onClick={() => onMoreClick("follower")}
                >
                  More<Icon type="double-right"/>
                </a>
            }
          </div>

          {followerLoading ?
            <div className={styles.spin}>
              <Spin/>
            </div> : null
          }

          { !followerLoading && result.follower && result.follower.length > 0 ?

            <FriendList
              num={status === 'follower' ? FRIEND_SIZE : PREVIEW_FRIEND_SIZE}
              list={result.follower}
            /> : null
          }

          { status === 'follower' ?
            <Pagination
              className={styles.page}
              showQuickJumper
              defaultCurrent={1}
              pageSize={ FRIEND_SIZE }
              total={totalCount}
              current={page}
              onChange={onPageChange}/>
            : null
          }
        </div>
        : null
      }
    </div>
  );
}

function mapStateToProps(state) {
  const {user, friend} = state.user;
  return {
    user,
    status: friend.status,
    result: friend.result,
    page: friend.page,
    totalCount: friend.totalCount,
    followingLoading: state.loading.effects['user/fetchFollowing'],
    followerLoading: state.loading.effects['user/fetchFollower'],
  };
}


export default connect(mapStateToProps)(UserFriendPage);
