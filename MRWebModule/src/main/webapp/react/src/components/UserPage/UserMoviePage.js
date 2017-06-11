import React from 'react';
import {connect} from 'dva';
import MathJax from 'react-mathjax';

import {Icon, Pagination, Spin} from 'antd';
import {routerRedux} from 'dva/router';

import MovieListSmall from '../MovieList/MovieListSmall';
import TipsPopover from '../Util/TipsPopover';

import styles from './UserPage.css';

import {
    PREVIEW_COLLECT_SIZE, PREVIEW_EVALUATE_SIZE,
    COLLECT_SIZE, EVALUATE_SIZE, RECOMMEND_SIZE
} from '../../constants'

function UserMoviePage({dispatch, user, currentUser, recommend, status, result, page, totalCount, collectLoading, evaluateLoading, recommendLoading}) {

    function onMoreClick(status) {
        dispatch(routerRedux.push({
            pathname: '/user/' + user.id + '/movie/' + status
        }));
    }

    function onPageChange(page) {
        dispatch({
            type: 'user/changeMoviePage',
            payload: page
        });
    }


    return (
        <div className={styles.movie_page}>

            { (status === null || status === 'collect') ?

                <div className={styles.part}>
                    <div className={styles.title}>
                        <h3>Want to watch</h3>
                        {
                            status === 'collect' ? null :
                                <a className={styles.title_right}
                                   onClick={() => onMoreClick("collect")}
                                >
                                    More<Icon type="double-right"/>
                                </a>
                        }
                    </div>
                    {collectLoading ?
                        <div className={styles.spin}>
                            <Spin/>
                        </div> : null
                    }
                    { !collectLoading && result.collect && result.collect.length > 0 ?
                        <MovieListSmall
                            num={status === 'collect' ? COLLECT_SIZE : PREVIEW_COLLECT_SIZE}
                            list={result.collect}
                        /> : null
                    }

                    { status === 'collect' ?
                        <Pagination
                            className={styles.page}
                            showQuickJumper
                            defaultCurrent={1}
                            pageSize={ COLLECT_SIZE }
                            total={totalCount}
                            current={page}
                            onChange={onPageChange}/>
                        : null
                    }
                </div>
                : null
            }
            { (status === null || status === 'evaluate') ?
                <div className={styles.part}>
                    <div className={styles.title}>
                        <h3>Had watched</h3>
                        {
                            status === 'evaluate' ? null :
                                <a className={styles.title_right}
                                   onClick={() => onMoreClick("evaluate")}
                                >
                                    More<Icon type="double-right"/>
                                </a>
                        }
                    </div>
                    {evaluateLoading ?
                        <div className={styles.spin}>
                            <Spin/>
                        </div> : null
                    }
                    {!evaluateLoading && result.evaluate && result.evaluate.length > 0 ?
                        <MovieListSmall
                            num={status === 'evaluate' ? EVALUATE_SIZE : PREVIEW_EVALUATE_SIZE}
                            list={result.evaluate}
                        /> : null
                    }
                    {
                        status === 'evaluate' ?
                            <Pagination
                                className={styles.page}
                                showQuickJumper
                                defaultCurrent={1}
                                pageSize={ EVALUATE_SIZE }
                                total={totalCount}
                                current={page}
                                onChange={onPageChange}/>
                            : null
                    }
                </div>
                : null
            }


            { currentUser && user && currentUser.id === user.id ?
                <div className={styles.part}>
                    <div className={styles.title}>
                        <h3>People like you are watching</h3>
                        <div className={styles.title_right}>
                            <TipsPopover>
                                <h6>相似用户电影推荐</h6>
                                <div>
                                    <MathJax.Context>
                                        <div>
                                            <p>用户相似度</p>
                                            <MathJax.Node>
                                                {`S_{x, y} = \\frac{ \\sum_{i=0}^{n_x} \\min ({ x_i, y_i })  } { \\sum_{i=0}^{n_x} x_i}`}
                                            </MathJax.Node>
                                            <MathJax.Node>
                                                {`U = \\max \\{S_1, S_2, S_3, \\dots \\}`}
                                            </MathJax.Node>
                                            <p>
                                                <MathJax.Node inline>{`n_x`}</MathJax.Node> - 用户<MathJax.Node inline>{`x`}</MathJax.Node>因子个数
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`x_i`}</MathJax.Node> - 用户<MathJax.Node inline>{`x`}</MathJax.Node>的特征因子<MathJax.Node inline>{`i`}</MathJax.Node>的值，表示对该因子喜好程度
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`y_i`}</MathJax.Node> - 用户<MathJax.Node inline>{`y`}</MathJax.Node>的特征因子<MathJax.Node inline>{`i`}</MathJax.Node>的值，表示对该因子喜好程度
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`S_{x,y}`}</MathJax.Node> - 用户<MathJax.Node inline>{`y`}</MathJax.Node>对用户<MathJax.Node inline>{`x`}</MathJax.Node>的相似度
                                            </p>
                                            <p>
                                                <MathJax.Node inline>{`U`}</MathJax.Node> - 相似用户合集
                                            </p>
                                        </div>
                                    </MathJax.Context>
                                    <div>
                                        <p>通过极端用户的相似度来推荐相似度高的用户收藏或评价的电影</p>
                                    </div>
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
                        <MovieListSmall
                            num={RECOMMEND_SIZE}
                            list={recommend}
                        /> : null
                    }
                </div>
                : null
            }
        </div>
    )
}

function mapStateToProps(state) {
    const {user, currentUser, recommend, movie} = state.user;
    return {
        user,
        currentUser,
        recommend,
        status: movie.status,
        result: movie.result,
        page: movie.page,
        totalCount: movie.totalCount,
        collectLoading: state.loading.effects['user/fetchCollectMovies'],
        evaluateLoading: state.loading.effects['user/fetchEvaluateMovies'],
        recommendLoading: state.loading.effects['user/fetchUserRecommend'],
    };
}


export default connect(mapStateToProps)(UserMoviePage);
