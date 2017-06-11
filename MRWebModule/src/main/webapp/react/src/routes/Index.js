import React from 'react';
import { connect } from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import LeadingPage from '../components/LeadingPage/LeadingPage';

function Movie() {
    return (
        <MainLayout location={location} theme="dark">
            <LeadingPage/>
        </MainLayout>
    );
}

function mapStateToProps() {
    return {};
}

export default connect(mapStateToProps)(Movie);
