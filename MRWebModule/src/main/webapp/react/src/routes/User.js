import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import UserBanner from '../components/User/UserBanner';
import UserMenu from '../components/MainLayout/UserMenu';


import UserMoviePage from '../components/UserPage/UserMoviePage';

function User({ user, movie }) {
  return (
    <MainLayout location={location}>
      {user ?
        <UserBanner user={user}/>
        : null }

      <div className="container">
        <UserMenu/>
      </div>

      <div className="background">
        <div className="container">
          <UserMoviePage/>
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
