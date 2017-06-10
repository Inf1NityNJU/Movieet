import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';


import {GENRES, GENRES_MAP} from '../../constants'

const Chart = createG2(chart => {
    chart.col('year', {
        alias: 'Year',
        tickCount: 12,
    });
    chart.col('count', {
        alias: 'Count',
        formatter: function (value) {
            return value.toFixed(2) + '%';
        }
    });
    chart.col('score', {
        alias: 'Score',
        tickInterval: 0.1,
    });
    chart.axis('year', {
        title: null
    });
    chart.axis('count', {
        formatter: function (value) {
            return parseFloat(value.substring(0, value.length-1)).toFixed(0) + '%';
        }
    });
    chart.legend({
        position: 'bottom'
    });
    // chart.line().position('year*count').color('#93A9BD').size(2);
    chart.interval().position('year*count').color('#93A9BD');
    chart.line().position('year*score').color('#F48984').size(2);

    chart.render();

});

class GenreLineChart extends Component {

    constructor(...argus) {
        super(...argus);

        this.state = {
            forceFit: false,
            width: 960,
            height: 600,
            plotCfg: {
                margin: [80, 80]
            },
        };
    }

    render() {

        let data = this.props.data;

        data = data.map(o => {
            // console.log(o.id, GENRES_MAP[o.id]);
            return {
                ...o,
                year: o.year + '',
                count: o.count * 100,
                genre: GENRES_MAP[o.id],

            }
        });

        return (
                <Chart
                    data={data}
                    width={this.state.width}
                    height={this.state.height}
                    plotCfg={this.state.plotCfg}
                    forceFit={this.state.forceFit}
                />

        );
    }
}

export default GenreLineChart;


