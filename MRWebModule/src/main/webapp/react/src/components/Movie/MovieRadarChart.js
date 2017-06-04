import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

import styles from './MovieRadarChart.css';

const Chart = createG2(chart => {
  G2.Global.axis.circle.grid = {
    line: {
      stroke: '#d9d9d9',
      lineWidth: 1,
      lineDash: [0, 0]
    }
  };
  chart.col('value', {
    min: 0,
    max: 10,
    nice: true,
    tickInterval: 2
  });
  chart.coord('polar');
  // chart.legend('name', {
  //   position: 'bottom'
  // });
  chart.axis('categories', { // 设置坐标系栅格样式
    line: null
  });
  chart.axis('value', {
    line: {
      lineWidth: 2
    },
    grid: {
      line: {
        lineDash: [0, 0],
        lineWidth: 1
      },
      odd: {
        fill: '#eeeeee',
        opacity: 0.3
      } // 交替的背景设置，索引值为奇数的 gird
    }// 设置坐标系栅格样式
  });
  chart.line().position('categories*value').color('#AED7D8').size(2);
  chart.point().position('categories*value').color('#AED7D8').shape(['circle']).size(4);
  chart.render();
});

class MovieRadarChart extends Component {
  constructor(...argus) {
    super(...argus);

    const {
      boxoffice,
      votesCN, votesFR,
      scoreCN, scoreFR,
    } = this.props.movie;

    const data = [{}];

    boxoffice ? data[0]['Box Office'] = boxoffice / 100000 : null;
    scoreFR ? data[0]['Foreign Score'] = scoreFR : null;
    scoreCN ? data[0]['Domestic Score'] = scoreCN : null;
    votesFR ? data[0]['Foreign Vote'] = votesFR / 100000 : null;
    votesCN ? data[0]['Domestic Vote'] = votesCN / 10000 : null;

    const Frame = G2.Frame;
    let frame = new Frame(data);
    frame = Frame.combinColumns(
      frame,
      [
        'Box Office',
        'Domestic Score',
        'Domestic Vote',
        'Foreign Vote',
        'Foreign Score',
      ],
      'value',
      'categories'
    );

    this.state = {
      data: frame.data,
      forceFit: true,
      width: 500,
      height: 400,
      plotCfg: {
        margin: [50, 50, 50, 50]
      }
    };
  }


  render() {
    return (
      <div>
        <Chart
          data={this.state.data}
          width={this.state.width}
          height={this.state.height}
          plotCfg={this.state.plotCfg}
          forceFit={this.state.forceFit}/>
      </div>
    );
  }

}

export default MovieRadarChart;
