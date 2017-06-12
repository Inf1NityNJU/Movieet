import React from 'react';
import {Icon, Spin} from 'antd';
import {connect} from 'dva';
import MathJax from 'react-mathjax';

import styles from './AnalysisPage.css';

import MovieListMini from '../MovieList/MovieListMini';
import RankList from '../Rank/RankList';
import TipsPopover from '../Util/TipsPopover';

import {RANK_MOVIE_SIZE, RANK_PEOPLE_SIZE} from '../../constants'


function AnalysisRankPage({dispatch, status, moviesFR, moviesCN, director, actor, moviesFRLoading, moviesCNLoading, directorLoading, actorLoading}) {
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
                    <div className={styles.title_right}>
                        <TipsPopover>
                            <h6>电影排名</h6>
                            <div>
                                <p>
                                    使用了贝叶斯平均算法，通过用户投票来计算电影的排名。
                                </p>
                            </div>
                            <div>
                                <MathJax.Context>
                                    <div>
                                        <MathJax.Node>
                                            {`WR = \\frac{v}{v+m}R + \\frac{m}{v+m}C`}
                                        </MathJax.Node>
                                        <p>
                                            <MathJax.Node inline>{`WR`}</MathJax.Node> - 加权得分（weighted rating）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`R`}</MathJax.Node> - 该电影的用户投票的平均得分（Rating）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`v`}</MathJax.Node> - 该电影的投票人数（votes）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`m`}</MathJax.Node> - 需要的最低投票数（现在为10000）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`C`}</MathJax.Node> - 所有电影的平均得分（现在为6.27）
                                        </p>
                                    </div>
                                </MathJax.Context>
                            </div>
                            <div>

                                <p>
                                    排名计算的分数和原始分数不一致。
                                </p>
                                <p>
                                    通过国内外的用户投票来识别国内外对电影的喜好。
                                </p>
                            </div>
                        </TipsPopover>
                    </div>
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

                        {moviesFR && moviesFR.length > 5 && status.moreFR ?
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
                    <div className={styles.title_right}>
                        <TipsPopover>
                            <h6>电影排名</h6>
                            <div>
                                <p>
                                    使用了贝叶斯平均算法，通过用户投票来计算电影的排名。
                                </p>
                            </div>
                            <div>
                                <MathJax.Context>
                                    <div>
                                        <MathJax.Node>
                                            {`WR = \\frac{v}{v+m}R + \\frac{m}{v+m}C`}
                                        </MathJax.Node>
                                        <p>
                                            <MathJax.Node inline>{`WR`}</MathJax.Node> - 加权得分（weighted rating）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`R`}</MathJax.Node> - 该电影的用户投票的平均得分（Rating）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`v`}</MathJax.Node> - 该电影的投票人数（votes）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`m`}</MathJax.Node> - 需要的最低投票数（现在为10000）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`C`}</MathJax.Node> - 所有电影的平均得分（现在为6.89）
                                        </p>
                                    </div>
                                </MathJax.Context>
                            </div>
                            <div>

                                <p>
                                    排名计算的分数和原始分数不一致。
                                </p>
                                <p>
                                    通过国内外的用户投票来识别国内外对电影的喜好。
                                </p>
                            </div>
                        </TipsPopover>
                    </div>
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

            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Directors</h3>
                    <div className={styles.title_right}>
                        <TipsPopover>
                            <h6>导演排名</h6>
                            <div>
                                <p>
                                    使用了贝叶斯平均算法，通过用户投票来计算电影的排名。
                                </p>
                            </div>
                            <div>
                                <MathJax.Context>
                                    <div>
                                        <MathJax.Node>
                                            {`WR = \\frac{C*m+ \\sum_{i=1}^n x_i*v_i}{n+C}`}
                                        </MathJax.Node>
                                        <p>
                                            <MathJax.Node inline>{`WR`}</MathJax.Node> - 加权得分（weighted rating）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`n`}</MathJax.Node> - 该导演的电影数量
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`x`}</MathJax.Node> - 电影的平均分
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`v`}</MathJax.Node> - 电影的投票人数
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`m`}</MathJax.Node> - 所有的电影的 Votes * Score 的平均值（现在为68658）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`C`}</MathJax.Node> - 所有导演的平均电影数量（现在为2）
                                        </p>
                                    </div>
                                </MathJax.Context>
                            </div>
                            <div>
                                <p>
                                    贝叶斯平均算法：先估计一个值，然后不断用新的信息修正，使得它越来越接近正确的值。
                                    m（总体平均分）是"先验概率"，每一次新的投票都是一个调整因子，使总体平均分不断向该项目的真实投票结果靠近。
                                    投票人数越多，该项目的"贝叶斯平均"就越接近算术平均，对排名的影响就越小。
                                </p>
                            </div>
                        </TipsPopover>
                    </div>
                </div>
                {directorLoading ?
                    <div className={styles.spin}>
                        <Spin/>
                    </div> : null
                }

                {!directorLoading && director ?
                    <RankList
                        type="director"
                        num={RANK_PEOPLE_SIZE}
                        list={director}
                    /> : null
                }
            </div>

            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Actors</h3>
                    <div className={styles.title_right}>
                        <TipsPopover>
                            <h6>演员排名</h6>
                            <div>
                                <p>
                                    使用了贝叶斯平均算法，通过用户投票来计算电影的排名。
                                </p>
                            </div>
                            <div>
                                <MathJax.Context>
                                    <div>
                                        <MathJax.Node>
                                            {`WR = \\frac{C*m+ \\sum_{i=1}^n x_i*v_i}{n+C}`}
                                        </MathJax.Node>
                                        <p>
                                            <MathJax.Node inline>{`WR`}</MathJax.Node> - 加权得分（weighted rating）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`n`}</MathJax.Node> - 该演员的电影数量
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`x`}</MathJax.Node> - 电影的平均分
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`v`}</MathJax.Node> - 电影的投票人数
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`m`}</MathJax.Node> - 所有的电影的 Votes * Score 的平均值（现在为68658）
                                        </p>
                                        <p>
                                            <MathJax.Node inline>{`C`}</MathJax.Node> - 所有演员的平均电影数量（现在为2）
                                        </p>
                                    </div>
                                </MathJax.Context>
                            </div>
                            <div>
                                <p>
                                    贝叶斯平均算法：先估计一个值，然后不断用新的信息修正，使得它越来越接近正确的值。
                                    m（总体平均分）是"先验概率"，每一次新的投票都是一个调整因子，使总体平均分不断向该项目的真实投票结果靠近。
                                    投票人数越多，该项目的"贝叶斯平均"就越接近算术平均，对排名的影响就越小。
                                </p>
                            </div>
                        </TipsPopover>
                    </div>
                </div>
                {actorLoading ?
                    <div className={styles.spin}>
                        <Spin/>
                    </div> : null
                }

                {!actorLoading && actor ?
                    <RankList
                        type="actor"
                        num={RANK_PEOPLE_SIZE}
                        list={actor}
                    /> : null
                }
            </div>
        </div>
    );

}
function mapStateToProps(state) {
    const {rank} = state.analysis;
    return {
        status: rank.status,
        moviesFR: rank.moviesFR,
        moviesCN: rank.moviesCN,
        director: rank.director,
        actor: rank.actor,
        moviesFRLoading: state.loading.effects['analysis/fetchRankMovieFR'],
        moviesCNLoading: state.loading.effects['analysis/fetchRankMovieCN'],
        directorLoading: state.loading.effects['analysis/fetchRankDirector'],
        actorLoading: state.loading.effects['analysis/fetchRankActor']
    };
}

export default connect(mapStateToProps)(AnalysisRankPage);
