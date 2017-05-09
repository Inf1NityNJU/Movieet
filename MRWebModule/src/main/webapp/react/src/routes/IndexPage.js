import React from 'react';
import { connect } from 'dva';
import styles from './IndexPage.css';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';

function IndexPage({ location }) {
  return (
    <MainLayout location={location}>
      <Banner/>
      <div className={styles.normal}>

      </div>
    </MainLayout>
  );
}

IndexPage.propTypes = {};

export default connect()(IndexPage);
