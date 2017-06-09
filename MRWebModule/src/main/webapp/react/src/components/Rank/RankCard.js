import React from 'react';
import {Card} from 'antd';
import styles from './RankCard.css';


import example from '../../assets/img/example.png';

function RankCard({onClick, type, item, rank}) {

    const node = type === 'movie' ?
        <Card
            className={styles.card}
            onClick={onClick}
        >
            <span className={styles.rank}>{rank}</span>
            <div className={styles.photo_wrapper}>
                <div className={styles.photo} style={{backgroundImage: `url(${item.poster})`}}/>
            </div>
            <p className={styles.name}>{item.originTitle}</p>
        </Card>
        :
        <Card
            className={styles.card}
            onClick={onClick}
        >
            <span className={styles.rank}>{rank}</span>
            <div className={styles.photo_wrapper}>
                <div className={styles.photo} style={{backgroundImage: `url(${item.photo})`}}/>
            </div>
            <p className={styles.name}>{item.name}</p>
        </Card>;
    return (
        node
    );
}

export default RankCard;
