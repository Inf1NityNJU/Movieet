import React from 'react';

import {Row, Col, Button} from 'antd';
import {routerRedux} from 'dva/router';
import {connect} from 'dva';

import LeadingBanner from '../Leading/LeadingBanner';
import LeadingFeatItem from  '../Leading/LeadingFeatItem';
import LeadingDataItem from  '../Leading/LeadingDataItem';
import LeadingTeamItem from  '../Leading/LeadingTeamItem';
import styles from './LeadingPage.css';

import photo_sorumi from '../../assets/img/photo_sorumi.png';
import photo_vivian from '../../assets/img/photo_vivian.png';
import photo_silver from '../../assets/img/photo_silver.png';
import photo_krayc from '../../assets/img/photo_krayc.png';

function LeadingPage({dispatch}) {

    function onDigOut() {
        dispatch(routerRedux.push({
            pathname: '/movies/discover',
        }));
    }

    return (
        <div>
            <LeadingBanner onDigOut={onDigOut}/>
            <div className={styles.feat}>
                <div className={styles.part}>
                    <h3 className={styles.title}>In Movieet, you could get</h3>
                    <div className={styles.main}>
                        <Row>
                            <Col span={8}>
                                <LeadingFeatItem
                                    className={styles.item}
                                    icon="inbox"
                                    title="Movie Collections"
                                    description="Record watched movies"
                                />
                            </Col>
                            <Col span={8}>
                                <LeadingFeatItem
                                    className={styles.item}
                                    icon="bars"
                                    title="Ranking & Trends"
                                    description="Know the popular ones"
                                />
                            </Col>
                            <Col span={8}>
                                <LeadingFeatItem
                                    className={styles.item}
                                    icon="star-o"
                                    title="Recommendations"
                                    description="Find you may like"
                                />
                            </Col>
                            <Col span={8}>
                                <LeadingFeatItem
                                    className={styles.item}
                                    icon="bar-chart"
                                    title="Data Analysis"
                                    description="What's behind the data"
                                />
                            </Col>
                            <Col span={8}>
                                <LeadingFeatItem
                                    className={styles.item}
                                    icon="line-chart"
                                    title="Predict score"
                                    description="Make combinations"
                                />
                            </Col>
                            <Col span={8}>
                                <LeadingFeatItem
                                    className={styles.item}
                                    icon="team"
                                    title="User system"
                                    description="Follow movie enthusiasts"
                                />
                            </Col>
                        </Row>
                    </div>
                </div>
            </div>


            <div className={styles.data}>
                <div className={styles.part}>
                    <h3 className={styles.title}>Data analyzed from</h3>
                    <div className={styles.main}>
                        <Row gutter={120}>
                            <Col span={8}>
                                <LeadingDataItem
                                    className={styles.item}
                                    title="81K"
                                    description="Movies"
                                />
                            </Col>
                            <Col span={8}>
                                <LeadingDataItem
                                    className={styles.item}
                                    title="3K"
                                    description="Directors"
                                />
                            </Col>
                            <Col span={8}>
                                <LeadingDataItem
                                    className={styles.item}
                                    title="11K"
                                    description="Actors"
                                />
                            </Col>
                        </Row>
                    </div>
                </div>
            </div>

            <div className={styles.introduction}>
                <div className={styles.bg_wrapper}>
                    <div className={styles.bg}/>
                </div>
                <div className={styles.text_wrapper}>
                </div>
            </div>

            <div className={styles.team}>
                <div className={styles.part}>
                    <h3 className={styles.title}>Who we are</h3>
                    <div className={styles.main}>
                        <Row>
                            <Col span={6}>
                                <LeadingTeamItem
                                    photo={photo_sorumi}
                                    title="Sorumi"
                                    description="User interface"
                                />
                            </Col>
                            <Col span={6}>
                                <LeadingTeamItem
                                    photo={photo_vivian}
                                    title="Vivian"
                                    description="Business logic"
                                />
                            </Col>
                            <Col span={6}>
                                <LeadingTeamItem
                                    photo={photo_silver}
                                    title="Silver"
                                    description="Algorithm analysis"
                                />
                            </Col>
                            <Col span={6}>
                                <LeadingTeamItem
                                    photo={photo_krayc}
                                    title="Kray.C"
                                    description="Data processing"
                                />
                            </Col>

                        </Row>
                    </div>
                </div>
            </div>
            <div className={styles.slogan}>
                <div className={styles.part}>
                    <h3>Nice to meet you !</h3>
                    <Button
                        size="large"
                        type="primary"
                        className={styles.button}
                        onClick={onDigOut}
                    >Dig it out !</Button>
                </div>
            </div>
        </div>
    );
}


export default connect()(LeadingPage);
