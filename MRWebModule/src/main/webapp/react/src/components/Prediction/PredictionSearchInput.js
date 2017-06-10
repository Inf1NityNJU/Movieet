import React from 'react';
import {connect} from 'dva';
import {Icon, Input, AutoComplete, Spin} from 'antd';

import styles from './PredictionSearchInput.css';

import {PREDICTION_SEARCH_SIZE} from '../../constants';

const Option = AutoComplete.Option;
const OptGroup = AutoComplete.OptGroup;

function PredictionSearchInput({dispatch, keyword, search: {genres, directors, actors}, directorLoading, actorLoading, moreDirectorLoading, moreActorLoading}) {
    const dataSource = [
        {
            title: 'Genre',
            children: genres.result,
            more: genres.page * PREDICTION_SEARCH_SIZE < genres.totalCount,
            loading: false,
        },
        {
            title: 'Director',
            children: directors.result,
            more: directors.page * PREDICTION_SEARCH_SIZE < directors.totalCount,
            loading: directorLoading || moreDirectorLoading
        },
        {
            title: 'Actor',
            children: actors.result,
            more: actors.page * PREDICTION_SEARCH_SIZE < actors.totalCount,
            loading: actorLoading || moreActorLoading
        },

    ];

    const options = dataSource.map(group => (

            <OptGroup
                key={group.title}
                label={renderTitle(group.title, group.more, group.loading)}
            >
                {group.children.length === 0 && group.loading ?
                    <Option key={group.title} disabled>
                    </Option> :

                    group.children.map(o => (
                        <Option key={o.id} value={group.title + ' ' + o.id}>
                            {o.name ? o.name : o.value}
                        </Option>
                    ))
                }
            </OptGroup>
    ));

    function renderTitle(title, more, loading) {
        return (
            <div>
                <span>
                    {title}
                    {more ?
                        <a
                            style={{marginLeft: '20px'}}
                            onClick={() => handleMore(title)}
                        >
                            More
                        </a> : null
                    }
                </span>
                {loading ?
                    <div className={styles.spin}>
                        <Spin/>
                    </div> : null
                }
            </div>
        );
    }


    function handleSearch(value) {
        dispatch({
            type: 'prediction/changeKeyword',
            payload: value,
        });
    }

    function handleSelect(value) {

        const [type, id] = value.split(' ');
        const item = dataSource.filter(o => o.title === type)[0].children
            .filter(t => t.id === parseInt(id))[0];

        console.log(item);

        dispatch({
            type: 'prediction/addCurrent' + type,
            payload: item,
        });
        dispatch({
            type: 'prediction/changeKeyword',
            payload: null,
        });
    }

    function handleMore(type) {
        console.log(type);
        dispatch({
            type: 'prediction/fetchMore' + type + 's',
            payload: {},
        })
    }

    return (
        <div className={styles.search_wrapper}>
            <AutoComplete
                className={styles.auto}
                dropdownClassName={styles.dropdown}
                dropdownMatchSelectWidth={false}
                dropdownStyle={{width: 300}}
                size="large"
                style={{width: '100%'}}
                dataSource={options}
                placeholder="genre / director / actor / keyword"
                optionLabelProp="value"
                onSearch={handleSearch}
                onSelect={handleSelect}
                value={keyword}
            >
                <Input
                    className={styles.search_input}
                    prefix={<Icon type="search" className={styles.icon}/>}
                />
            </AutoComplete>
        </div>
    );
}

function mapStateToProps(state) {
    return {
        directorLoading: state.loading.effects['prediction/fetchDirectorsByKeyword'],
        actorLoading: state.loading.effects['prediction/fetchActorsByKeyword'],
        moreDirectorLoading: state.loading.effects['prediction/fetchMoreDirectors'],
        moreActorLoading: state.loading.effects['prediction/fetchMoreActors'],
    };
}

export default connect(mapStateToProps)(PredictionSearchInput);
