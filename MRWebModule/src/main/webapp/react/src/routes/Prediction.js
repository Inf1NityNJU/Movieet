import React from 'react';
import {connect} from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';
import PredictionCombinationPage from '../components/PredictionPage/PredictionCombinationPage';

function Prediction() {
  return (
    <MainLayout location={location}>
        <Banner location={location}/>
      <PredictionCombinationPage/>
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Prediction);
