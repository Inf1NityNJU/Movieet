import React from 'react';
import styles from './PeopleCard.css';


function PeopleCard({onClick, people}) {
  return (
    <div
      className={styles.card}
      onClick={onClick}
    >
      <div className={styles.avatar_wrapper}>
        <div className={styles.avatar} style={{backgroundImage: `url(${people.photo})`}}/>
      </div>
      <p className={styles.name}>{people.name}</p>
    </div>
  );
}

export default PeopleCard;
