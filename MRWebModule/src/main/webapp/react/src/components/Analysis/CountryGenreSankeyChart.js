import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

import {GENRES} from '../../constants'

const Chart = createG2(chart => {
    let Stat = G2.Stat;// 统计算法对象
    let Layout = G2.Layout;// 布局算法对像
    let data = chart.get('data');
    let edges = data.data;

    console.log(data);
    chart.animate(false);
    chart.tooltip({
        title: null
    });
    chart.legend(false);
    // 桑基图布局
    let layout = new Layout.Sankey({
        edges: edges,
        thickness: 0.02  // 节点厚度
    });
    let nodes = layout.getNodes(); // 根据边数据自动创建出节点，并完成节点的布局
    // 创建边的视图
    let edgeView = chart.createView();
    edgeView.source(edges,{
        '..x':{
            min:0,
            max:1
        },
        '..y':{
            min:0,
            max:1
        }
    });
    edgeView.coord().transpose();
    edgeView.axis(false);
    edgeView.edge()
    // 由于边的坐标数据较多，此处使用统计函数简化语法，Stat.link计算布局后的边的坐标，放在..x和..y中，数值范围是 0-1
        .position(Stat.link.sankey('source*target*value',nodes)) // detachment：是否将节点的输入边和输出边权重分开计算的标志
        .shape('arc') // 使用弧线完成边的绘制
        .color('#bbb')
        .opacity(0.6)
        .tooltip('value');
    // 创建节点视图
    let nodeView = chart.createView();
    nodeView.axis(false);
    nodeView.source(nodes,{
        x:{
            min:0,
            max:1
        },
        y:{
            min:0,
            max:1
        }
    });
    nodeView.coord().transpose();
    nodeView.point()
        .position('x*y') // nodes数据的x、y由layout方法计算得出
        .color('id')
        .size('width*height',function(width,height){
            return [width,height];
        })
        .shape('rect')
        .label('id',{
            renderer: function(text, item, index)  {
                return text.substring(0,5);
            },
            offset:12
        })
        .style({
            stroke: '#ccc'
        });
    chart.render();
});

class CountryGenreSankeyChart extends Component {

    constructor(...argus) {
        super(...argus);

        let data = [];

        GENRES.slice(1,9).map(genre => {
            for (let i=0; i<6; i++) {
                data.push({
                    source: genre.value,
                    target: 'country ' + i,
                    value: Math.floor(Math.random()*300),
                })
            }
        });


        this.state = {
            data: data,
            forceFit: true,
            width: 500,
            height: 900,
            plotCfg: {
                margin: [10,50,10,5]
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

export default CountryGenreSankeyChart;


// const Chart = createG2(chart => {
//     let Stat = G2.Stat; // 统计算法对象
//     let Layout = G2.Layout; // 布局算法对像
//     let data = chart.get('data'); // 从 chart 对象中获取数据源
//     data = data.data;
//     let nodes = data.nodes; // 节点数据
//     let links = data.links; // 边数据
//     links.map(function (link) {
//         let sourceObj = nodes.filter(function (node) {
//             return node.id === link.source;
//         })[0];
//         link.type = sourceObj.modularity_class; // 边按照源节点的类型进行分类
//     });
//     // 线性布局Linear
//     let layout = new Layout.Linear({
//         nodes: nodes
//     });
//     nodes = layout.getNodes();// 获取布局后的节点数据
//     chart.animate(false);
//     chart.legend(false);
//     chart.tooltip({
//         title: null
//     });
//     // 创建边视图
//     let edgeView = chart.createView();
//     edgeView.source(links);
//     edgeView.coord('polar').reflect('y');  // 使用极坐标，反转y轴(布局方法默认给y赋值为0)
//     edgeView.axis(false);
//     edgeView.edge()
//     // 由于边的坐标数据较多，此处使用统计函数简化语法，Stat.link计算布局后的边的坐标，放在..x和..y中，数值范围是 0-1
//         .position(Stat.link('source*target', nodes))
//         .shape('arc') // 使用弧线完成边的绘制
//         .color('type', ['#93A9BD', '#A7DAD8', '#B6D7B3'])
//         .size('value')
//         .opacity(0.5)
//         .tooltip('source*target*value');
//
//     // 创建节点视图
//     let nodeView = chart.createView();
//     nodeView.coord('polar').reflect('y');
//     nodeView.axis(false);
//     nodeView.source(nodes);
//     nodeView.point()
//         .position('x*y') // nodes数据的x、y由layout方法计算得出
//         .color('modularity_class', ['#93A9BD', '#A7DAD8', '#B6D7B3'])
//         .size('size')
//         .shape('circle')
//         .label('label', {
//             offset: 10,
//             labelLine: false,
//             labelEmit: true  // 配置label文字为放射状
//         })
//         .tooltip('size*modularity_class');
//     chart.render();
// });
//
// class CountryGenreSankeyChart extends Component {
//
//     constructor(...argus) {
//         super(...argus);
//
//         let nodes = [];
//         let links = [];
//
//         GENRES.slice(1, 9).map(genre => {
//             nodes.push({
//                 "id": genre.id,
//                 "label": genre.value,
//                 "modularity_class": "1",
//                 "size": Math.floor(Math.random() * 20)
//             });
//         });
//
//         for (let i = 0; i < 6; i++) {
//             nodes.push({
//                 "id": i,
//                 "label": 'country ' + i,
//                 "modularity_class": "2",
//                 "size": Math.floor(Math.random() * 20)
//             })
//         }
//
//         GENRES.slice(1, 9).map(genre => {
//             for (let i = 0; i < 6; i++) {
//                 links.push({
//                     source: genre.id,
//                     target: i,
//                     value: Math.floor(Math.random() * 30),
//                 })
//             }
//         });
//
//         const data = {nodes, links};
//
//         this.state = {
//             data: data,
//             forceFit: true,
//             width: 500,
//             height: 600,
//             plotCfg: {
//                 margin: [80, 0, 80, 0]
//             },
//         };
//     }
//
//
//     render() {
//         return (
//             <div>
//                 <Chart
//                     data={this.state.data}
//                     width={this.state.width}
//                     height={this.state.height}
//                     plotCfg={this.state.plotCfg}
//                     forceFit={this.state.forceFit}
//                 />
//             </div>
//         );
//     }
// }
//
// export default CountryGenreSankeyChart;
