import React from 'react';
import {connect} from 'dva';
import MathJax from 'react-mathjax';

import {Spin} from 'antd';

import {NEW_RELEASED_SIZE, RECOMMEND_SIZE} from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';
import MovieListLarge from '../MovieList/MovieListLarge';
import TipsPopover from '../Util/TipsPopover';

import styles from './MoviePage.css';

function MovieDiscoverPage({user, newReleased, recommend, newReleasedLoading, recommendLoading}) {
    return (
        <div className={styles.discover_page}>
            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>New Released</h3>
                </div>
                {newReleasedLoading ?
                    <div className={styles.spin}>
                        <Spin/>
                    </div> : null
                }
                {!newReleasedLoading && newReleased && newReleased.length > 0 ?
                    <MovieListSmall
                        num={NEW_RELEASED_SIZE}
                        list={newReleased}
                    /> : null
                }
            </div>

            {user ?
                <div className={styles.part}>
                    <div className={styles.title}>
                        <h3>Recommend</h3>
                        <div className={styles.title_right}>
                            <TipsPopover>
                                <h6>综合推荐：基于用户历史行为及主观行为</h6>
                                <div>
                                    <MathJax.Context>
                                        <div>
                                            <MathJax.Node>
                                                {`F_k = \\sum_{i=1}^n (x_i * y_i)`}
                                            </MathJax.Node>
                                            <MathJax.Node>
                                                {`M = \\max \\{F_1, F_2, F_3, \\dots \\}`}
                                            </MathJax.Node>
                                            <p>
                                                <MathJax.Node inline>{`F_k`}</MathJax.Node> - 用户对电影<MathJax.Node inline>{`k`}</MathJax.Node>的喜好值
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`n`}</MathJax.Node> - 因子个数
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`x_i`}</MathJax.Node> - 电影的特征因子<MathJax.Node inline>{`i`}</MathJax.Node>的值
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`y_i`}</MathJax.Node> - 用户的特征因子<MathJax.Node inline>{`i`}</MathJax.Node>的值，表示对该因子喜好程度
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`M`}</MathJax.Node> - 推荐电影
                                            </p>
                                        </div>
                                    </MathJax.Context>
                                </div>
                            </TipsPopover>
                        </div>

                    </div>
                    {recommendLoading ?
                        <div className={styles.spin}>
                            <Spin/>
                        </div> : null
                    }
                    {!recommendLoading && recommend && recommend.length > 0 ?
                        <MovieListLarge
                            num={RECOMMEND_SIZE}
                            list={recommend}
                        /> : null
                    }
                </div> : null
            }


        </div>
    )
}

function mapStateToProps(state) {
    const {discover} = state.movies;
    return {
        user: state.user.currentUser,
        newReleased: discover.newReleased,
        recommend: discover.recommend,
        newReleasedLoading: state.loading.effects['movies/fetchLatestMovies'],
        recommendLoading: state.loading.effects['movies/fetchRecommendMovies']
    };
}

export default connect(mapStateToProps)(MovieDiscoverPage);
