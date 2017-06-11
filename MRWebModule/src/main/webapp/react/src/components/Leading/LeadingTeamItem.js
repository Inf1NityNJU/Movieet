import React from 'react';
import styles from './LeadingTeamItem.css';

function LeadingTeamItem({title, description, photo, className = ''}) {
  return (
      <div className={styles.item + ' ' + className}>
          <div className={styles.photo_wrapper}>
              <div className={styles.photo} style={{backgroundImage: `url(${photo})`}}/>
          </div>
          <div className={styles.text_wrapper}>
              <h6 className={styles.title}>{title}</h6>
              <p className={styles.description}>{description}</p>
          </div>
      </div>
  );
}

export default LeadingTeamItem;
