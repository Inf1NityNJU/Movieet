import React from 'react';
import {connect} from 'dva';

import styles from './PredictionPage.css';

import PredictionSearchInput from '../Prediction/PredictionSearchInput';
import ElementItemList from '../Prediction/ElementItemList';

function PredictionCombinationPage({ dispatch, search }) {
  return (
    <div className="background">
      <div className="container">

        <div className={styles.part}>
          <PredictionSearchInput
            search={search}
            dispatch={dispatch}
          />
        </div>


        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Genre</h3>
          </div>

          <ElementItemList num={10}/>

        </div>

      </div>
    </div>
  );
}

function mapStateToProps(state) {
  const {search} = state.prediction;
  return {
    search
  };
}

export default connect(mapStateToProps)(PredictionCombinationPage);
