import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import MovieBanner from '../components/Movie/MovieBanner';
import MovieBrief from '../components/Movie/MovieBrief';

function Movie({ children, movie, reviews, likeMovies, user }) {
  return (
    <MainLayout location={location}>

      { movie ?
        <div>
          <MovieBanner movie={movie}/>
          <MovieBrief movie={movie} />
        </div> : null
      }
      {children}
    </MainLayout>
  );
}

function mapStateToProps(state) {
  const { movie, reviews, likeMovies, user } = state.movie;
  return {
    movie,
    reviews,
    likeMovies,
    user
  };
}

export default connect(mapStateToProps)(Movie);
