import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import UserBanner from '../components/User/UserBanner';
import UserMenu from '../components/MainLayout/UserMenu';


import UserMoviePage from '../components/UserPage/UserMoviePage';

import styles from './User.css';

function User() {
  return (
    <MainLayout location={location}>
      <UserBanner/>
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

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(User);
