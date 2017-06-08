import React from 'react';
import {Icon} from 'antd';
import {connect} from 'dva';

import styles from './AnalysisPage.css';

import MovieListMini from '../MovieList/MovieListMini';
import RankList from '../Rank/RankList';

function AnalysisRankPage({dispatch, status}) {
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
                <MovieListMini num={5}/>

                {status.moreFR ? null :
                    <span
                        className={styles.more}
                        onClick={() => onClickMore('FR')}
                    >
                        Ranking 6~50
                        <Icon type="down"/>
                    </span>
                }
                {status.moreFR ?
                    <div className={styles.more_list}>
                        <RankList num={15}/>
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


            </div>

            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Movies In Domestic</h3>
                </div>
                <MovieListMini num={5}/>
                {status.moreCN ? null :
                    <span
                        className={styles.more}
                        onClick={() => onClickMore('CN')}
                    >
                        Ranking 6~50
                        <Icon type="down"/>
                    </span>
                }
                {status.moreCN ?
                    <div className={styles.more_list}>
                        <RankList num={15}/>
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

            </div>

            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Directors</h3>
                </div>
                <RankList num={15}/>
            </div>
        </div>
    );

}
function mapStateToProps(state) {
    const {rank} = state.analysis;
    return {
        status: rank.status,
    };
}

export default connect(mapStateToProps)(AnalysisRankPage);
