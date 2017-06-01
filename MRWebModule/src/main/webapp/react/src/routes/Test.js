import React from 'react';
import {connect} from 'dva';
import styles from './Test.css';

import MovieScoreChart from '../components/Movie/MovieScoreChart';
function Test({language}) {
  let keys = language === null ? null : Object.keys(language);
  console.log(keys);

  let array;
  if (language !== null) {
    array = [];
    for(let key in language) {
      let value = language[key];
      console.log(key, value);
      array.push({
        id: key,
        value: value.name,
      })
    }



  }
  return (
    <div className={styles.normal}>

      { array ?
        array.map(a =>
        <div key={a.id}>
          <span>{a.id}</span>
          <span>{'("' + a.value + '"),'}</span>
        </div>
        ) : null
      }
    </div>
  );
}

function mapStateToProps(state) {
  return {
    language: state.test.language,
  };
}

export default connect(mapStateToProps)(Test);
