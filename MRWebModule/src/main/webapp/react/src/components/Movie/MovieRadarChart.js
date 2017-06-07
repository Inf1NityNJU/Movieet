import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

import styles from './MovieRadarChart.css';

import {
    BOX_OFFICE_ARRAY,
    SCORE_FR_ARRAY, SCORE_CN_ARRAY,
    VOTE_FR_ARRAY, VOTE_CN_ARRAY
} from '../../constants';

const Chart = createG2(chart => {
    G2.Global.axis.circle.grid = {
        line: {
            stroke: '#d9d9d9',
            lineWidth: 1,
            lineDash: [0, 0]
        }
    };
    chart.col('value', {
        min: 0,
        max: 10,
        nice: true,
        tickInterval: 2
    });
    chart.coord('polar');
    // chart.legend('name', {
    //   position: 'bottom'
    // });
    chart.axis('categories', { // 设置坐标系栅格样式
        line: null,
        // labels: {
        //     custom: true, // 表示使用自定义 html 显示文本标签
        // renderer: function(value){
        //     return '<span>' + value + '</span><span></span>';
        // }
        // }
    });
    chart.axis('value', {
        line: {
            lineWidth: 2
        },
        grid: {
            line: {
                lineDash: [0, 0],
                lineWidth: 1
            },
            odd: {
                fill: '#eeeeee',
                opacity: 0.3
            } // 交替的背景设置，索引值为奇数的 gird
        }// 设置坐标系栅格样式
    });
    chart.line().position('categories*value').color('#AED7D8').size(2);
    chart.point().position('categories*value').color('#AED7D8').shape(['circle']).size(4);
    chart.render();
});

class MovieRadarChart extends Component {
    constructor(...argus) {
        super(...argus);
        this.state = {
            forceFit: true,
            width: 500,
            height: 400,
            plotCfg: {
                margin: [50, 50, 50, 50]
            }
        };
    }


    render() {
        const {
            boxOffice,
            votesCN, votesFR,
            scoreCN, scoreFR,
        } = this.props.movie;

        const data = [{}];
        boxOffice ? data[0]['Box Office'] = BOX_OFFICE_ARRAY.reduce((result, current, index, array) => {
                if (boxOffice > current) {
                    result++
                }
                return result;
            }) + 1 : null;
        scoreFR ? data[0]['Foreign Score'] = SCORE_FR_ARRAY.reduce((result, current, index, array) => {
                if (scoreFR > current) {
                    result++
                }
                return result;
            }) + 1 : null;
        scoreCN ? data[0]['Domestic Score'] = SCORE_CN_ARRAY.reduce((result, current, index, array) => {
                if (scoreCN > current) {
                    result++
                }
                return result;
            }) + 1 : null;
        votesFR ? data[0]['Foreign Vote'] = VOTE_FR_ARRAY.reduce((result, current, index, array) => {
                if (votesFR > current) {
                    result++
                }
                return result;
            }) + 1 : null;
        votesCN ? data[0]['Domestic Vote'] = VOTE_CN_ARRAY.reduce((result, current, index, array) => {
                if (boxOffice > current) {
                    result++
                }
                return result;
            }) + 1 : null;

        console.log(data[0]);

        const Frame = G2.Frame;
        let frame = new Frame(data);
        frame = Frame.combinColumns(
            frame,
            [
                'Box Office',
                'Domestic Score',
                'Domestic Vote',
                'Foreign Vote',
                'Foreign Score',
            ],
            'value',
            'categories'
        );

        return (
            <div>
                <div className={styles.data_wrapper}>
                    <span className={styles.box_office}> {boxOffice ? '$ ' + boxOffice : ''}</span>
                    <span className={styles.score_fr}>{scoreFR ? scoreFR : ''}</span>
                    <span className={styles.score_cn}>{scoreCN ? scoreCN : ''}</span>
                    <span className={styles.votes_fr}>{votesFR ? votesFR : ''}</span>
                    <span className={styles.votes_cn}>{votesCN ? votesCN : ''}</span>
                </div>

                <Chart
                    data={frame.data}
                    width={this.state.width}
                    height={this.state.height}
                    plotCfg={this.state.plotCfg}
                    forceFit={this.state.forceFit}/>
            </div>
        );
    }

}

export
default
MovieRadarChart;
