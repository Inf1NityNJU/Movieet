import React, {Component} from 'react';
import {connect} from 'dva';
import MathJax from 'react-mathjax';

import {Button, Alert, Spin} from 'antd';

import styles from './PredictionPage.css';

import PredictionSearchInput from '../Prediction/PredictionSearchInput';
import ElementItemList from '../Prediction/ElementItemList';
import MovieRadarChart from '../Movie/MovieRadarChart';
import TypeSelect from '../Prediction/TypeSelect';
import TLineChart from '../Prediction/TLineChart';
import TipsPopover from '../Util/TipsPopover';

// function PredictionCombinationPage({dispatch, keyword, current, search, predict, estimate, estimateStatus}) {

class PredictionCombinationPage extends Component {

    state = {
        genreWarning: false,
        directorWarning: false,
        actorWarning: false,
    };

    onCheckChange = (type, id, checked) => {
        const {dispatch} = this.props;
        dispatch({
            type: 'prediction/checkCurrent' + type,
            payload: {
                id,
                checked,
            }
        });
    };

    onItemRemove = (type, id) => {
        const {dispatch} = this.props;
        dispatch({
            type: 'prediction/removeCurrent' + type,
            payload: {
                id,
            }
        });
    };

    onPredictClick = () => {
        const {dispatch, current} = this.props;

        const genre = current.genres.filter(g => g.checked);
        const director = current.directors.filter(d => d.checked);
        const actor = current.actors.filter(a => a.checked);

        this.setState({
            genreWarning: genre.length === 0,
            directorWarning: director.length === 0,
            actorWarning: actor.length === 0
        });

        dispatch({
            type: 'prediction/savePredict',
            payload: null
        });
        dispatch({
            type: 'prediction/saveEstimate',
            payload: null
        });

        if (genre.length === 0 || director.length === 0 || actor.length === 0) {
            return;
        }

        dispatch({
            type: 'prediction/predict',
            payload: {}
        });
        dispatch({
            type: 'prediction/estimate',
            payload: {}
        });
        dispatch({
            type: 'prediction/saveEstimateStatus',
            payload: 'boxOffice'
        });
    };

    onTypeChange = (type) => {
        const {dispatch} = this.props;
        dispatch({
            type: 'prediction/saveEstimateStatus',
            payload: type
        });
    };

    render() {
        const {dispatch, keyword, current, search, predict, estimate, estimateStatus, predictionLoading, estimateLoading} = this.props;
        const {genreWarning, directorWarning, actorWarning} = this.state;
        return (
            <div className={styles.prediction + " background"}>
                <div className="container">

                    <div className={styles.part}>
                        <PredictionSearchInput
                            keyword={keyword}
                            search={search}
                            dispatch={dispatch}
                        />
                    </div>


                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Genre</h3>
                        </div>

                        <div className={styles.hint}>
                            {genreWarning ?
                                <Alert message="Please choose at least one genre" type="warning" showIcon/> :
                                <p>Please choose at least one genre</p>
                            }
                        </div>

                        <ElementItemList
                            list={current.genres}
                            onCheckChange={(id, checked) => this.onCheckChange('Genre', id, checked)}
                            onItemRemove={(id) => this.onItemRemove('Genre', id)}
                        />
                    </div>

                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Directors</h3>
                        </div>

                        <div className={styles.hint}>
                            {directorWarning ?
                                <Alert message="Please choose at least one director" type="warning" showIcon/> :
                                <p>Please choose at least one director</p>
                            }
                        </div>

                        <ElementItemList
                            list={current.directors}
                            onCheckChange={(id, checked) => this.onCheckChange('Director', id, checked)}
                            onItemRemove={(id) => this.onItemRemove('Director', id)}
                        />
                    </div>

                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Actors</h3>
                        </div>

                        <div className={styles.hint}>
                            {actorWarning ?
                                <Alert message="Please choose at least one actor" type="warning" showIcon/> :
                                <p>Please choose at least one actor</p>
                            }
                        </div>

                        <ElementItemList
                            list={current.actors}
                            onCheckChange={(id, checked) => this.onCheckChange('Actor', id, checked)}
                            onItemRemove={(id) => this.onItemRemove('Actor', id)}
                        />
                    </div>

                    <div className={styles.part}>
                        <Button
                            className={styles.button}
                            type="primary"
                            size="large"
                            icon="api"
                            onClick={this.onPredictClick}
                        >
                            Predict!
                        </Button>
                    </div>


                    {predict || predictionLoading ?
                        <div className={styles.part}>
                            <div className={styles.title}>
                                <h3>Prediction Result</h3>
                            </div>

                            {predictionLoading ?
                                <div className={styles.spin}>
                                    <Spin/>
                                </div> : null
                            }
                            {!predictionLoading && predict ?
                                <MovieRadarChart
                                    movie={{
                                        boxOffice: parseInt(predict.boxOffice.toFixed(0)),
                                        votesFR: predict.votesFR,
                                        votesCN: predict.votesCN,
                                        scoreFR: parseFloat(predict.scoreFR.toFixed(2)),
                                        scoreCN: parseFloat(predict.scoreCN.toFixed(2)),
                                    }}
                                /> : null
                            }
                        </div> : null
                    }

                    {estimateLoading || (estimate && estimateStatus) ?
                        <div className={styles.part}>
                            <div className={styles.title}>
                                <h3>T Distribution Chart</h3>
                                <div className={styles.title_right}>
                                    <TipsPopover>
                                        <h6>区间估计预测</h6>
                                        <div>
                                            <p>
                                                使用了小样本条件下总体均值µ的区间估计，使用 T 分布来得到置信区间。
                                            </p>
                                        </div>
                                        <div>
                                            <MathJax.Context>
                                                <div>
                                                    <MathJax.Node>
                                                        {`\\frac { \\overline{x} - \\mu }{S/\\sqrt{n}} \\sim  t( n-1 )`}
                                                    </MathJax.Node>
                                                    <p>
                                                        <MathJax.Node inline>{`\\overline{x}`}</MathJax.Node> - 样本均值
                                                    </p>
                                                    <p>
                                                        <MathJax.Node inline>{`\\mu`}</MathJax.Node> - 总体均值
                                                    </p>
                                                    <p>
                                                        <MathJax.Node inline>{`S`}</MathJax.Node> - 样本方差
                                                    </p>
                                                    <p>
                                                        <MathJax.Node inline>{`n`}</MathJax.Node> - 样本容量
                                                    </p>
                                                    <p>
                                                        <MathJax.Node inline>{`\\alpha`}</MathJax.Node> - 显著水平（0.05）
                                                    </p>
                                                </div>
                                            </MathJax.Context>
                                        </div>
                                        <div>
                                            <p>

                                            </p>
                                        </div>
                                    </TipsPopover>
                                </div>
                            </div>

                            {estimateLoading ?
                                <div className={styles.spin}>
                                    <Spin/>
                                </div> : null
                            }


                            {!estimateLoading && estimate && estimate.boxOffice && estimateStatus ?
                                <div>
                                    <TypeSelect
                                        status={estimateStatus}
                                        className={styles.type_select}
                                        onChange={this.onTypeChange}
                                    />
                                    <TLineChart
                                        data={estimate[estimateStatus]}
                                    />
                                </div> :
                                <div className={styles.lack}>
                                    Lack of data
                                </div>
                            }



                        </div> : null
                    }

                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const {current, search, keyword, predict, estimate, estimateStatus} = state.prediction;
    return {
        keyword,
        current,
        search,
        predict,
        estimate,
        estimateStatus,
        predictionLoading: state.loading.effects['prediction/predict'],
        estimateLoading: state.loading.effects['prediction/estimate'],

    };
}

export default connect(mapStateToProps)(PredictionCombinationPage);
