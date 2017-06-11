import React from 'react';
import {connect} from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import Banner from '../components/MainLayout/Banner';
import AnalysisMenu from '../components/MainLayout/AnalysisMenu';

function Analysis({children}) {
    return (
        <MainLayout location={location}>
            <Banner/>

            <div className="normal">
                <div className="container">
                    <AnalysisMenu location={location}/>
                </div>
                <div className="background">
                    <div className="container">
                        { children }
                    </div>
                </div>
            </div>

        </MainLayout>
    );
}

function mapStateToProps() {
    return {};
}

export default connect(mapStateToProps)(Analysis);
