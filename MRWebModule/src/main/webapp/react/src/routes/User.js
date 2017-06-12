import React from 'react';
import {connect} from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import UserBanner from '../components/User/UserBanner';
import UserMenu from '../components/MainLayout/UserMenu';

function User({dispatch, user, currentUser, userFollow, userSimilarity, children})
{
    return (
        <MainLayout location={location}>
            {user ?
                <UserBanner/>
                : null }

            <div className="container">
                <UserMenu user={user} location={location}/>
            </div>

            <div className="background">
                <div className="container">
                    {children}
                </div>
            </div>

        </MainLayout>
    );
}

function mapStateToProps(state) {
    const {user, currentUser, userFollow, userSimilarity} = state.user;
    return {
        user,
        currentUser,
        userFollow,
        userSimilarity
    };
}

export default connect(mapStateToProps)(User);
