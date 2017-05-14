import React from 'react';
import { Row, Col, Icon, Tag } from 'antd';

import styles from './RecentSearch.css';

function RecentSearch({ recent, onClick }) {
  return (
    <div className={styles.recent_search}>
      <Row>
        <Col span={1}>
          <Icon type="clock-circle-o"/>
        </Col>
        <Col span={21} offset={1}>
          <div className={styles.tags}>
            {recent.map((keyword) =>
              <Tag
                key={keyword}
                onClick={() => onClick(keyword)}
              >
                {keyword}
              </Tag>
            )}
          </div>
        </Col>
      </Row>
    </div>
  );
}

export default RecentSearch;
