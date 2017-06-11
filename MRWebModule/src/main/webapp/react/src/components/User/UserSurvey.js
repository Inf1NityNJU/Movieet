import React, {Component} from 'react';
import {connect} from 'dva';
import {Modal, Form, Button, Rate, Tag} from 'antd';

import {GENRES} from '../../constants'

import styles from './UserSurvey.css';

const CheckableTag = Tag.CheckableTag;
const FormItem = Form.Item;

class UserSurvey extends Component {

  state = {
    genre: [],
    loading: false,
  };

  handleSubmit = () => {
    const {genre} = this.state;
    const values = genre;
    console.log('Received values of form: ', values);
    this.props.handleOk(genre);
  };

  handleGenreChange = (tag, checked) => {
    const {genre} = this.state;
    const nextGenre = checked ?
      [...genre, tag] :
      genre.filter(t => t !== tag);
    this.setState({genre: nextGenre});
  };


  render() {

    const {form, visible, loading, handleOk, handleCancel} = this.props;
    const {genre} = this.state;

    return (
      <Modal
        className={styles.survey}
        width={700}
        visible={visible}
        title="Likes..."
        onOk={handleOk}
        onCancel={handleCancel}
        footer={[
          <Button key="back" type="primary" size="large" ghost onClick={handleCancel}>Cancel</Button>,
          <Button key="submit" type="primary" size="large" loading={loading} onClick={this.handleSubmit}>
            Submit
          </Button>,
        ]}
      >

        <Form className={styles.form}>

          <FormItem
            label="Genres you like"
          >
            <div className={styles.tag_wrapper}>
              {GENRES ?
                GENRES.slice(1, GENRES.length)
                  .map((k) =>
                    <CheckableTag
                      key={k.id}
                      checked={genre.indexOf(k.id) > -1}
                      onChange={checked => this.handleGenreChange(k.id, checked)}
                    >
                      {k.value}
                    </CheckableTag>
                  ) : null
              }
            </div>
          </FormItem>

        </Form>

      </Modal>
    );
  }
}

export default connect()(Form.create()(UserSurvey));
