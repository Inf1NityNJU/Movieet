import React from 'react';
import styles from './FriendCard.css';

import avatar from '../../assets/img/avatar.png';

function FriendCard({ friend, onClick }) {
  return (
    <div
      className={styles.card}
      onClick={onClick}
    >
      <div className={styles.avatar_wrapper}>
        <div className={styles.avatar} style={{backgroundImage: `url(${avatar})`}} />
      </div>
      <p className={styles.name}>{friend.username}</p>
    </div>
  );
}

export default FriendCard;
