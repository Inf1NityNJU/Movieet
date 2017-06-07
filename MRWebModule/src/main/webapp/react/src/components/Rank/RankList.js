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

    let cards = [];
    // num = Math.min(num, list.length);
    for (let i = 0; i < num; i++) {
        cards.push(
            <RankCard
                key={i}
                onClick={onCardClick}
                // onClick={() => onCardClick(list[i].id)}
                // people={list[i]}
            />
        );
    }

    return (
        <Row gutter={20}>
            <Col span={8}>
                {cards}
            </Col>
            <Col span={8}>
                {cards}
            </Col>
            <Col span={8}>
                {cards}
            </Col>
        </Row>
    );
}

export default connect()(RankList);
