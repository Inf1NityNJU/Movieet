import React, { Component }  from 'react';
import SortButton from '../Util/SortButton';

import styles from './MovieSort.css';

import { MOVIE_SORT, ORDER } from '../../constants'

const MOVIE_SORT_NAME = {
  'date': 'Released date',
  'score': 'Average score'
};


class MovieSort extends Component {

  handleChange(sortName) {
    const { currentSort, onChange } = this.props;
    const nextOrder = sortName === currentSort.name ?
      ORDER.filter(o => o !== currentSort.order)[0] :
      currentSort.order;

    onChange(sortName, nextOrder);

  }
  render() {
    const { currentSort, className } = this.props;
    return (
      <div className={styles.buttons + ' ' + className}>
        {MOVIE_SORT.map((sortName) =>
          <SortButton
            key={sortName}
            checked={sortName === currentSort.name}
            order={currentSort.order}
            onChange={(check) => this.handleChange(sortName, check)}
          >
            {MOVIE_SORT_NAME[sortName]}
          </SortButton>
        )}

      </div>
    );
  }
}

export default MovieSort;
