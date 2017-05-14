import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import MovieBanner from '../components/Movie/MovieBanner';
import MovieBrief from '../components/Movie/MovieBrief';

function Movie({ children }) {
  return (
    <MainLayout location={location}>
      <MovieBanner/>
      <MovieBrief/>
      {children}
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Movie);
