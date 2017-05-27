import React from 'react';
import { connect } from 'dva';

import UserFriendPage from '../components/UserPage/UserFriendPage';


function UserFriend() {
  return (
    <UserFriendPage />
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(UserFriend);
