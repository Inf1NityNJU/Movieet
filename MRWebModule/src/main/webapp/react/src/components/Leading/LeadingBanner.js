import React from 'react';

import { Button } from 'antd';

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
                    <img src={logo} className={styles.logo}/>
                    <p>Meet the movie<br/>Meet the world</p>
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


export default LeadingBanner;
