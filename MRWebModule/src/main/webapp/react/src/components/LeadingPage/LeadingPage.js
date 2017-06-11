import React from 'react';

import {Row, Col, Button} from 'antd';
import {routerRedux} from 'dva/router';
import {connect} from 'dva';

import {OverPack} from 'rc-scroll-anim';
import QueueAnim from 'rc-queue-anim';


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
                    <OverPack>
                        <QueueAnim
                            key="title"
                            delay={300}
                            animConfig={[
                                {opacity: [1, 0], translateY: [0, -30]},
                                {opacity: [1, 0], translateY: [0, -30]}
                            ]}
                        >
                            <h3 key="title" className={styles.title}>In Movieet, you could get</h3>
                        </QueueAnim>
                        <QueueAnim
                            key="queue"
                            delay={300}
                            animConfig={[
                                {opacity: [1, 0], translateY: [0, 50]},
                                {opacity: [1, 0], translateY: [0, 50]}
                            ]}
                        >
                            <LeadingFeatItem
                                key="a"
                                className={styles.item}
                                icon="inbox"
                                title="Movie Collections"
                                description="Record watched movies"
                            />
                            <LeadingFeatItem
                                key="b"
                                className={styles.item}
                                icon="bars"
                                title="Ranking & Trends"
                                description="Know the popular ones"
                            />
                            <LeadingFeatItem
                                key="c"
                                className={styles.item}
                                icon="star-o"
                                title="Recommendations"
                                description="Find you may like"
                            />
                            <LeadingFeatItem
                                key="d"
                                className={styles.item}
                                icon="bar-chart"
                                title="Data Analysis"
                                description="What's behind the data"
                            />
                            <LeadingFeatItem
                                key="e"
                                className={styles.item}
                                icon="line-chart"
                                title="Predict score"
                                description="Make combinations"
                            />
                            <LeadingFeatItem
                                key="f"
                                className={styles.item}
                                icon="team"
                                title="User system"
                                description="Follow movie enthusiasts"
                            />
                        </QueueAnim>
                    </OverPack>

                </div>
            </div>


            <div className={styles.data}>
                <div className={styles.part}>
                    <OverPack>
                        <QueueAnim
                            key="title"
                            animConfig={[
                                {opacity: [1, 0], translateY: [0, -30]},
                                {opacity: [1, 0], translateY: [0, -30]}
                            ]}
                        >
                            <h3 key="title" className={styles.title}>Data analyzed from</h3>
                        </QueueAnim>
                        <QueueAnim
                            key="queue"
                            interval={0}
                            animConfig={[
                                {opacity: [1, 0], translateY: [0, 50]},
                                {opacity: [1, 0], translateY: [0, 50]}
                            ]}
                        >
                            <LeadingDataItem
                                key="a"
                                className={styles.item + ' ' + styles.item_1}
                                title="81K"
                                description="Movies"
                            />

                            <LeadingDataItem
                                key="b"
                                className={styles.item + ' ' + styles.item_2}
                                title="3K"
                                description="Directors"
                            />

                            <LeadingDataItem
                                key="c"
                                className={styles.item + ' ' + styles.item_3}
                                title="11K"
                                description="Actors"
                            />
                        </QueueAnim>
                    </OverPack>

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
                    <OverPack>
                        <QueueAnim
                            key="title"
                            animConfig={[
                                {opacity: [1, 0], translateY: [0, -30]},
                                {opacity: [1, 0], translateY: [0, -30]}
                            ]}
                        >
                            <h3 key="title" className={styles.title}>Who we are</h3>
                        </QueueAnim>

                        <QueueAnim
                            key="queue"
                            interval={0}
                            animConfig={[
                                {opacity: [1, 0], translateY: [0, 50]},
                                {opacity: [1, 0], translateY: [0, 50]}
                            ]}
                        >

                            <LeadingTeamItem
                                key="a"
                                className={styles.item}
                                photo={photo_sorumi}
                                title="Sorumi"
                                description="User interface"
                            />

                            <LeadingTeamItem
                                key="b"
                                className={styles.item}
                                photo={photo_vivian}
                                title="Vivian"
                                description="Business logic"
                            />

                            <LeadingTeamItem
                                key="c"
                                className={styles.item}
                                photo={photo_silver}
                                title="Silver"
                                description="Algorithm analysis"
                            />
                            <LeadingTeamItem
                                key="d"
                                className={styles.item}
                                photo={photo_krayc}
                                title="Kray.C"
                                description="Data processing"
                            />

                        </QueueAnim>
                    </OverPack>
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
