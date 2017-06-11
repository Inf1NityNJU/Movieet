import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import MovieBanner from '../components/Movie/MovieBanner';
import MovieBrief from '../components/Movie/MovieBrief';

function Movie({ dispatch, children, movie, user }) {
  return (
    <MainLayout location={location}>

      { movie ?
        <div>
          <MovieBanner />
          <MovieBrief />
        </div> : null
      }
      {children}
    </MainLayout>
  );
}

function mapStateToProps(state) {
  const { movie, reviews, user } = state.movie;
  return {
    movie,
    reviews,
    user
  };
}

export default connect(mapStateToProps)(Movie);
