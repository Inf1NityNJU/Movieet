import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';
import MovieMenu from '../components/MainLayout/MovieMenu';

function Movies({ children }) {
  return (
    <MainLayout location={location}>
      <Banner location={location}/>
      <div className="normal">
        <div className="container">
          <MovieMenu location={location}/>
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
