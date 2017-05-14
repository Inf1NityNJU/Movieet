import React from 'react';
import { connect } from 'dva';

import MovieSearchPage from '../components/MoviePage/MovieSearchPage';

function MovieSearch() {
  return (
    <MovieSearchPage/>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(MovieSearch);
