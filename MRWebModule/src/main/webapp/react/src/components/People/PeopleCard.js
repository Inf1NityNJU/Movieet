import React from 'react';

import BlankPhoto from '../Util/BlankPhoto';

import styles from './PeopleCard.css';

function PeopleCard({onClick, people}) {
  return (
    <div
      className={styles.card}
      onClick={onClick}
    >
      <div className={styles.avatar_wrapper}>
          {people.photo ?
              <div className={styles.avatar} style={{backgroundImage: `url(${people.photo})`}}/> :
              <BlankPhoto
                  className={styles.avatar}
                  size="mini"
              >
                No Photo
              </BlankPhoto>
          }
      </div>
      <p className={styles.name}>{people.name}</p>
    </div>
  );
}

export default PeopleCard;
