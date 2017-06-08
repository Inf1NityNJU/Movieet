import React from 'react';
import {connect} from 'dva';
import {Carousel, Row, Col} from 'antd';
import logo from '../../assets/img/logo.png';

import Auth from '../Auth/Auth'
import styles from './Banner.css';

// function Banner({ isAuth = true, loading, user, surveyStatus }) {

class Banner extends React.Component {


    render() {
        const {isAuth = true, loading} = this.props;

        return (
            <div className={styles.banner}>
                <div className={styles.carousel}>
                    <Carousel>
                        <div>
                            <div className={styles.bg_wrapper}>
                                <div className={styles.bg_img + ' ' + styles.bg_img1}/>
                                <div className={styles.overlay}/>
                            </div>
                        </div>

                        <div>
                            <div className={styles.bg_wrapper}>
                                <div className={styles.bg_img + ' ' + styles.bg_img2}/>
                                <div className={styles.overlay}/>
                            </div>
                        </div>

                        <div>
                            <div className={styles.bg_wrapper}>
                                <div className={styles.bg_img + ' ' + styles.bg_img3}/>
                                <div className={styles.overlay}/>
                            </div>
                        </div>

                    </Carousel>
                </div>

                <div className="container">
                    <Row>
                        <Col span={5} offset={5}>
                            <div className={styles.logo_wrapper}>
                                <img src={logo} className={styles.logo}/>
                                <p>Meet the movie<br/>Meet the world</p>
                            </div>
                        </Col>
                        <Col span={6} offset={8}>

                            {
                                (!isAuth) ? '' :
                                    <div className={styles.auth_wrapper}>
                                        <Auth loading={loading}
                                              location={location}/>
                                    </div>
                            }

                        </Col>
                    </Row>
                </div>

            </div>
        );
    }
}

function mapStateToProps(state) {
    const {currentUser} = state.user;
    return {
        loading: state.loading.models.user,
        user: currentUser,
    };
}


export default connect(mapStateToProps)(Banner);
