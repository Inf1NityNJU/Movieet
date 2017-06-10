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
                {!genreInYearLoading && genreInYear ?
                    <GenreLineChart
                        data={genreInYear}
                    /> :
                    null
                }
            </div>


            <div className={styles.part}>
                <div className={styles.title}>
                    <h3>Genre Score Count</h3>
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
