import React from 'react';
import { connect } from 'dva';
import styles from './MovieInfo.css';

import MovieInfoPage from '../components/MoviePage/MovieInfoPage';


function MovieInfo() {
  return (
    <MovieInfoPage/>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(MovieInfo);
