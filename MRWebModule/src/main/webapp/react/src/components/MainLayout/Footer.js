/**
 * Created by Sorumi on 17/5/11.
 */
import React from 'react';
import { Link } from 'dva/router';
import icon from '../../assets/img/icon.png';
import styles from './Footer.css';

function Footer() {
  return (
    <div className={styles.footer}>
      <div className={styles.title_wrapper}>
        <img className={styles.icon} src={icon}/>
        <div className={styles.title_text}>
          <Link className={styles.main_title}>Movieet</Link>
          <p className={styles.sub_title}>meet the movie</p>
        </div>
      </div>
      <p className={styles.copyright}>Developed by Infinity &copy; 2017</p>
    </div>
  )
}

export default Footer;
