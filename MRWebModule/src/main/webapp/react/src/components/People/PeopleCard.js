import React from 'react';
import styles from './PeopleCard.css';

import avatar from '../../assets/img/avatar.png';

function PeopleCard({ people }) {
  return (
    <div className={styles.card}>
      <div className={styles.avatar_wrapper}>
        <div className={styles.avatar} style={{backgroundImage: `url(${people.avatar})`}}></div>
      </div>
      <p className={styles.name}>{people.name}</p>
    </div>
  );
}

export default PeopleCard;
