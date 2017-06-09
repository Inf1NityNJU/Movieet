import React from 'react';
import {connect} from 'dva';
import MathJax from 'react-mathjax';

import TLineChart from '../components/Prediction/TLineChart';

import styles from './Test.css'

function Test({ data }) {

    const tex = `f(x) = \\int_{-\\infty}^\\infty
    \\hat f(\\xi)\\,e^{2 \\pi i \\xi x}
    \\,d\\xi`

    // const tex = `WR = \\frac{v}{v+m}R + \\frac{m}{v+m}C`;

  return (
    <div className={styles.normal}>

        <MathJax.Context>
            <div>
                This is an inline math formula:
                <MathJax.Node inline>{'a = b'}</MathJax.Node>
                And a block one:

                <MathJax.Node>{tex}</MathJax.Node>
            </div>
        </MathJax.Context>

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
