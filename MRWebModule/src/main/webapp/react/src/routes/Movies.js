import React from 'react';
import { connect } from 'dva';
import styles from './Movies.css';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';
import MovieMenu from '../components/MainLayout/MovieMenu';
import MovieCategoryPage from '../components/MoviePage/MovieCategoryPage';



function Movies({ children }) {
  return (
    <MainLayout location={location}>
      <Banner/>
      <div className="normal">
        <div className="container">
          <MovieMenu/>
        </div>
        <div className="background">
          <div className="container">
            { children }
          </div>
        </div>
      </div>
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Movies);
