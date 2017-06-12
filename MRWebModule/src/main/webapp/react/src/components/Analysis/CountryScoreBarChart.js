import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Util} from 'g2';

const Chart = createG2(chart => {
    //chart.coord().transpose();
    chart.axis('name', {
        title: "country"
    });
    chart.axis('count', {
        formatter: function (value) {
            value = parseInt(value);
            return Math.abs(value); // 将负数格式化成正数
        }
    });
    chart.col('name');
    chart.intervalDodge().position('name*count').color('area', ['#93A9BD', '#F48984']).shape('type', ['rect', 'hollowRect']).style({
        lineWidth: 1,
    }).size(15);
    chart.on('tooltipchange', function (ev) {
        let items = ev.items;
        items[0].name = 'Domestic more than average';
        items[1].name = 'Domestic less than average';
        items[1].value = -items[1].value;
        items[2].name = 'Foreign more than average';
        items[3].name = 'Foreign less than average';
        items[3].value = -items[3].value;
    });
    chart.render();
});

class CountryScoreBarChart extends Component {

    constructor(...argus) {
        super(...argus);

        let data = this.props.data;
        data.forEach(function (obj) {
            obj.less *= -1;
        });
        let Frame = G2.Frame;
        let frame = new Frame(data);

        frame = Frame.combinColumns(frame, ['more', 'less'], 'count', 'type');

        // frame = Frame.sortBy(frame, (obj1, obj2) => {
        //     if (obj1.name = obj2.name ){
        //         return 0;
        //     }
        //     return (obj1.less + obj1.more) > (obj2.less + obj2.more)
        // });

        console.log(frame);

        this.state = {
            data: frame.data,
            forceFit: true,
            width: 960,
            height: 600,
            plotCfg: {
                margin: [10, 120, 120]
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

export default CountryScoreBarChart;


