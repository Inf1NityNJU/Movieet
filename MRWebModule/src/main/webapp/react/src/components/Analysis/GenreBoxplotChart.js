import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

import { GENRES } from '../../constants'

const Chart = createG2(chart => {
    chart.tooltip({
        map:{
            title: "type"
        }
    });
    chart.schemaDodge().position(Stat.bin.quantile.letter('value')).color('type').shape('box').size(50);
    chart.render();
    // chart.on('tooltipchange', function(ev){
    //     let items = ev.items;
    //     let origin = items[0];
    //     let values = origin.point._origin.value;
    //     items.splice(0); // 清空
    //     items.push(Util.mix({}, origin, {
    //         name: '最小值',
    //         value: values[0].toFixed(2)
    //     }));
    //     items.push(Util.mix({}, origin, {
    //         name: '下四分位数',
    //         value: values[1].toFixed(2),
    //         marker: 'circle'
    //     }));
    //     items.push(Util.mix({}, origin, {
    //         name: '中位数',
    //         value: values[2].toFixed(2),
    //         marker: 'circle'
    //     }));
    //     items.push(Util.mix({}, origin, {
    //         name: '上四分位数',
    //         value: values[3].toFixed(2),
    //         marker: 'circle'
    //     }));
    //     items.push(Util.mix({}, origin, {
    //         name: '最大值',
    //         value: values[4].toFixed(2),
    //         marker: 'circle'
    //     }));
    // });
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


