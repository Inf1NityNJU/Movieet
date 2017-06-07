/**
 * Created by Sorumi on 17/6/7.
 */
import React from 'react';
import { connect } from 'dva';

import AnalysisRankPage from '../components/AnalysisPage/AnalysisRankPage';

function AnalysisRank({ location }) {
    return (
        <AnalysisRankPage location={location}/>
    );
}

function mapStateToProps() {
    return {};
}

export default connect(mapStateToProps)(AnalysisRank);
