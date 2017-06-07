import React from 'react';
import styles from './RankCard.css';


import example from '../../assets/img/example.png';

function RankCard({onClick, item}) {
    return (
        <div
            className={styles.card}
            onClick={onClick}
        >
            <div className={styles.photo_wrapper}>
                <div className={styles.photo} style={{backgroundImage: `url(${example})`}}/>
            </div>
            <p className={styles.name}>name</p>
        </div>
    );
}

export default RankCard;
