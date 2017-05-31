import React from 'react';
import {connect} from 'dva';
import {Carousel, Row, Col} from 'antd';
import logo from '../../assets/img/logo.png';

import Auth from '../Auth/Auth'
import UserSurvey from '../User/UserSurvey'

import styles from './Banner.css';

// function Banner({ isAuth = true, loading, user, surveyStatus }) {

class Banner extends React.Component {

  onSurveyOk = (survey) => {
    const {dispatch} = this.props;
    dispatch({
      type: 'user/postUserSurvey',
      payload: survey
    });
  };
  onSurveyCancel = () => {
    const {dispatch} = this.props;
    dispatch({
      type: 'user/saveSurveyStatus',
      payload: false
    });
  };


  render() {
    const {isAuth = true, loading, surveyLoading, user, surveyStatus} = this.props;

    return (
      <div className={styles.banner}>
        <div className={styles.carousel}>
          <Carousel>
            <div>
              <div className={styles.bg_wrapper + ' ' + styles.overlay}/>
              <div className={styles.bg_wrapper}>
                <div className={styles.bg_img + ' ' + styles.bg_img1}/>
              </div>
            </div>
            <div>
              <div className={styles.bg_wrapper + ' ' + styles.overlay}/>
              <div className={styles.bg_wrapper}>
                <div className={styles.bg_img + ' ' + styles.bg_img2}/>
              </div>
            </div>
            <div>
              <div className={styles.bg_wrapper + ' ' + styles.overlay}/>
              <div className={styles.bg_wrapper}>
                <div className={styles.bg_img + ' ' + styles.bg_img3}/>
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
                (!isAuth || user) ? '' :
                  <div className={styles.auth_wrapper}>
                    <Auth loading={loading}/>
                  </div>
              }

            </Col>
          </Row>
        </div>
        <UserSurvey
          visible={surveyStatus}
          loading={surveyLoading}
          handleOk={this.onSurveyOk}
          handleCancel={this.onSurveyCancel}
        />
      </div>
    );
  }
}

function mapStateToProps(state) {
  const {currentUser, surveyStatus} = state.user;
  return {
    loading: state.loading.models.user,
    surveyLoading: state.loading.effects['user/postUserSurvey'],
    user: currentUser,
    surveyStatus,
  };
}


export default connect(mapStateToProps)(Banner);
