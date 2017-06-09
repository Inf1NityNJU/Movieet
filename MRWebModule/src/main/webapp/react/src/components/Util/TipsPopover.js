import React from 'react';

import {Popover, Icon} from 'antd';

import styles from './TipsPopover.css';

function TipsPopover({children}) {

    const content = (
        <div>
            {children}
        </div>
    );

    return (
        <Popover
            overlayClassName={styles.popover}
            placement="bottomRight"
            content={children}
            title="Tips"
        >
            <Icon
                className={styles.icon}
                type="exclamation-circle"
            />
        </Popover>
    );
}

export default TipsPopover;
