import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';
import MovieMenu from '../components/MainLayout/MovieMenu';

import styles from './Error.css';

function ERROR() {
    return (
        <MainLayout location={location}>
            <Banner location={location}/>
            <div className="normal">
                <div className="container">
                    <MovieMenu location={location}/>
                </div>
                <div className="background">
                    <div className="container">
                        <div className={styles.text}>
                            <p>404</p>
                            <p>Something seems to be wrong!</p>
                        </div>

                    </div>
                </div>
            </div>
        </MainLayout>
    );
}

function mapStateToProps() {
    return {};
}

export default connect(mapStateToProps)(ERROR);
