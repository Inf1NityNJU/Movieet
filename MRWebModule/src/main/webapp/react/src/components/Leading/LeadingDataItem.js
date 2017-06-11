import React from 'react';
import styles from './LeadingDataItem.css';

function LeadingDataItem({title, description, className = ''}) {
    return (
        <div className={styles.item + ' ' + className}>
            <h6>{title}</h6>
            <p>{description}</p>
        </div>
    );
}

export default LeadingDataItem;
