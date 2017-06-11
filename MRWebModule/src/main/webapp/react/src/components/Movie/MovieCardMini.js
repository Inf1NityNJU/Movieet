import React from 'react';
import {Card} from 'antd';

import BlankPhoto from '../Util/BlankPhoto';

import styles from './MovieCard.css';


function MovieCardMini({onClick, movie, rank}) {

    return (
        <Card className={styles.card + ' ' + styles.card_mini}
              onClick={onClick}>
            <div className={styles.img_wrapper}>
                {movie.poster ?
                    <div className={styles.img}
                         style={{backgroundImage: `url(${movie.poster})`}}/> :
                    <BlankPhoto className={styles.img}>
                        No Poster
                    </BlankPhoto>
                }
            </div>
            <div className={styles.text}>
                <h3 className={styles.title}>{movie.originTitle}</h3>
                <span className={styles.year}>({movie.releaseDate.split('.')[0]})</span>
                <span className={styles.score}>{movie.rankScore}</span>
            </div>
            <div className={styles.triangle}>
                <span>{rank}</span>
            </div>
        </Card>
    );
}

export default MovieCardMini;
