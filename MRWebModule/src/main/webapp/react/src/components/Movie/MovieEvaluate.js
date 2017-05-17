import React from 'react';
import { connect } from 'dva';
import { Modal, Form, Button, Rate, Input, Checkbox, Icon } from 'antd';

import styles from './MovieEvaluate.css';

function MovieEvaluate({ form, visible, loading, handleOk, handleCancel }) {

  function handleSubmit() {
    form.validateFields((err, values) => {
      if (!err) {
        console.log('Sign in: Received values of form: ', values);
        handleOk(values);
      }
    });
  }

  function handleChange(props, values) {
    //console.log(props);
    //console.log(values);
    //form.validateFields((err, values) => {
    //  if (!err) {
    //    console.log('Sign in: Received values of form: ', values);
    //  }
    //  console.log('Sign in: Received values of form: ', values);
    //});

  }

  const FormItem = Form.Item;
  const { getFieldDecorator } = form;

  return (
    <Modal
      visible={visible}
      title="Evaluate"
      onOk={handleOk}
      onCancel={handleCancel}
      footer={[
            <Button key="back" type="primary" size="large" ghost onClick={handleCancel}>Cancel</Button>,
            <Button key="submit" type="primary" size="large" loading={loading} onClick={handleSubmit}>
              Submit
            </Button>,
          ]}
    >

      <Form onChange={handleChange} className={styles.form}>
        <FormItem>
          {getFieldDecorator('score', {
            rules: [{required: true, message: 'Please rate the movie!'}],
            initialValue: 5,
          })(
            <Rate allowHalf className={styles.rate}/>
          )}
        </FormItem>

        <FormItem>
          {getFieldDecorator('genre', {
            valuePropName: 'checked',
            initialValue: false,
          })(
            <Checkbox className={styles.checkbox}>I like the movie's genre!</Checkbox>
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('director', {
            valuePropName: 'checked',
            initialValue: false,
          })(
            <Checkbox className={styles.checkbox}>I like the movie's directors!</Checkbox>
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('actor', {
            valuePropName: 'checked',
            initialValue: false,
          })(
            <Checkbox className={styles.checkbox}>I like the movie's actors!</Checkbox>
          )}
        </FormItem>
      </Form>

    </Modal>
  );
}

export default connect()(Form.create()(MovieEvaluate));
