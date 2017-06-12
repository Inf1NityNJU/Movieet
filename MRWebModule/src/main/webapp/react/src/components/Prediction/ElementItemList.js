import React from 'react';
import {Col, Row} from 'antd';

import styles from './ElementItemList.css';

import ElementItem from './ElementItem';

function ElementItemList({num, img, list, onCheckChange, onItemRemove}) {


    let items = [];
    num = num ? Math.min(num, list.length) : list.length;

    for (let i = 0; i < num; i++) {
        items.push(
            <Col key={i} span={6} className={styles.item}>
                <ElementItem
                    img={img}
                    item={list[i]}
                    onCheckChange={onCheckChange}
                    onItemRemove={onItemRemove}
                />
            </Col>
        );
    }

    return (
        <Row gutter={10}>
            {items}
        </Row>
    );
}

export default ElementItemList;
