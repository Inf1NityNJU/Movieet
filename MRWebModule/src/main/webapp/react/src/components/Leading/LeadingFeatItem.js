import React from 'react';

import {Icon} from 'antd';

import styles from './LeadingFeatItem.css';

function LeadingFeatItem({icon, title, description, className}) {
    return (
        <div className={styles.item + ' ' + className}>
            <div className={styles.icon_wrapper}>
                <Icon className={styles.icon} type={icon} />
            </div>
            <div className={styles.text_wrapper}>
                <h6 className={styles.title}>{title}</h6>
                <p className={styles.description}>{description}</p>
            </div>
        </div>
    );
}

export default LeadingFeatItem;
