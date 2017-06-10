import React from 'react';
import {Tag} from 'antd';

import styles from './Analysis.css';

import {GENRES} from '../../constants'

const CheckableTag = Tag.CheckableTag;

function GenreSelect({ currentGenre, onChange }) {

    return (
        <div className={styles.select}>
            {GENRES.slice(1, GENRES.length).map((genre) =>
                <CheckableTag
                    key={genre.id}
                    checked={genre.id === currentGenre}
                    onChange={checked => onChange(genre.id)}
                >
                    {genre.value}
                </CheckableTag>
            )}
        </div>
    );
}

export default GenreSelect;