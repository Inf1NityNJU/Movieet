import React from 'react';
import { Tag, Icon } from 'antd';

import styles from './SortButton.css';

const CheckableTag = Tag.CheckableTag;

function SortButton({ children, checked = false, order = 'DESC', onClick, onChange }) {

  const ORDER = {
    'ASC': 'up',
    'DESC': 'down'
  };

  return (
    <CheckableTag
      className="sort-button"
      checked={ checked }
      onClick={ onClick }
      onChange={ onChange }
    >
      { children }<Icon type={ORDER[order]}/>
    </CheckableTag>
  );
}

export default SortButton;
