import React from 'react';
import {connect} from 'dva';
import {Spin} from 'antd';

import {LIKE_SIZE} from '../../constants'

import ReviewList from '../ReviewList/ReviewList';
import MovieListSmall from '../MovieList/MovieListSmall';
import MovieScoreChart from '../Movie/MovieScoreChart';
import MovieRadarChart from '../Movie/MovieRadarChart';
import TipsPopover from '../Util/TipsPopover';

import styles from './MoviePage.css';

function MovieInfoPage({movie, reviews, similarMovies, user, similarLoading}) {
    return (
        <div className={styles.normal}>
            <div className="container">

                {movie ?
                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Storyline</h3>
                        </div>
                        <p className={styles.storyline}>
                            {movie.plot}
                        </p>
                        <p className={styles.storyline}>
                            {movie.plotCN}
                        </p>
                    </div> : null
                }

                {/*
                 <div className={styles.part}>
                 <div className={styles.title}>
                 <h3>Reviews</h3>
                 <Link className={styles.title_right} to="/movie/1/review">
                 More<Icon type="double-right"/>
                 </Link>
                 </div>
                 <ReviewList num={PREVIEW_REVIEW_SIZE}/>
                 </div>
                 */}
                {movie && (movie.scoreFR !== 0 || movie.scoreCN !== 0 ) ?
                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Area Score Distribution</h3>
                        </div>
                        <MovieScoreChart
                            movie={movie}
                        />
                    </div> : null
                }

                {movie && (movie.boxOffice !== 0 || movie.scoreFR !== 0 || movie.scoreCN !== 0 ) ?
                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Integrated Analysis</h3>

                            <div className={styles.title_right}>
                                <TipsPopover>
                                    <h6>雷达图</h6>
                                    <p>
                                        由于各个数值的标志值分布不均匀，国内外评分标准不同，不适用等距分组。通过对数据进行等比分组，将数值化为等级，来对电影进行多维度的评价。
                                    </p>
                                </TipsPopover>
                            </div>
                        </div>
                        <MovieRadarChart
                            movie={movie}
                        />
                    </div> : null
                }
            </div>

            <div className="background">
                <div className="container">
                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Similar Movies</h3>
                        </div>
                        {similarLoading ?
                            <div className={styles.spin}>
                                <Spin/>
                            </div> : null
                        }
                        {!similarLoading && similarMovies && similarMovies.length > 0 ?
                            <MovieListSmall
                                num={LIKE_SIZE}
                                list={similarMovies}
                            /> : null
                        }
                    </div>
                </div>
            </div>

        </div>
    )
}

function mapStateToProps(state) {
    const {movie, reviews, similarMovies, user} = state.movie;
    return {
        movie,
        reviews,
        similarMovies,
        user,
        similarLoading: state.loading.effects['movie/fetchSimilarMovies'],
    };
}

export default connect(mapStateToProps)(MovieInfoPage);
