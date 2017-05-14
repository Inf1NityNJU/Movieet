import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';

function Analysis() {
  return (
    <MainLayout location={location}>
      <Banner/>
      <div className="normal">
        <div className="container">

        </div>

      </div>
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Analysis);
