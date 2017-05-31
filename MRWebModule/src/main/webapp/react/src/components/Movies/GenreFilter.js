import React, { Component } from 'react';
import { Tag } from 'antd';

import styles from './GenreFilter.css';

import { GENRES } from '../../constants'


const CheckableTag = Tag.CheckableTag;

class GenreFilter extends Component {

  handleChange(tag, checked) {
    const { genres, onChange } = this.props;

    let nextSelectedGenres;
    if (tag === 0 && checked) {
      nextSelectedGenres = [tag];
      onChange(nextSelectedGenres);

    } else {
      const all = GENRES[0].id;
      nextSelectedGenres = checked ?
        [...genres, tag] :
        genres.filter(t => t !== tag);

      nextSelectedGenres = nextSelectedGenres.filter(t => t !== all);
      if (nextSelectedGenres.length ===0 ) {
        nextSelectedGenres.push(all);
      }
      onChange(nextSelectedGenres);
    }

  }

  render() {
    const { genres, className } = this.props;
    return (
      <div className={styles.genre_tags + ' ' + className}>
        {GENRES.map((genre) =>
          <CheckableTag
            key={genre.id}
            checked={genres.indexOf(genre.id) > -1}
            onChange={checked => this.handleChange(genre.id, checked)}
          >
            {genre.value}
          </CheckableTag>
        )}
      </div>

    );
  }
}
export default GenreFilter;
