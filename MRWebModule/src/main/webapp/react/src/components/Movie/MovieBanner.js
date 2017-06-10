import React from 'react';
import { connect } from 'dva';
import {Rate, Tag} from 'antd';
import { routerRedux } from 'dva/router';
import styles from './MovieBanner.css';

import bg from '../../assets/img/bg1.png';
import {MOVIE_SORT, ORDER} from '../../constants'


function MovieBanner({dispatch, movie}) {

    function onClickGenre(id) {
        dispatch({
            type: 'movies/saveGenres',
            payload: [id],
        });
        dispatch({
            type: 'movies/saveSort',
            payload: {
                name: MOVIE_SORT[0],
                order: ORDER[1],
            },
        });
        dispatch(routerRedux.push({
            pathname: '/movies/category',
        }));
    }

    return (

        <div className={styles.banner}>
            <div className={styles.bg}>
                <div className={styles.bg_wrapper}>
                    <div className={styles.bg_img}
                         style={{backgroundImage: `url(${movie.backgroundPoster ? movie.backgroundPoster : bg})`}}/>
                    <div className={styles.overlay}/>
                </div>
            </div>

            <div className={styles.text}>
                <div className="container">
                    <p className={styles.date}>{movie.releaseDate}</p>
                    <h1>{movie.originTitle}</h1>
                    { movie.title !== movie.originTitle ?
                        <p className={styles.alias}>{movie.title}</p>
                        : null
                    }
                    { movie.titleCN !== movie.originTitle ?
                        <p className={styles.alias}>{movie.titleCN}</p>
                        : null
                    }
                    <div className={styles.genre_tags}>
                        {movie.genre ?
                            movie.genre.map((genre) =>
                                <Tag
                                    key={genre.id}
                                    onClick={() => onClickGenre(genre.id)}
                                >
                                    {genre.value}
                                </Tag>
                            ) : null
                        }
                    </div>
                </div>
            </div>

        </div>
    );
}

function mapStateToProps(state) {
    const { movie } = state.movie;
    return {
        movie
    };
}

export default connect(mapStateToProps)(MovieBanner);
