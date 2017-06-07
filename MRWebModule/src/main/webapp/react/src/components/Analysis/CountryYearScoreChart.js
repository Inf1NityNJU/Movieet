import React, {Component} from 'react';
import createG2 from 'g2-react';

import Slider from 'g2-plugin-slider';
import G2, {Stat, Global} from 'g2';

const Chart = createG2(chart => {
    chart.col('year', {
        // mask: 'yyyy.mm',
        // tickCount: 12
    });
    chart.col('score', {
        min: 0,
        max: 10,
        // alias: 'Temperature, ºF'
    });
    chart.legend({
        position: 'bottom'
    });
    // chart.axis('date', {
    //     line: null,
    //     tickLine: {
    //         stroke: '#000',
    //         value: 6 // 刻度线长度
    //     },
    //     title: null
    // });
    chart.axis('value', {
        // tickLine: {
        //     stroke: '#000',
        //     value: 6 // 刻度线长度
        // },
        // labels: {
        //     label: {
        //         fill: '#000'
        //     }
        // },
        // line: {
        //     stroke: '#000'
        // },
        // grid: null
    });
    chart.line().position('year*score').color('country').shape('line').size(2);
    chart.render();
});

class CountryYearScoreChart extends Component {

    constructor(...argus) {
        super(...argus);

        let data = [];

        for (let y = 1993; y <= 2017; y++) {
            for (let i = 0; i < 8; i++) {
                data.push({
                    country: 'country ' + i,
                    year: y + '',
                    score: parseFloat((Math.random() * 10).toFixed(2)),
                })
            }
        }
        // let frame = new G2.Frame(data);
        // frame = Frame.combinColumns(frame, ['New York', 'San Francisco','Austin'], 'value', 'city', 'date');


        this.state = {
            data: data,
            forceFit: true,
            width: 960,
            height: 500,
            plotCfg: {
                margin: [20, 20, 100, 80]
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

export default CountryYearScoreChart;


