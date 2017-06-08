import React from 'react';
import {Col, Row} from 'antd';
import {routerRedux} from 'dva/router';
import {connect} from 'dva';

import styles from './RankList.css';

import RankCard from '../Rank/RankCard';

function RankList({dispatch, type, num, list}) {

    function onCardClick(id) {
        console.log(id);
        dispatch(routerRedux.push({
            pathname: '/' + type + '/' + id,
        }));
    }

    let cards_1 = [];
    let cards_2 = [];
    let cards_3 = [];

    // num = Math.min(num, list.length);
    let col = Math.ceil(num / 3);

    for (let i = 0; i < col; i++) {
        cards_1.push(
            <RankCard
                key={i}
                // className={styles.card}
                onClick={onCardClick}
                // onClick={() => onCardClick(list[i].id)}
                // people={list[i]}
            />
        );
    }
    for (let i = col; i < col * 2; i++) {
        cards_2.push(
            <RankCard
                key={i}
                // className={styles.card}
                onClick={onCardClick}
                // onClick={() => onCardClick(list[i].id)}
                // people={list[i]}
            />
        );
    }
    for (let i = col * 2; i < num; i++) {
        cards_3.push(
            <RankCard
                key={i}
                // className={styles.card}
                onClick={onCardClick}
                // onClick={() => onCardClick(list[i].id)}
                // people={list[i]}
            />
        );
    }

    return (
        <Row
            gutter={20}>
            <Col span={8}>
                {cards_1}
            </Col>
            <Col span={8}>
                {cards_2}
            </Col>
            <Col span={8}>
                {cards_3}
            </Col>
        </Row>
    );
}

export default connect()(RankList);
