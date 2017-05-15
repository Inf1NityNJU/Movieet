import React, { Component } from 'react';
import createG2 from 'g2-react';
import G2, { Stat, Global } from 'g2';

const Chart = createG2(chart => {
  chart.col('year', {
    alias: 'Year',
    //type: 'time',
    tickCount: 18,
    //mask: 'yyyy'
  });
  chart.col('count', {
    alias: 'Count'
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
  chart.line().position('year*count').color('#93A9BD').size(2);
  chart.line().position('year*score').color('#F48984').size(2);
  //var slider = new G2.Plugin.Slider({
  //  domId: 'slider', //DOM id
  //  height: 26,
  //  xDim: 'time',
  //  yDim: 'flow',
  //  charts: chart
  //});
  //slider.render();
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


