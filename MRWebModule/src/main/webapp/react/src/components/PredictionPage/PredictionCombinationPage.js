import React from 'react';
import {connect} from 'dva';

import {Button} from 'antd';

import styles from './PredictionPage.css';

import PredictionSearchInput from '../Prediction/PredictionSearchInput';
import ElementItemList from '../Prediction/ElementItemList';
import MovieRadarChart from '../Movie/MovieRadarChart';
import TLineChart from '../Prediction/TLineChart';

function PredictionCombinationPage({dispatch, keyword, current, search, predict, estimate}) {

    function onCheckChange(type, id, checked) {
        dispatch({
            type: 'prediction/checkCurrent' + type,
            payload: {
                id,
                checked,
            }
        });
    }

    function onItemRemove(type, id) {
        dispatch({
            type: 'prediction/removeCurrent' + type,
            payload: {
                id,
            }
        });
    }

    function onPredictClick() {
        dispatch({
            type: 'prediction/predict',
            payload: {}
        });
        dispatch({
            type: 'prediction/estimate',
            payload: {}
        });
    }

    return (
        <div className="background">
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

                    <ElementItemList
                        list={current.genres}
                        onCheckChange={(id, checked) => onCheckChange('Genre', id, checked)}
                        onItemRemove={(id) => onItemRemove('Genre', id)}
                    />
                </div>

                <div className={styles.part}>
                    <div className={styles.title}>
                        <h3>Directors</h3>
                    </div>

                    <ElementItemList
                        list={current.directors}
                        onCheckChange={(id, checked) => onCheckChange('Director', id, checked)}
                        onItemRemove={(id) => onItemRemove('Director', id)}
                    />
                </div>

                <div className={styles.part}>
                    <div className={styles.title}>
                        <h3>Actors</h3>
                    </div>

                    <ElementItemList
                        list={current.actors}
                        onCheckChange={(id, checked) => onCheckChange('Actor', id, checked)}
                        onItemRemove={(id) => onItemRemove('Actor', id)}
                    />
                </div>

                <div className={styles.part}>
                    <Button
                        className={styles.button}
                        type="primary"
                        size="large"
                        icon="api"
                        onClick={onPredictClick}
                    >
                        Predict!
                    </Button>
                </div>

                {predict ?
                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>Prediction Result</h3>
                        </div>
                        <MovieRadarChart
                            movie={{
                                boxOffice: parseInt(predict.boxOffice.toFixed(0)),
                                votesFR: predict.votesFR,
                                votesCN: predict.votesCN,
                                scoreFR: parseFloat(predict.scoreFR.toFixed(2)),
                                scoreCN: parseFloat(predict.scoreCN.toFixed(2)),
                            }}
                        />
                    </div> : null
                }
                {estimate ?
                    <div className={styles.part}>
                        <div className={styles.title}>
                            <h3>T Distribution Chart</h3>
                        </div>
                        <TLineChart
                            data={estimate.scoreFR}
                        />
                    </div> : null
                }

            </div>
        </div>
    );
}

function mapStateToProps(state) {
    const {current, search, keyword, predict, estimate} = state.prediction;
    return {
        keyword,
        current,
        search,
        predict,
        estimate
    };
}

export default connect(mapStateToProps)(PredictionCombinationPage);
