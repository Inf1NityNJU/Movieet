import React, { Component } from 'react';
import createG2 from 'g2-react';
import G2, { Stat, Global } from 'g2';

const Chart = createG2(chart => {
  chart.axis('score',{
    title: 'score'
  });
  chart.axis('population', {
    title: 'population'
  });
  chart.coord('rect').transpose();
  chart.interval().position('score*population');
  chart.render();
});

class MovieScoreChart extends Component {

  constructor(...argus) {
    super(...argus);

    var data = [
      {"score":'10',"population":19612368},
      {"score":'9',"population":12938693},
      {"score":'8',"population":71854210},
      {"score":'7',"population":27500000},
      {"score":'6',"population":24706291},
      {"score":'5',"population":43746323},
      {"score":'4',"population":27452815},
      {"score":'3',"population":38313991},
      {"score":'2',"population":23019196},
      {"score":'1',"population":78660941},
    ];

    var Frame = G2.Frame;
    var frame = new Frame(data);

    frame = Frame.sort(frame, 'population'); // 将数据按照population 进行排序，由大到小

    this.state = {
      forceFit: true,
      width: 300,
      height: 400,
      plotCfg: {
        // margin: [20, 60, 20, 120]
      },
      data:data,

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

export default MovieScoreChart;
