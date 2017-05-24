import React, { Component } from 'react';
import { Tag } from 'antd';

import styles from './GenreFilter.css';

import { GENRES } from '../../constants'


const CheckableTag = Tag.CheckableTag;

class GenreFilter extends Component {

  handleChange(tag, checked) {
    const { genres, onChange } = this.props;

    let nextSelectedGenres;
    if (GENRES.indexOf(tag) === 0 && checked) {
      nextSelectedGenres = [tag];
      onChange(nextSelectedGenres);

    } else if (GENRES.indexOf(tag) > 0) {
      const all = GENRES[0];
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
            key={genre}
            checked={genres.indexOf(genre) > -1}
            onChange={checked => this.handleChange(genre, checked)}
          >
            {genre}
          </CheckableTag>
        )}
      </div>

    );
  }
}
export default GenreFilter;
