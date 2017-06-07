import React, {Component} from 'react';
import createG2 from 'g2-react';

import Slider from 'g2-plugin-slider';
import G2, {Stat, Global} from 'g2';

const Chart = createG2(chart => {
    chart.col('year', {
        alias: 'Year',
        tickCount: 10,
    });
    chart.col('count', {
        alias: 'Count',
        formatter: function (value) {
            return value.toFixed(0) + '%';
        }
    });
    chart.col('score', {
        alias: 'Score'
    });
    chart.axis('year', {
        title: null
    });
    chart.legend({
        position: 'bottom'
    });
    // chart.line().position('year*count').color('#93A9BD').size(2);
    chart.interval().position('year*count').color('#93A9BD');
    chart.line().position('year*score').color('#F48984').size(2);

    chart.render();

});

class GenreRingChart extends Component {

    constructor(...argus) {
        super(...argus);

        this.state = {
            forceFit: false,
            width: 960,
            height: 500,
            plotCfg: {
                margin: [80, 80]
            },
        };
    }

    render() {
        return (
            <Chart
                data={this.props.data}
                width={this.state.width}
                height={this.state.height}
                plotCfg={this.state.plotCfg}
                forceFit={this.state.forceFit}
            />
        );
    }
}

export default GenreRingChart;


