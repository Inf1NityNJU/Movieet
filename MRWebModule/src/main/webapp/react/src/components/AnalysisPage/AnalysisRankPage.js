import React from 'react';

import {connect} from 'dva';

import styles from './AnalysisPage.css';

import MovieListMini from '../MovieList/MovieListMini';


function AnalysisRankPage({location,}) {

    return (
        <div className={styles.normal}>
            <MovieListMini num={5}/>
        </div>
    );

}
function mapStateToProps(state) {
    const analysis = state.analysis;
    return {};
}

export default connect(mapStateToProps)(AnalysisRankPage);
