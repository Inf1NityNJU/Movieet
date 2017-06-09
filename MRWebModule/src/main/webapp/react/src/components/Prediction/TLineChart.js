import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

const Chart = createG2(chart => {
    chart.col('x', {
    });
    chart.col('y', {
        // max: 1,
    });
    chart.line().position('x*y').color('#AED7D8').size(2).tooltip('x*y');
    chart.tooltip({
        title: null
    });
    chart.on('tooltipchange',function(ev){
        let x = ev.items[0];
        x.value = parseFloat(x.value).toFixed(5);
        let y = ev.items[1];
        y.value = parseFloat(y.value).toFixed(5);

    });

    chart.render();
});

class TLineChart extends Component {

    constructor(...argus) {
        super(...argus);

        this.state = {
            forceFit: true,
            width: 500,
            height: 400,
            plotCfg: {
                margin: [40, 40, 80, 80]
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

export default TLineChart;
