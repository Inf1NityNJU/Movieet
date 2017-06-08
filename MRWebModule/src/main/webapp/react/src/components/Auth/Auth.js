import React, {Component} from 'react';
import {connect} from 'dva';
import {Tabs, Form, Icon, Input, Button, Checkbox, message} from 'antd';

import UserSurvey from '../User/UserSurvey'

import styles from './Auth.css';

class Auth extends Component {
    handleSignInSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Sign in: Received values of form: ', values);
                const {dispatch} = this.props;
                dispatch({
                    type: "user/signIn",
                    payload: {
                        username: values.username,
                        password: values.password
                    },
                    onSuccess: (username) => message.success('Hello ' + username + ' !'),
                    onError: (error) => message.error(error)
                });
            }
        });
    };
    handleSignUpSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                //console.log('Received values of form: ', values);
                const {dispatch} = this.props;
                dispatch({
                    type: "user/signUp",
                    payload: {
                        username: values.username,
                        password: values.password
                    },
                    onSuccess: (username) => message.success('Hello ' + username + ' !'),
                    onError: (error) => message.error(error)
                });
            }
        });
    };
    onChangeTab = (key) => {

    };

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
        const TabPane = Tabs.TabPane;
        const FormItem = Form.Item;
        const {getFieldDecorator} = this.props.form;
        const {dark, surveyLoading, surveyStatus, currentUser} = this.props;

        return (
            <div className={styles.auth}>
                {currentUser ? null :
                    <Tabs defaultActiveKey="sign-in"
                          animated={{inkBar: true, tabPane: false}}
                          onChange={this.onChangeTab}
                          className={dark ? styles.tab_wrapper + ' ' + styles.dark : styles.tab_wrapper}>
                        <TabPane tab="Sign in" key="sign-in">
                            <Form onSubmit={this.handleSignInSubmit} className={styles.form}>
                                <FormItem>
                                    {getFieldDecorator('username', {
                                        rules: [{required: true, message: 'Please input your username!'}],
                                    })(
                                        <Input prefix={<Icon type="user" style={{fontSize: 14}}/>}
                                               placeholder="Username"/>
                                    )}
                                </FormItem>
                                <FormItem>
                                    {getFieldDecorator('password', {
                                        rules: [{required: true, message: 'Please input your password!'}],
                                    })(
                                        <Input prefix={<Icon type="lock" style={{fontSize: 14}}/>} type="password"
                                               placeholder="Password"/>
                                    )}
                                </FormItem>
                                <FormItem>
                                    {getFieldDecorator('remember', {
                                        valuePropName: 'checked',
                                        initialValue: true,
                                    })(
                                        <Checkbox className={styles.login_remember}>Remember me</Checkbox>
                                    )}
                                    <a className={styles.login_forgot} href="">Forgot password</a>
                                    <Button type="primary" htmlType="submit" loading={this.props.loading}
                                            className={styles.submit_button}>
                                        Sign in
                                    </Button>
                                </FormItem>
                            </Form>
                        </TabPane>
                        <TabPane tab="Sign up" key="sign-up">
                            <Form onSubmit={this.handleSignUpSubmit} className={styles.form}>
                                <FormItem>
                                    {getFieldDecorator('username', {
                                        rules: [{required: true, message: 'Please input your username!'}],
                                    })(
                                        <Input prefix={<Icon type="user" style={{fontSize: 14}}/>}
                                               placeholder="Username"/>
                                    )}
                                </FormItem>
                                <FormItem>
                                    {getFieldDecorator('password', {
                                        rules: [{required: true, message: 'Please input your password!'}],
                                    })(
                                        <Input prefix={<Icon type="lock" style={{fontSize: 14}}/>} type="password"
                                               placeholder="Password"/>
                                    )}
                                </FormItem>
                                <FormItem>
                                    <Button type="primary" htmlType="submit" loading={this.props.loading}
                                            className={styles.submit_button}>
                                        Sign up
                                    </Button>
                                </FormItem>
                            </Form>
                        </TabPane>
                    </Tabs>
                }

                <UserSurvey
                    visible={surveyStatus}
                    loading={surveyLoading}
                    handleOk={this.onSurveyOk}
                    handleCancel={this.onSurveyCancel}
                />
            </div>
        )
    }
}

function mapStateToProps(state) {
    const {surveyStatus, currentUser} = state.user;
    return {
        surveyLoading: state.loading.effects['user/postUserSurvey'],
        surveyStatus,
        currentUser,
    };
}


export default connect(mapStateToProps)(Form.create()(Auth));
