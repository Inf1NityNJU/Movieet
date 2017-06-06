import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

import { GENRES } from '../../constants'

const Chart = createG2(chart => {
    chart.col('range', {
        min: 0,
        max: 10,
        tickInterval: 1,
        alias: 'Score Range'
    });
    chart.col('average', {
        min: 0,
        max: 10,
        title: null,
        alias: 'Score Average'
    });
    chart.legend({
        back: {
            fill: '#e9e9e9',
            fillOpacity: 0.55,
            radius: 5
        }
    });
    chart.axis('genre', {
        title: null,
    });
    chart.axis('average', false);
    chart.axis('range', {
        titleOffset: 50
    });
    chart.coord().transpose();
    chart.legend({
        position: 'bottom', // 设置图例的显示位置
        // spacingX: 20 // 图例项之间的水平间距
        itemWrap: true,
    });
    chart.interval().position('genre*range').color('genre').size(15);
    chart.point().position('genre*average').color('#fff')
        .shape('line').size(8)
        .label('average', {offset: 20, label: {fill: '#fff'}});
    chart.render();
});

class GenreRingChart extends Component {

    constructor(...argus) {
        super(...argus);

        let data = [];

        GENRES.slice(1, GENRES.length)
            .forEach(genre => {
            data.push({
                genre: genre.value,
                highest: parseFloat((Math.random() * 4 + 6).toFixed(2)),
                lowest:  parseFloat((Math.random() * 4).toFixed(2)),
                average: parseFloat((Math.random() * 2 + 4).toFixed(2)),
            })
        });

        let Frame = G2.Frame;
        let frame = new Frame(data); // 加工数据
        frame.addCol('range', function (obj) {
            return [obj.lowest, obj.highest];
        });
        frame = Frame.sort(frame, 'average');

        this.state = {
            data: frame,
            forceFit: true,
            width: 500,
            height: 600,
            plotCfg: {
                margin: [20, 20, 160, 120]
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

export default GenreRingChart;


