import React from 'react';

import {Tag} from 'antd';

import styles from './TypeSelect.css';

import {ESTIMATE_TYPE} from '../../constants'


function TypeSelect({status, onChange, className = ''}) {

    const CheckableTag = Tag.CheckableTag;

    return (
        <div className={styles.select + ' ' + className}>
            {ESTIMATE_TYPE.map((type) =>
                <CheckableTag
                    key={type.value}
                    checked={type.value === status}
                    onChange={checked => onChange(type.value)}
                >
                    {type.name}
                </CheckableTag>
            )}
        </div>
    );
}

export default TypeSelect;
