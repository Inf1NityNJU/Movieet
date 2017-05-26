import React from 'react';
import { connect } from 'dva';


import UserMoviePage from '../components/UserPage/UserMoviePage';


function UserMovie() {
  return (

    <UserMoviePage/>

  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(UserMovie);
