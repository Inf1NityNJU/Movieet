import React, {Component} from 'react';
import {Card, Rate} from 'antd';

import BlankPhoto from '../Util/BlankPhoto';

import styles from './MovieCard.css';

function MovieCardSmall({movie, onClick}) {

    return (
        <Card className={styles.card + ' ' + styles.card_small}
              onClick={onClick}>
            <div className={styles.img_wrapper}>
                {movie.poster ?
                    <div className={styles.img} style={{backgroundImage: `url(${movie.poster})`}}/> :
                    <BlankPhoto className={styles.img}>
                        No Poster
                    </BlankPhoto>
                }
            </div>
            <div className={styles.text}>
                <h3 className={styles.title}>{movie.originTitle}</h3>
                {movie.scoreFR === 0 ? <span className={styles.score}>No Score</span> :
                    <div>
                        <Rate
                            className={styles.rate}
                            disabled
                            allowHalf
                            value={movie.scoreFR % 2 > 1 ?
                                Math.floor(movie.scoreFR / 2) + 0.5 :
                                Math.floor(movie.scoreFR / 2)}
                        />

                        <span className={styles.score}>{movie.scoreFR}</span>
                    </div>
                }
            </div>
        </Card>
    );
}

export default MovieCardSmall;
