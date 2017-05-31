import React from 'react';
import { connect } from 'dva';
import styles from './Test.css';

import MovieScoreChart from '../components/Movie/MovieScoreChart';
function Test() {
  return (
    <div className={styles.normal}>

      <MovieScoreChart/>
    </div>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Test);
