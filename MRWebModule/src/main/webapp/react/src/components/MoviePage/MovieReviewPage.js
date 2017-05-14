import React from 'react';

import { Pagination } from 'antd';

import { REVIEW_SIZE } from '../../constants'

import ReviewList from '../ReviewList/ReviewList';

import styles from './MoviePage.css';

function MovieReviewPage() {

  function onPageChange(pageNumber) {
    console.log('Page: ', pageNumber);
  }

  return (
    <div className={styles.review_wrapper + " container"}>

      <div className={styles.part}>
        <div className={styles.title}>
          <h3>Reviews</h3>
        </div>
        <ReviewList num={REVIEW_SIZE}/>

        <Pagination
          className={styles.page}
          showQuickJumper
          defaultCurrent={2}
          pageSize={ REVIEW_SIZE }
          total={100}
          onChange={onPageChange}/>,

      </div>

    </div>

  )
}

export default MovieReviewPage;
