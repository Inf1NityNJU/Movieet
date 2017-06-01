import React from 'react';
import {connect} from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import PredictionCombinationPage from '../components/PredictionPage/PredictionCombinationPage';

function Prediction() {
  return (
    <MainLayout location={location}>
      <PredictionCombinationPage/>
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Prediction);
