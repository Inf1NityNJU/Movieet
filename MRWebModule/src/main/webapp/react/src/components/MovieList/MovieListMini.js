import React from 'react';
import { Card, Col, Row } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';

import styles from './MovieList.css';

import MovieCardMini from '../Movie/MovieCardMini';

function MovieListMini({ dispatch, list, num }) {

  function onCardClick(id) {
    dispatch(routerRedux.push({
      pathname: '/movie/' + id,
    }));
  }

  let cards = [];
  // num = Math.min(num, list.length);
  for (let i = 0; i < num; i++) {
    cards.push(
      <div key={i} className={styles.col_5}>
        <MovieCardMini
            onClick={onCardClick}
          // onClick={() => onCardClick(list[i].id)}
          // movie={list[i]}
        />
      </div>
    );
  }

  return (
    <div className={styles.row_5}>
      {cards}
    </div>
  );
}

export default connect()(MovieListMini);
