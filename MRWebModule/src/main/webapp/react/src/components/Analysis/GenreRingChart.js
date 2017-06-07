import React, { Component } from 'react';
import createG2 from 'g2-react';
import G2, { Stat, Global } from 'g2';

const Chart = createG2(chart => {

  chart.legend({
    position: 'bottom',
    itemWrap: true,
    // word: {
    //   fill: '#808080',
    //   fontSize: 14,
    //   fontWeight: 200,
    // },
  });
  chart.coord('theta', {
    radius: 1,
    inner: 0.6
  });
  chart.tooltip({
    title: null
  });
  chart.intervalStack().position(Stat.summary.percent('quantity'))
    .color('genre')
    .label('..percent', {
      offset: -5
    })
    .style({
      lineWidth: 1,
      stroke: 'white',
    });
  chart.render();
});


class GenreRingChart extends Component {
  constructor(...argus) {
    super(...argus);

    this.state = {
      forceFit: true,
      width: 960,
      height: 500,
      plotCfg: {
        margin: [20, 100, 150, 100]
      }
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


