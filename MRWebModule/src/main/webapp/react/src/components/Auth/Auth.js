import React, { Component } from 'react';
import { Tabs, Form, Icon, Input, Button, Checkbox } from 'antd';
import styles from './Auth.css';

class Auth extends Component {
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received values of form: ', values);
      }
    });
  };
  onChangeTab = (key) => {
    console.log(key);
  };

  render() {
    const TabPane = Tabs.TabPane;
    const FormItem = Form.Item;
    const { getFieldDecorator } = this.props.form;
    return (
      <div>
        <Tabs defaultActiveKey="sign-in"
              animated={{inkBar: true, tabPane: false}}
              onChange={this.onChangeTab}
              className={styles.tab_wapper}>
          <TabPane tab="Sign in" key="sign-in">
            <Form onSubmit={this.handleSubmit} className={styles.form}>
              <FormItem>
                {getFieldDecorator('userName', {
                  rules: [{required: true, message: 'Please input your username!'}],
                })(
                  <Input prefix={<Icon type="user" style={{ fontSize: 14 }} />} placeholder="Username"/>
                )}
              </FormItem>
              <FormItem>
                {getFieldDecorator('password', {
                  rules: [{required: true, message: 'Please input your Password!'}],
                })(
                  <Input prefix={<Icon type="lock" style={{ fontSize: 14 }} />} type="password" placeholder="Password"/>
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
                <Button type="primary" htmlType="submit" className={styles.submit_button}>
                  Sign in
                </Button>
              </FormItem>
            </Form>
          </TabPane>
          <TabPane tab="Sign up" key="sign-up">
            <Form onSubmit={this.handleSubmit} className={styles.form}>
              <FormItem>
                {getFieldDecorator('userName', {
                  rules: [{required: true, message: 'Please input your username!'}],
                })(
                  <Input prefix={<Icon type="user" style={{ fontSize: 14 }} />} placeholder="Username"/>
                )}
              </FormItem>
              <FormItem>
                {getFieldDecorator('password', {
                  rules: [{required: true, message: 'Please input your Password!'}],
                })(
                  <Input prefix={<Icon type="lock" style={{ fontSize: 14 }} />} type="password" placeholder="Password"/>
                )}
              </FormItem>
              <FormItem>
                <Button type="primary" htmlType="submit" className={styles.submit_button}>
                  Sign up
                </Button>
              </FormItem>
            </Form>
          </TabPane>
        </Tabs>

      </div>
    )
  }
}

export default Form.create()(Auth);
