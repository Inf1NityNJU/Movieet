import React from 'react';
import { connect } from 'dva';
import styles from './IndexPage.css';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';
import MovieMenu from '../components/MainLayout/MovieMenu';
import MovieDiscoverPage from '../components/MoviePage/MovieDiscoverPage';


function IndexPage({ location }) {
  return (
    <MainLayout location={location}>
      <Banner/>
      <div className="normal">
        <div className="container">
          <MovieMenu/>
        </div>
        <div className="background">
          <div className="container">
            <MovieDiscoverPage/>
          </div>
        </div>
      </div>
    </MainLayout>
  );
}

IndexPage.propTypes = {};

export default connect()(IndexPage);
