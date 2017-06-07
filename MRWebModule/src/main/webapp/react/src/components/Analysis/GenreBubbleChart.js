import React, {Component} from 'react';
import createG2 from 'g2-react';

import G2, {Stat, Global} from 'g2';

import {Slider} from 'antd';
import {GENRES} from '../../constants'

const Chart = createG2(chart => {
    chart.cols({
        'votes': {
            alias: 'Average votes'
        },
        'count': {
            type: 'pow',
            alias: 'Count'
        },
        'score': {
            alias: 'Average score'
        },
        'genre': {
            alias: 'genre'
        }
    });
    chart.axis('votes', {
        // 格式化坐标轴的显示
        formatter: function (value) {
            return (value / 1000).toFixed(0) + 'k';
        }
    });
    chart.tooltip({
        title: null // 不显示默认标题
    });
    // 该图表默认会生成多个图例，设置不展示 Population 和 Country 两个维度的图例
    chart.legend('genre', {
        position: 'bottom',
        itemWrap: true,
    });
    chart.point().position('votes*score')
        .size('count', 35, 5)
        .color('genre')
        .opacity(0.7)
        .shape('circle')
        .tooltip('genre*count*votes*score');
    chart.render();
});

class GenreBubbleChart extends Component {

    constructor(...argus) {
        super(...argus);

        let data = [];

        GENRES.slice(1, GENRES.length).map(genre => {
            data.push({
                "genre": genre.value,
                "votes": parseFloat((Math.random() * 1000000).toFixed(2)),
                "score": parseFloat((Math.random() * 10).toFixed(2)),
                "count": parseInt((Math.random() * 10000).toFixed(0))
            });
        });
        this.state = {
            data: data,
            forceFit: true,
            width: 500,
            height: 500,
            plotCfg: {
                margin: [20, 80, 160, 120]
            },
        };

        let self = this;

        // setInterval(function() {
        //     self.setState({
        //         year: self.state.year++,
        //     })
        // }, 1500);
    }

    onSliderChange = (value) => {

        let data = [];

        GENRES.slice(1, GENRES.length).map(genre => {
            data.push({
                "genre": genre.value,
                "votes": parseFloat((Math.random() * 1000000).toFixed(2)),
                "score": parseFloat((Math.random() * 10).toFixed(2)),
                "count": parseInt((Math.random() * 10000).toFixed(0))
            });
        });

        this.setState({
            year: value,
            data: data
        });
    };

    render() {
        return (
            <div>
            <Chart
                data={this.state.data}
                width={this.state.width}
                height={this.state.height}
                plotCfg={this.state.plotCfg}
                forceFit={this.state.forceFit}
            />
                <Slider min={1970}
                        max={2017}
                        value={this.state.year}
                        onChange={this.onSliderChange}/>
            </div>
        );
    }
}

export default GenreBubbleChart;


