import React from 'react';
import { connect } from 'dva';
import styles from './Movie.css';

import MainLayout from '../components/MainLayout/MainLayout';
//import MovieBanner from '../components/Movie/MovieBanner';
//import MovieInfo from '../components/Movie/MovieInfo';
//import ReviewList from '../components/ReviewList/ReviewList';
import MovieInfoPage from '../components/MoviePage/MovieInfoPage';

function Movie() {
  return (
    <MainLayout location={location}>
      <MovieInfoPage/>
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Movie);
