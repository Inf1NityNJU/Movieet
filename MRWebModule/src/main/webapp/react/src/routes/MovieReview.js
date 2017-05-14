import React from 'react';
import { connect } from 'dva';

import MovieReviewPage from '../components/MoviePage/MovieReviewPage';

function MovieReview() {
  return (
    <MovieReviewPage/>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(MovieReview);
