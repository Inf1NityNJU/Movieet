import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Util} from 'g2';

import {GENRES} from '../../constants'

const Chart = createG2(chart => {
    //chart.coord().transpose();
    chart.axis('genre',
        {title: null}
    );
    chart.axis('count', {
        formatter: function (value) {
            value = parseInt(value);
            return Math.abs(value); // 将负数格式化成正数
        },
        title: null
    });
    chart.col('genre');
    chart.intervalDodge().position('genre*count').color('area', ['#93A9BD', '#F48984']).shape('type', ['rect', 'hollowRect']).style({
        lineWidth: 1,
    }).size(10);
    chart.on('tooltipchange', function (ev) {
        let items = ev.items;
        items[0].name = 'Domestic more than average';
        items[1].name = 'Domestic less than average';
        items[2].name = 'Foreign more than average';
        items[3].name = 'Foreign less than average';
    });
    chart.render();
});

class GenreScoreBarChart extends Component {

    constructor(...argus) {
        super(...argus);
        let data = [];

        GENRES.slice(1, GENRES.length).map(genre => {
                data.push({
                    genre: genre.value,
                    area: 'foreign',
                    more: Math.random() * 100,
                    less: Math.random() * 100,
                });
                data.push({
                    genre: genre.value,
                    area: 'domestic',
                    more: Math.random() * 100,
                    less: Math.random() * 100,
                });
            }
        );


        data.forEach(function (obj) {
            obj.less *= -1;
        });
        let Frame = G2.Frame;
        let frame = new Frame(data);

        frame = Frame.combinColumns(frame, ['more', 'less'], 'count', 'type');

        console.log(frame);

        this.state = {
            data: frame.data,
            forceFit: true,
            width: 500,
            height: 450,
            plotCfg: {
                margin: [10, 100, 120]
            },
        };
    }

    render() {
        return (
            <Chart
                data={this.state.data}
                width={this.state.width}
                height={this.state.height}
                plotCfg={this.state.plotCfg}
                forceFit={this.state.forceFit}
            />
        );
    }
}

export default GenreScoreBarChart;


