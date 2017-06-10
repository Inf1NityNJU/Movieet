import React from 'react';
import styles from './Avatar.css';

function Avatar({name = '', level, size = 'small', className = ''}) {
    return (
        <div className={styles.avatar + ' ' + className + ' ' +
        `bg_color_${name.charCodeAt(0) % 20 + 1 }`}
        >
              <span
                  className={size === 'large' ? styles.large : ''
                  + size ==='small' ? styles.small : ''
                  + size ==='mini' ? styles.mini : ''}
              >
                {name.charAt(0)}
              </span>
        </div>
    );
}

export default Avatar;
