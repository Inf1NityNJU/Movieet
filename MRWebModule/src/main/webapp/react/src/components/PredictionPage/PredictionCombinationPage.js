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

class PredictionCombinationPage extends Component {

    state = {
        genreWarning: false,
        directorWarning: false,
        actorWarning: false,
    };

    onCheckChange = (type, id, checked) => {
        const {dispatch, current} = this.props;
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
            genreWarning: !(genre.length > 0 && genre.length <= 3),
            directorWarning: director.length !== 1,
            actorWarning: !(actor.length > 0 && actor.length <= 2)
        });


        const {genreWarning, directorWarning, actorWarning} = this.state;

        dispatch({
            type: 'prediction/savePredict',
            payload: null
        });
        dispatch({
            type: 'prediction/saveEstimate',
            payload: null
        });

        if (!(genre.length > 0 && genre.length <= 3) || director.length !== 1 || !(actor.length > 0 && actor.length <= 2)) {
            window.scrollTo(0, 500);
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
                                <Alert message="Please choose at least one at most three genres" type="warning" showIcon/> :
                                <p>Please choose at least one at most three genres</p>
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
                                <Alert message="Please choose one director" type="warning" showIcon/> :
                                <p>Please choose one director</p>
                            }
                        </div>

                        <ElementItemList
                            img="true"
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
                                <Alert message="Please choose at least one at most two actors" type="warning" showIcon/> :
                                <p>Please choose at least one at most two actors</p>
                            }
                        </div>

                        <ElementItemList
                            img="true"
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

                                <div className={styles.title_right}>
                                    <TipsPopover>
                                        <div>
                                            <h6>雷达图</h6>
                                            <p>
                                                由于各个数值的标志值分布不均匀，国内外评分标准不同，不适用等距分组。通过对数据进行等比分组，将数值化为等级，来对电影进行多维度的评价。
                                            </p>
                                        </div>
                                        <div>
                                            <h6>Weka 预测</h6>
                                            <div>
                                                <p><strong>Discretize</strong> 将 actor, director 的 rank value 进行等距离离散化。</p>
                                                <p><strong>Preprocess</strong> 去除不完整的，无效的（评分、评价数或票房为0）的数据。 </p>
                                                <p><strong>Classify</strong> 使用M5P算法，分别对国外评分、国内评分、国内评价数、国外评价数和票房五个数值进行分类预测训练。 </p>
                                                <p><strong>Predict</strong> 保存算法得到的训练集，需要预测时对模型输入 actor, director,genre 三个因子，得到预测结果。 </p>
                                            </div>
                                        </div>
                                    </TipsPopover>
                                </div>

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
                                <h3>Confidence Interval</h3>
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
                                        type={estimateStatus}
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
