import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import UserBanner from '../components/User/UserBanner';
import UserMenu from '../components/MainLayout/UserMenu';

function User({ user, currentUser, children }) {
  return (
    <MainLayout location={location}>
      {user ?
        <UserBanner user={user}
                    currentUser={currentUser}/>
        : null }

      <div className="container">
        <UserMenu user={user}/>
      </div>

      <div className="background">
        <div className="container">
          {children}
        </div>
      </div>

    </MainLayout>
  );
}

function mapStateToProps(state) {
  const { user, currentUser } = state.user;
  return {
    user,
    currentUser,
  };
}

export default connect(mapStateToProps)(User);
