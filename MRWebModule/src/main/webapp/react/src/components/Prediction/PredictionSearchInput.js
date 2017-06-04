import React from 'react';

import {Icon, Input, AutoComplete} from 'antd';

import styles from './PredictionSearchInput.css';

import {PREDICTION_SEARCH_SIZE} from '../../constants';

const Option = AutoComplete.Option;
const OptGroup = AutoComplete.OptGroup;

function PredictionSearchInput({dispatch, keyword, search: {genres, directors, actors}}) {
  const dataSource = [
    {
      title: 'Genre',
      children: genres.result,
      more: genres.page * PREDICTION_SEARCH_SIZE < genres.totalCount,
    },
    {
      title: 'Director',
      children: directors.result,
      more: directors.page * PREDICTION_SEARCH_SIZE < directors.totalCount,
    },
    {
      title: 'Actor',
      children: actors.result,
      more: actors.page * PREDICTION_SEARCH_SIZE < actors.totalCount,
    },

  ];

  const options = dataSource.map( group => (
    <OptGroup
      key={group.title}
      label={renderTitle(group.title, group.more)}
    >
      {group.children.map(o => (
        <Option key={o.id} value={group.title + ' ' + o.id}>
          {o.name ? o.name : o.value}
        </Option>
      ))}
    </OptGroup>
  ));

  function renderTitle(title, more) {
    return (
      <span>
      {title}
        {more ?
          <a
            style={{float: 'right'}}
            onClick={() => handleMore(title)}
          >
            More
          </a> : null
        }
    </span>
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

export default PredictionSearchInput;
