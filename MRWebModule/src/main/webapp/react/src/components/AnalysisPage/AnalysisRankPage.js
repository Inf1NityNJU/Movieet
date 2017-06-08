import React from 'react';
import {Icon, Spin} from 'antd';
import {connect} from 'dva';

import styles from './AnalysisPage.css';

import MovieListMini from '../MovieList/MovieListMini';
import RankList from '../Rank/RankList';

function AnalysisRankPage({dispatch, status, moviesFR, moviesCN, moviesFRLoading, moviesCNLoading}) {
    function onClickMore(type) {

        dispatch({
            type: 'analysis/saveMore' + type,
            payload: true,
        });
    }

    function onClickWrap(type) {
        dispatch({
            type: 'analysis/saveMore' + type,
            payload: false,
        });
    }

    return (
        <div className={styles.normal + ' ' + styles.rank}>
            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Movies In Foreign</h3>
                </div>
                {moviesFRLoading ?
                    <div className={styles.spin}>
                        <Spin/>
                    </div> : null
                }

                {!moviesFRLoading ?
                    <div>
                        { moviesFR ?
                            <MovieListMini
                                num={5}
                                list={moviesFR.slice(0, 5)}
                            /> : null
                        }


                        {status.moreFR ? null :
                            <span
                                className={styles.more}
                                onClick={() => onClickMore('FR')}
                            >
                        Ranking 6~50
                        <Icon type="down"/>
                    </span>
                        }

                        {moviesFR && moviesFR.length>5 && status.moreFR ?
                            <div className={styles.more_list}>
                                <RankList
                                    type="movie"
                                    num={45}
                                    list={moviesFR.slice(5, moviesFR.length)}
                                    start={5}
                                />
                            </div> : null
                        }
                        {status.moreFR ?
                            <span
                                className={styles.more}
                                onClick={() => onClickWrap('FR')}
                            >
                        <Icon type="up"/>
                        Wrap Ranking 6~50
                    </span> : null
                        }
                    </div> : null
                }

            </div>

            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Movies In Domestic</h3>
                </div>

                {moviesCNLoading ?
                    <div className={styles.spin}>
                        <Spin/>
                    </div> : null
                }

                {!moviesCNLoading ?
                    <div>
                        {moviesCN ?
                            <MovieListMini
                                num={5}
                                list={moviesCN.slice(0, 5)}
                            /> : null
                        }
                        {status.moreCN ? null :
                            <span
                                className={styles.more}
                                onClick={() => onClickMore('CN')}
                            >
                        Ranking 6~50
                        <Icon type="down"/>
                    </span>
                        }
                        {moviesCN && moviesCN.length > 5 && status.moreCN ?
                            <div className={styles.more_list}>
                                <RankList
                                    type="movie"
                                    num={45}
                                    list={moviesCN.slice(5, moviesCN.length)}
                                    start={5}
                                />
                            </div> : null
                        }
                        {status.moreCN ?
                            <span
                                className={styles.more}
                                onClick={() => onClickWrap('CN')}
                            >
                        <Icon type="up"/>
                        Wrap Ranking 6~50
                    </span> : null
                        }
                    </div> : null
                }

            </div>

            {/*<div className={styles.part}>*/}
                {/*<div className={styles.title}>*/}
                    {/*<h3>Directors</h3>*/}
                {/*</div>*/}
                {/*<RankList num={15}/>*/}
            {/*</div>*/}
        </div>
    );

}
function mapStateToProps(state) {
    const {rank} = state.analysis;
    return {
        status: rank.status,
        moviesFR: rank.moviesFR,
        moviesCN: rank.moviesCN,
        moviesFRLoading: state.loading.effects['analysis/fetchRankMovieFR'],
        moviesCNLoading: state.loading.effects['analysis/fetchRankMovieCN']
    };
}

export default connect(mapStateToProps)(AnalysisRankPage);
