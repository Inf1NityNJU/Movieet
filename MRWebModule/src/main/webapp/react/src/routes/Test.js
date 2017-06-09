import React from 'react';
import {connect} from 'dva';
import styles from './Test.css';

import TLineChart from '../components/Prediction/TLineChart';
function Test({ data }) {

  return (
    <div className={styles.normal}>

        {data ?
            <div className={styles.part}>
              <div className={styles.title}>
                <h3>T Distribution Chart</h3>
              </div>
              <TLineChart
                  data={data.scoreFR}
              />
            </div> : null
        }

    </div>
  );
}

function mapStateToProps(state) {
    const {data} = state.test;
    return {
      data
    };
}
export default connect(mapStateToProps)(Test);
