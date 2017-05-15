import React from 'react';
import { connect } from 'dva';

import AnalysisPage from '../components/AnalysisPage/AnalysisPage';

function Analysis({ location }) {
  return (
    <AnalysisPage location={location}/>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Analysis);
