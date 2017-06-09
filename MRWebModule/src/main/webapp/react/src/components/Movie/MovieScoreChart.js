import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

import {Row, Col} from 'antd';

import styles from './MovieScoreChart.css';

const Chart = createG2(chart => {
    chart.coord().transpose();
    chart.axis('score', {title: null});
    chart.axis('count', {
        formatter: function (value) {
            return Math.abs(value).toFixed(2) + '%';
        },
        title: null
    });
    chart.col('count', {
        type: 'linear',
        min: -100,
        max: 100
    });
    chart.interval().position('score*count').color('area', ['#93A9BD', '#F48984']);
    chart.legend({
        position: 'bottom',
        spacingX: 20
    });

    chart.on('tooltipchange', function (ev) {
        console.log(ev);
        ev.items.map(item => {
            item.value = item.value === 'NaN' ?
                '' :
                Math.abs(item.value).toFixed(2) + '%';
        })
        // const fr = items[0];
        // fr.value += ' ' + parseInt(fr.title)-1
    });

    chart.render();
});

class MovieScoreChart extends Component {

    constructor(...argus) {
        super(...argus);

        const {
            distributionCN, distributionFR,
            votesCN, votesFR
        } = this.props.movie;

        let data = [];

        for (let i = 0; i < 5; i++) {
            data.push({
                score: String(i + 1),
                foreign: distributionFR[i] / votesFR * 100,
                domestic: distributionCN[i] / votesCN * 100,
            })
        }

        data.forEach(function (obj) {
            obj['foreign'] *= -1;
        });
        let Frame = G2.Frame;
        let frame = new Frame(data);
        frame = Frame.combinColumns(frame, ['foreign', 'domestic'], 'count', 'area');

        this.state = {
            forceFit: true,
            width: 500,
            height: 400,
            plotCfg: {
                margin: [40, 40, 80]
            },
            data: frame.data,

        };
    }

    render() {
        const {
            votesCN, votesFR,
            scoreCN, scoreFR,
        } = this.props.movie;

        return (
            <div>
                <Row gutter={100} className={styles.score}>
                    { votesFR === null || scoreFR === null || votesFR === 0 || scoreFR === 0 ?
                        <Col span={12} className={styles.score_left}>
                            <p className={styles.area}>Foreign</p>
                            <h5>No Data</h5>
                        </Col> :
                        <Col span={12} className={styles.score_left}>
                            <p className={styles.area}>Foreign</p>
                            <h5>{scoreFR}</h5>
                            <p className={styles.vote}>{votesFR}</p>
                        </Col>
                    }
                    { votesCN === null || scoreCN === null || votesCN === 0 || scoreCN === 0 ?
                        <Col span={12} className={styles.score_right}>
                            <p className={styles.area}>Domestic</p>
                            <h5>No Data</h5>
                        </Col> :
                        <Col span={12} className={styles.score_right}>
                            <p className={styles.area}>Domestic</p>
                            <h5>{scoreCN}</h5>
                            <p className={styles.vote}>{votesCN}</p>
                        </Col>
                    }
                </Row>
                <Chart
                    data={this.state.data}
                    width={this.state.width}
                    height={this.state.height}
                    plotCfg={this.state.plotCfg}
                    forceFit={this.state.forceFit}
                />
            </div>
        );
    }
}

export default MovieScoreChart;
