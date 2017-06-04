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
    const {item, onCheckChange, onItemRemove} = this.props;

    return (
      <div className={styles.item}
           onMouseEnter={this.handleMouseEnter}
           onMouseLeave={this.handleMouseLeave}>

        <Checkbox
          checked={item.checked}
          onChange={() => onCheckChange(item.id, !item.checked)}
        >
          {item.value}
        </Checkbox>
        {this.state.close ?
          <Icon
            type="close"
            onClick={() => onItemRemove(item.id)}
          /> : null
        }
      </div>
    );
  }
}


export default ElementItem;
