import React from 'react';
import {Row, Col} from 'antd';

import BlankPhoto from '../Util/BlankPhoto';

import styles from './PeopleBrief.css';

function PeopleBrief({people}) {
    return (
        <div className={styles.main}>
            <div className="container">
                <Row>

                    <Col span={7}>
                        <div className={styles.photo_wrapper}>

                            {people.photo ?
                                <div className={styles.photo} style={{backgroundImage: `url(${people.photo})`}}/> :
                                <BlankPhoto
                                    className={styles.photo}
                                    size="large"
                                >
                                    No Photo
                                </BlankPhoto>
                            }
                        </div>
                    </Col>

                    <Col offset={1} span={16} className={styles.col_2}>
                        <h1 className={styles.name}>{people.name}</h1>
                    </Col>

                </Row>
            </div>
        </div>
    );
}

export default PeopleBrief;
