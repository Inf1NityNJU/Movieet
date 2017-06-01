import React, {Component} from 'react';
import {Checkbox, Icon} from 'antd';

import styles from './ElementItem.css';


class ElementItem extends Component {

  state = {
    close: false,
  };

  handleMouseEnter = () => {
    this.setState({
      close: true,
    })
  };

  handleMouseLeave = () => {
    this.setState({
      close: false,
    })
  };


  render() {
    return (
      <div className={styles.item}
      onMouseEnter={this.handleMouseEnter}
      onMouseLeave={this.handleMouseLeave}>

        <Checkbox>
          name
        </Checkbox>
        {this.state.close ?
          <Icon type="close"/> : null
        }
      </div>
    );
  }
}


export default ElementItem;
