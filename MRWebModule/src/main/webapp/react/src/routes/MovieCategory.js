import React from 'react';
import { connect } from 'dva';


import MovieCategoryPage from '../components/MoviePage/MovieCategoryPage';


function MovieCategory() {
  return (

    <MovieCategoryPage/>

  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(MovieCategory);
