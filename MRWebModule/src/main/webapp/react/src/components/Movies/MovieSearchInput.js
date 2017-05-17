import React, { Component } from 'react';
import { Icon, Input } from 'antd';

import styles from './MovieSearchInput.css';

class MovieSearchInput extends Component {

  constructor(props) {
    super(props);
    this.state = {
      width: '80%',
    };
  }

  emitEmpty = (e) => {
    e.preventDefault();
    this.refs.input.focus();
    this.props.onEnter('');
  };

  onChange = (e) => {
    this.props.onChange(e.target.value);
  };

  onEnter = (e) => {

  };

  onInputFocus = (e) => {
    this.setState({
      width: '100%',
    });
  };
  onInputBlur = (e) => {
    this.setState({
      width: '80%',
    });
  };

  render() {
    const { keyword } = this.props;
    const suffix = keyword ? <Icon type="close-circle" onClick={this.emitEmpty}/> : null;
    return (
      <div className={styles.search_wrapper} style={{width: this.state.width}}>
        <Input
          className={styles.search_input}
          size="large"
          placeholder="movie / actor / director "
          prefix={<Icon type="search" />}
          suffix={suffix}
          value={keyword}
          onChange={this.onChange}
          onFocus={this.onInputFocus}
          onBlur={this.onInputBlur}
          onPressEnter={() => this.props.onEnter(keyword)}
          ref="input"
        />
      </div>
    );
  }
}


export default MovieSearchInput;
