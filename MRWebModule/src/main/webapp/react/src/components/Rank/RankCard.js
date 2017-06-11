import React from 'react';
import {Card} from 'antd';

import BlankPhoto from '../Util/BlankPhoto';

import styles from './RankCard.css';

function RankCard({onClick, type, item, rank}) {

    const node = type === 'movie' ?
        <Card
            className={styles.card}
            onClick={onClick}
        >
            <span className={styles.rank}>{rank}</span>
            <div className={styles.photo_wrapper}>
                {item.poster ?
                    <div className={styles.photo} style={{backgroundImage: `url(${item.poster})`}}/> :
                    <BlankPhoto
                        className={styles.photo}
                        size="mini"
                    >
                        No Photo
                    </BlankPhoto>
                }
            </div>

            <div className={styles.text}>
                <p className={styles.name}>{item.originTitle}</p>
                <span className={styles.year}>({item.releaseDate.split('.')[0]})</span>
                <span className={styles.score}>{item.rankScore}</span>
            </div>
        </Card>
        :
        <Card
            className={styles.card}
            onClick={onClick}
        >
            <span className={styles.rank}>{rank}</span>
            <div className={styles.photo_wrapper}>
                {item.photo ?
                    <div className={styles.photo} style={{backgroundImage: `url(${item.photo})`}}/> :
                    <BlankPhoto
                        className={styles.photo}
                        size="mini"
                    >
                        No Photo
                    </BlankPhoto>
                }
            </div>
            <div className={styles.text}>
                <p className={styles.name}>{item.name}</p>
            </div>
        </Card>;
    return (
        node
    );
}

export default RankCard;
