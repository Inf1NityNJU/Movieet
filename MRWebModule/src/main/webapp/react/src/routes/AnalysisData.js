import React from 'react';
import { connect } from 'dva';

import AnalysisDataPage from '../components/AnalysisPage/AnalysisDataPage';

function AnalysisData({ location }) {
  return (
    <AnalysisDataPage location={location}/>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(AnalysisData);
