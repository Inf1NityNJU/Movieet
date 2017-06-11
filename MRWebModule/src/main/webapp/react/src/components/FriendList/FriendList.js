import React from 'react';
import {Col, Row} from 'antd';
import {routerRedux} from 'dva/router';
import {connect} from 'dva';

import styles from './FriendList.css';

import FriendCard from '../Friend/FriendCard';

function FriendList({dispatch, num, list}) {

    function onCardClick(id) {
        dispatch(routerRedux.push({
            pathname: '/user/' + id,
        }));
    }

    let cards = [];
    num = Math.min(num, list.length);
    for (let i = 0; i < num; i++) {
        cards.push(
            <Col key={i} span={6} className={styles.card}>
                <FriendCard
                    onClick={() => onCardClick(list[i].id)}
                    friend={list[i]}
                />
            </Col>
        );
    }

    return (
        <Row gutter={20}>
            {cards}
        </Row>
    );
}

export default connect()(FriendList);
