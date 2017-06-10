import React from 'react';
import styles from './BlankPhoto.css';

function BlankPhoto({ children, size='small', className = ''}) {
  return (
    <div className={styles.photo + ' ' + className}>
      <div
          className={size === 'large' ? styles.large : ''
          + size ==='small' ? styles.small : ''
          + size ==='mini' ? styles.mini : ''}
      >
          {children}
      </div>
    </div>
  );
}

export default BlankPhoto;
