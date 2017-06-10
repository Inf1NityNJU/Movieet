import React, {Component} from 'react';
import {Card, Rate, Tag} from 'antd';

import BlankPhoto from '../Util/BlankPhoto';

import styles from './MovieCard.css';

function MovieCardLarge({movie, onClick}) {


    return (
        <Card className={styles.card + ' ' + styles.card_large}
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
                <h3>{movie.originTitle}</h3>
                <div className={styles.tags}>
                    {
                        movie.genre.map((genre) =>
                            <Tag key={genre.id}>{genre.value}</Tag>
                        )
                    }
                </div>
                <div className={styles.bottom}>
                    <p className={styles.date}>{movie.releaseDate}</p>

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
                            <span className={styles.count}>({movie.votesFR})</span>
                        </div>
                    }
                </div>
            </div>
        </Card>
    );

}

export default MovieCardLarge;
