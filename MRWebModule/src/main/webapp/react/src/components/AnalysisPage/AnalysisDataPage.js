import React from 'react';
import {connect} from 'dva';
import {Spin} from 'antd';

import MainLayout from '../MainLayout/MainLayout';
import Banner from '../MainLayout/Banner';

import ScorePyramidChart from '../Analysis/ScorePyramidChart';

import GenreScoreBarChart from '../Analysis/GenreScoreBarChart';
import GenreRingChart from '../Analysis/GenreRingChart';
import GenreLineChart from '../Analysis/GenreLineChart';
import GenreBarChart from '../Analysis/GenreBarChart';

import CountryYearScoreChart from '../Analysis/CountryYearScoreChart';
import CountryScoreBarChart from '../Analysis/CountryScoreBarChart';

import GenreSelect from '../Analysis/GenreSelect';
import TipsPopover from '../Util/TipsPopover';

import styles from './AnalysisPage.css';

function AnalysisDataPage({dispatch, location, currentGenre, genreCount, genreInYear, countryCount, genreCountLoading, genreInYearLoading, countryCountLoading}) {

    function onGenreChange(id) {
        dispatch({
            type: 'analysis/changeGenre',
            payload: id
        })

    }

    return (
        <div className={styles.normal}>


            {/*<div className={styles.part}>*/}
            {/*<div className={styles.title}>*/}
            {/*<h3>Score Pyramid</h3>*/}
            {/*</div>*/}
            {/*<ScorePyramidChart />*/}
            {/*</div>*/}

            {/*no*/}
            {/*<div className={styles.part}>*/}
            {/*<div className={styles.title}>*/}
            {/*<h3>Genre Quantity</h3>*/}
            {/*</div>*/}
            {/*{*/}
            {/*quantityInGenre ?*/}
            {/*<GenreRingChart data={quantityInGenre}/> :*/}
            {/*null*/}
            {/*}*/}
            {/*</div>*/}
            {/**/}


            {/*<div className={styles.part}>*/}
            {/*<div className={styles.title}>*/}
            {/*<h3>Genre Score Range</h3>*/}
            {/*</div>*/}
            {/*{dataGenreCount ?*/}
            {/*<GenreBarChart*/}
            {/*data={dataGenreCount}*/}
            {/*/> : null*/}
            {/*}*/}
            {/*</div>*/}


            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Genre Count Percent And Score In Year</h3>
                    <div className={styles.title_right}>
                        <TipsPopover>
                            <div>
                                <p>这张图表展示不同类型随着年份的增长，每年的数量所占比率以及平均分。从图中可以看到不同类型随时间的流行趋势。</p>
                            </div>
                        </TipsPopover>
                    </div>
                </div>
                <div>
                    <GenreSelect
                        currentGenre={currentGenre}
                        onChange={onGenreChange}
                    />
                </div>
                {genreInYearLoading ?
                    <div
                        className={styles.spin}
                        style={{height: '600px'}}
                    >
                        <Spin/>
                    </div> : null
                }
                {!genreInYearLoading && genreInYear && genreInYear.filter(o => o.id === currentGenre).length > 0 ?
                    <GenreLineChart
                        data={genreInYear.filter(o => o.id === currentGenre)[0].genreInYear}
                    /> :
                    null
                }
            </div>


            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Genre Score Count</h3>
                    <div className={styles.title_right}>
                        <TipsPopover>
                            <div>
                                <p>这张图表展示国外用户和国内用户对不同类型的电影的评分分布情况，其中蓝色代表国外、红色代表国内，实心和空心分别代表高于和低于平均分的评分数量。</p>
                                <p>国外评分平均分：6.27<br/>国内平分平均分：6.89</p>
                            </div>
                        </TipsPopover>
                    </div>
                </div>
                {genreCountLoading ?
                    <div
                        className={styles.spin}
                        style={{height: '500px'}}
                    >
                        <Spin/>
                    </div> : null
                }
                {!genreCountLoading && genreCount ?
                    <GenreScoreBarChart
                        data={genreCount}
                    /> : null
                }
            </div>


            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Country Score Count</h3>
                    <div className={styles.title_right}>
                        <TipsPopover>
                            <div>
                                <p>这张图表展示了国外用户和国内用户对不同国家制作的电影的评分分布情况，其中蓝色代表国外、红色代表国内，实心和空心分别代表高于和低于平均分的评分数量。</p>
                                <p>从图中可以一窥这些国家电影的质量和口碑水平，例如美国电影好坏参半，而英法日的电影则是好评多于差评。同时也可以看出全球影迷都较为喜欢欧美电影，而亚洲电影似乎还有较大上升空间。</p>
                                <p>国外评分平均分：6.27<br/>国内平分平均分：6.89</p>
                            </div>
                        </TipsPopover>
                    </div>
                </div>
                {countryCountLoading ?
                    <div
                        className={styles.spin}
                        style={{height: '500px'}}
                    >
                        <Spin/>
                    </div> : null
                }
                {!countryCountLoading && countryCount ?
                    <CountryScoreBarChart
                        data={countryCount}
                    /> : null
                }
            </div>

            {/*no*/}
            {/*<div className={styles.part}>*/}
            {/*<div className={styles.title}>*/}
            {/*<h3>Country Average Score In Year</h3>*/}
            {/*</div>*/}
            {/*{countryScoreInYear ?*/}
            {/*<CountryYearScoreChart*/}
            {/*data={countryScoreInYear}*/}
            {/*/> : null*/}
            {/*}*/}
            {/*</div>*/}
            {/**/}


            {/*no*/}
            {/*<div className={styles.part}>*/}
            {/*<div className={styles.title}>*/}
            {/*<h3>Genre Country Relationship</h3>*/}
            {/*</div>*/}
            {/*<CountryGenreSankeyChart />*/}
            {/*</div>*/}
            {/**/}

        </div>

    );
}


function mapStateToProps(state) {
    const {data} = state.analysis;
    return {
        currentGenre: data.currentGenre,
        genreCount: data.genreCount,
        genreInYear: data.genreInYear,
        // countryScoreInYear: data.countryScoreInYear,
        countryCount: data.countryCount,

        genreCountLoading: state.loading.effects['analysis/fetchGenreCount'],
        genreInYearLoading: state.loading.effects['analysis/fetchGenreInYear'],
        countryCountLoading: state.loading.effects['analysis/fetchCountryCount']
    };
}

export default connect(mapStateToProps)(AnalysisDataPage);
