import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import PeoplePage from '../components/PeoplePage/PeoplePage';

function People() {
  return (
    <MainLayout location={location}>
      <PeoplePage/>
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(People);
