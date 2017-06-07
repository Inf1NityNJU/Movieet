import React from 'react';
import {connect} from 'dva';

import MainLayout from '../components/MainLayout/MainLayout';
import UserBanner from '../components/User/UserBanner';
import UserMenu from '../components/MainLayout/UserMenu';

function User({dispatch, user, currentUser, userFollow, children}) {
    return (
        <MainLayout location={location}>
            {user ?
                <UserBanner
                    dispatch={dispatch}
                    user={user}
                    currentUser={currentUser}
                    userFollow={userFollow}/>
                : null }

            <div className="container">
                <UserMenu user={user}/>
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
    const {user, currentUser, userFollow} = state.user;
    return {
        user,
        currentUser,
        userFollow,
    };
}

export default connect(mapStateToProps)(User);
