import React from 'react';
import { connect } from 'dva';
import styles from './IndexPage.css';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';
import MovieMenu from '../components/MainLayout/MovieMenu';
import MovieListSmall from '../components/MovieList/MovieListSmall';
import MovieListLarge from '../components/MovieList/MovieListLarge';

function IndexPage({ location }) {
  return (
    <MainLayout location={location}>
      <Banner/>
      <div className={styles.normal}>
        <div className="container">
          <MovieMenu/>
        </div>
        <div className="background">
          <div className="container">
            <MovieListSmall/>
          </div>
        </div>
        <div className="background">
          <div className="container">
            <MovieListLarge/>
          </div>
        </div>
      </div>
    </MainLayout>
  );
}

IndexPage.propTypes = {};

export default connect()(IndexPage);
