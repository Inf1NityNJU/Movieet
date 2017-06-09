import React, {Component} from 'react';
import createG2 from 'g2-react';
import G2, {Stat, Global} from 'g2';

import {Tag} from 'antd';

import styles from './Analysis.css';

import {GENRES, GENRES_MAP} from '../../constants'

const Chart = createG2(chart => {
    chart.col('year', {
        alias: 'Year',
        tickCount: 12,
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

class GenreLineChart extends Component {

    constructor(...argus) {
        super(...argus);

        this.state = {
            currentGenre: GENRES[1],
            forceFit: false,
            width: 960,
            height: 500,
            plotCfg: {
                margin: [80, 80]
            },
        };
    }

    onChange = (id) => {
        this.setState({
            currentGenre: id,
        });
        this.props.onGenreChange(id);
    };

    render() {
        const CheckableTag = Tag.CheckableTag;

        let data = this.props.data;

        data = data.map(o => {
            // console.log(o.id, GENRES_MAP[o.id]);
            return {
                ...o,
                year: o.year + '',
                count: o.count * 100,
                genre: GENRES_MAP[o.id],

            }
        });

        return (
            <div>
                <div className={styles.select}>
                    {GENRES.slice(1, GENRES.length).map((genre) =>
                        <CheckableTag
                            key={genre.id}
                            checked={genre.id === this.state.currentGenre}
                            onChange={checked => this.onChange(genre.id)}
                        >
                            {genre.value}
                        </CheckableTag>
                    )}
                </div>
                <Chart
                    data={data}
                    width={this.state.width}
                    height={this.state.height}
                    plotCfg={this.state.plotCfg}
                    forceFit={this.state.forceFit}
                />
            </div>
        );
    }
}

export default GenreLineChart;


