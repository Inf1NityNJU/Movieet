import React from 'react';
import {connect} from 'dva';
import {Icon, Pagination, Spin} from 'antd';
import {routerRedux} from 'dva/router';

import {
  PREVIEW_COLLECT_SIZE, PREVIEW_EVALUATE_SIZE,
  COLLECT_SIZE, EVALUATE_SIZE, USER_MOVIE_STATUS
} from '../../constants'

import MovieListSmall from '../MovieList/MovieListSmall';

import styles from './UserPage.css';

function UserMoviePage({dispatch, status, result, page, totalCount, collectLoading, evaluateLoading}) {

  function onMoreClick(status) {
    dispatch(routerRedux.push({
      pathname: '/user/movie/' + status
    }));
  }

  function onPageChange(page) {
    dispatch({
      type: 'user/changePage',
      payload: page
    });
  }


  return (
    <div className={styles.movie_page}>

      { (status === null || status === 'collect') ?

        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Want to watch</h3>
            {
              status === 'collect' ? null :
                <a className={styles.title_right}
                   onClick={() => onMoreClick("collect")}
                >
                  More<Icon type="double-right"/>
                </a>
            }
          </div>
          {collectLoading ?
            <div className={styles.spin}>
              <Spin/>
            </div> : null
          }
          { !collectLoading && result.collect && result.collect.length > 0 ?
            <MovieListSmall
              num={PREVIEW_COLLECT_SIZE}
              list={result.collect}
            /> : null
          }

          { status === 'collect' ?
            <Pagination
              className={styles.page}
              showQuickJumper
              defaultCurrent={1}
              pageSize={ COLLECT_SIZE }
              total={totalCount}
              current={page}
              onChange={onPageChange}/>
            : null
          }
        </div>
        : null
      }
      { (status === null || status === 'evaluate') ?
        <div className={styles.part}>
          <div className={styles.title}>
            <h3>Had watched</h3>
            {
              status === 'evaluate' ? null :
                <a className={styles.title_right}
                   onClick={() => onMoreClick("evaluate")}
                >
                  More<Icon type="double-right"/>
                </a>
            }
          </div>
          {evaluateLoading ?
            <div className={styles.spin}>
              <Spin/>
            </div> : null
          }
          {!evaluateLoading && result.evaluate && result.evaluate.length > 0 ?
            <MovieListSmall
              num={PREVIEW_EVALUATE_SIZE}
              list={result.evaluate}
            /> : null
          }
          {
            status === 'evaluate' ?
              <Pagination
                className={styles.page}
                showQuickJumper
                defaultCurrent={1}
                pageSize={ EVALUATE_SIZE }
                total={totalCount}
                current={page}
                onChange={onPageChange}/>
              : null
          }
        </div>
        : null
      }

    </div>
  )
}

function mapStateToProps(state) {
  const {movie} = state.user;
  return {
    status: movie.status,
    result: movie.result,
    page: movie.page,
    totalCount: movie.totalCount,
    collectLoading: state.loading.effects['user/fetchCollectMovies'],
    evaluateLoading: state.loading.effects['user/fetchEvaluateMovies'],
  };
}


export default connect(mapStateToProps)(UserMoviePage);
