import React from 'react';

import {Button} from 'antd';
import {OverPack} from 'rc-scroll-anim';
import QueueAnim from 'rc-queue-anim';

import logo from '../../assets/img/logo.png';

import styles from './LeadingBanner.css';

function LeadingBanner({onDigOut}) {

    return (
        <div className={styles.banner}>

            <div>
                <div className={styles.bg_wrapper}>
                    <div className={styles.bg_img + ' ' + styles.bg_img1}/>
                </div>
            </div>


            <div className="container">
                <div className={styles.logo_wrapper}>

                    <OverPack>
                        <QueueAnim
                            key="title"
                            delay={300}
                            animConfig={[
                                {opacity: [1, 0], translateY: [0, -50]},
                                {opacity: [1, 0], translateY: [0, -50]}
                            ]}
                        >
                            <img key="logo" src={logo} className={styles.logo}/>
                            <p key="text">Meet the movie<br/>Meet the world</p>
                            <Button
                                key="button"
                                size="large"
                                type="primary"
                                className={styles.button}
                                onClick={onDigOut}
                            >Dig it out !
                            </Button>

                        </QueueAnim>
                    </OverPack>

                </div>
            </div>

        </div>
    );

}


export default LeadingBanner;
