import React from 'react';

import { connect } from 'dva';


import MainLayout from '../MainLayout/MainLayout';
import Banner from '../MainLayout/Banner';

import GenreRingChart from '../Analysis/GenreRingChart';
import GenreLineChart from '../Analysis/GenreLineChart';
import GenreBarChart from '../Analysis/GenreBarChart';

import styles from './AnalysisPage.css';

function AnalysisPage({ location, quantityInGenre, genreQuantityScoreInYear }) {

  return (
    <MainLayout location={location}>
      <Banner isAuth={false}/>
      <div className="normal">
        <div className="container">

          {/*<div className={styles.part}>*/}
            {/*<div className={styles.title}>*/}
              {/*<h3>Genre Quantity</h3>*/}
            {/*</div>*/}
            {/*{*/}
              {/*quantityInGenre ?*/}
                {/*<GenreRingChart data={quantityInGenre}/> :*/}
                {/*null*/}
            {/*}*/}
          {/*</div>*/}

            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Genre Score Range</h3>
                </div>
                <GenreBarChart />
            </div>


          {/*<div className={styles.part}>*/}
            {/*<div className={styles.title}>*/}
              {/*<h3>Genre Quantity And Score In Year</h3>*/}
            {/*</div>*/}
            {/*{*/}
              {/*genreQuantityScoreInYear ?*/}
                {/*<GenreLineChart data={genreQuantityScoreInYear}/> :*/}
                {/*null*/}
            {/*}*/}
          {/*</div>*/}


        </div>
      </div>
    </MainLayout>
  );
}


function mapStateToProps(state) {
  const analysis = state.analysis;
  return {
    quantityInGenre: analysis.quantityInGenre,
    genreQuantityScoreInYear: analysis.genreQuantityScoreInYear
  };
}

export default connect(mapStateToProps)(AnalysisPage);
