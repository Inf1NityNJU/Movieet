import React from 'react';
import {connect} from 'dva';

import {Spin} from 'antd';

import PeopleBrief from '../People/PeopleBrief';
import MovieListSmall from '../MovieList/MovieListSmall';

import styles from './PeoplePage.css';

function PeoplePage({people}) {
  return (

    <div className={styles.normal}>

      {people ?
        <div>
          <PeopleBrief people={people}/>

          <div className="container">
            <div className={styles.part}>
              <div className={styles.title}>
                <h3>Movies</h3>
              </div>
              <MovieListSmall
                num={people.movies.length}
                list={people.movies}
              />
            </div>
          </div>

        </div> :
          <div className={styles.spin}>
            <Spin/>
          </div>
      }
    </div>
  );
}

function mapStateToProps(state) {
  const {people} = state.people;
  return {
    people,
  };
}

export default connect(mapStateToProps)(PeoplePage);
