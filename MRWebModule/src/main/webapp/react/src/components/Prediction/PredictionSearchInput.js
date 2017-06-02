import React from 'react';

import {Icon, Input, AutoComplete} from 'antd';

import styles from './PredictionSearchInput.css';

const Option = AutoComplete.Option;
const OptGroup = AutoComplete.OptGroup;

function PredictionSearchInput({dispatch, search}) {
  const dataSource = [
    {
      title: 'Genre',
      children: search.genres.result
    },
    {
      title: 'Director',
      children: search.directors.result
    },
    {
      title: 'Actor',
      children: search.actors.result
    },

  ];

  const options = dataSource.map(group => (
    <OptGroup
      key={group.title}
      label={renderTitle(group.title)}
    >
      {group.children.map(o => (
        <Option key={o.id} value={o.name}>
          {o.name}
          {/*<span className="certain-search-item-count">{opt.count} 人 关注</span>*/}
        </Option>
      ))}
    </OptGroup>
  ));

  function renderTitle(title) {
    return (
      <span>
      {title}
        <a
          style={{float: 'right'}}
          onClick={() => handleMore(title)}
        >
          More
        </a>
    </span>
    );
  }


  function handleSearch(value) {
    console.log(value);
    dispatch({
      type: 'prediction/changeKeyword',
      payload: value,
    })
  }

  function handleSelect(value) {
    console.log(value);
    // dispatch({
    //   type: 'prediction/changeKeyword',
    //   payload: value,
    // })
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
