import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import UserBanner from '../components/User/UserBanner';
import UserMenu from '../components/MainLayout/UserMenu';

function User({ user, children }) {
  return (
    <MainLayout location={location}>
      {user ?
        <UserBanner user={user}/>
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
  const { user, movie } = state.user;
  return {
    user,
    movie,
  };
}

export default connect(mapStateToProps)(User);
