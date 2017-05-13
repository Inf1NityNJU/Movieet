import React from 'react';
import { connect } from 'dva';

import styles from './MovieDiscover.css';

import MovieDiscoverPage from '../components/MoviePage/MovieDiscoverPage';


function MovieDiscover({ location }) {
  return (
    <MovieDiscoverPage/>

  );
}

function mapStateToProps() {
  return {};
}


export default connect(mapStateToProps)(MovieDiscover);
