import React from 'react';
import { Card } from 'antd';
import styles from './RankCard.css';


import example from '../../assets/img/example.png';

function RankCard({onClick, item}) {
    return (
        <Card
            className={styles.card}
            onClick={onClick}
        >
            <span className={styles.rank}>11</span>
            <div className={styles.photo_wrapper}>
                <div className={styles.photo} style={{backgroundImage: `url(${example})`}}/>
            </div>
            <p className={styles.name}>name</p>
        </Card>
    );
}

export default RankCard;
