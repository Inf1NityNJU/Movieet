import React from 'react';
import { connect } from 'dva';
import { Icon, Pagination } from 'antd';

import { PREVIEW_COLLECT_SIZE, PREVIEW_EVALUATE_SIZE, USER_MOVIE_SIZE, USER_MOVIE_STATUS } from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';

import styles from './UserPage.css';

function UserMoviePage({ dispatch, status, result }) {

  //function onMoreClick(status) {
  //  console.log(status);
  //  dispatch({
  //    type: 'user/fetchUserMovie',
  //    payload: status
  //  })
  //}

  function onPageChange(pageNumber) {
    console.log('Page: ', pageNumber);
  }


  return (
    <div className={styles.movie_page}>

      {
        //(status === 'All' || status === 'Collect') ?
          <div className={styles.part}>
            <div className={styles.title}>
              <h3>Want to watch</h3>
              {/*
                status === 'Collect' ? null :
                  <a className={styles.title_right}
                     onClick={() => onMoreClick("Collect")}>
                    More<Icon type="double-right"/>
                  </a>
              */}
            </div>
            <MovieListSmall num={PREVIEW_COLLECT_SIZE}/>
            {/*
              status === 'Collect' ?
                <Pagination
                  className={styles.page}
                  showQuickJumper
                  defaultCurrent={1}
                  pageSize={ USER_MOVIE_SIZE }
                  total={100}
                  onChange={onPageChange}/>
                : null
            */}
          </div>
          //: null
      }
      {
        //(status === 'All' || status === 'Evaluate') ?
          <div className={styles.part}>
            <div className={styles.title}>
              <h3>Had watched</h3>
              {/*
                status === 'Evaluate' ? null :
                  <a className={styles.title_right}
                     onClick={() => onMoreClick("Evaluate")}>
                    More<Icon type="double-right"/>
                  </a>
              */}
            </div>
            <MovieListSmall num={PREVIEW_EVALUATE_SIZE}/>
            {/*
              status === 'Evaluate' ?
                <Pagination
                  className={styles.page}
                  showQuickJumper
                  defaultCurrent={1}
                  pageSize={ USER_MOVIE_SIZE }
                  total={100}
                  onChange={onPageChange}/>
                : null
            */}
          </div>
          //: null
      }

    </div>
  )
}

function mapStateToProps(state) {
  const { movie } = state.user;
  return {
    status: movie.status,
    result: movie.result,
  };
}


export default connect(mapStateToProps)(UserMoviePage);
