/**
 * Created by Sorumi on 17/6/7.
 */
import React from 'react';
import {Menu, Icon} from 'antd';
import {Link} from 'dva/router';
import styles from './SubMenu.css';

function Analysis({user}) {
    const MenuItem = Menu.Item;
    return (
        <Menu
            className={styles.sub_menu}
            selectedKeys={[location.pathname]}
            mode="horizontal"
            theme="light"
        >
            <MenuItem key="/analysis/rank">
                <Link to="/analysis/rank">
                    <Icon type="bars"/>Rank
                </Link>
            </MenuItem>
            <MenuItem key="/analysis/data">
                <Link to="/analysis/data">
                    <Icon type="line-chart"/>Data
                </Link>
            </MenuItem>
        </Menu>
    );
}

export default Analysis;
