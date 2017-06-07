/**
 * Created by Sorumi on 17/6/7.
 */
import React, {Component} from 'react';
import createG2 from 'g2-react';

import G2, {Stat, Global} from 'g2';
import {Slider} from 'antd';

const Chart = createG2(chart => {
    chart.legend('name', {
        position: 'left'
    });
    chart.coord('rect').transpose().scale(1, -1);
    chart.axis(false);
    chart.intervalSymmetric().position('name*value')
        .color('name', ['#93A9BD', '#A7DAD8', '#B6D7B3', '#FEE9A5', '#F7CC9B', '#FDB8A1', '#E3645A', '#F9815C', '#EB4456', '#C82B3D'])
        .shape('pyramid');
    chart.render();
});

class ScorePyramidChart extends Component {

    constructor(...argus) {
        super(...argus);
        let data = [
            {name: "More than 3", value: 3500},
            {name: "More than 4", value: 3124},
            {name: "More than 5", value: 1003},
            {name: "More than 6", value: 252},
            {name: "More than 7", value: 200},
            {name: "More than 8", value: 137},
            {name: "More than 9", value: 50},
        ];

        this.state = {
            year: 1970,
            data: data,
            forceFit: true,
            width: 960,
            height: 500,
            plotCfg: {
                margin: [80, 80]
            },
        };
    }

    onSliderChange = (value) => {
        this.setState({
            year: value,
            data: [
                {name: "More than 3", value: Math.floor(Math.random() * 500) + 3000},
                {name: "More than 4", value: Math.floor(Math.random() * 500) + 1000},
                {name: "More than 5", value: Math.floor(Math.random() * 500) + 300},
                {name: "More than 6", value: Math.floor(Math.random() * 100) + 200},
                {name: "More than 7", value: Math.floor(Math.random() * 50) + 150},
                {name: "More than 8", value: Math.floor(Math.random() * 50) + 50},
                {name: "More than 9", value: Math.floor(Math.random() * 50)},
            ]
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
                        onChange={this.onSliderChange}/>
            </div>
        );
    }
}

export default ScorePyramidChart;


