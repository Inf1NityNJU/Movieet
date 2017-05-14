import React, { Component } from 'react';
import { Card, Rate, Tag, Button } from 'antd';
import styles from './ReviewCard.css';

import avatar from '../../assets/img/avatar.png';

class ReviewCard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isWrap: true,
    };
  }
  onClickAll = () =>{
    this.setState({
      isWrap: !this.state.isWrap
    });
  };

  render() {
    return (
      <Card className={styles.card}>
        <div className={styles.normal}>

          <div className={styles.avatar_wrapper}>
            <div className={styles.avatar} style={{ backgroundImage: `url(${avatar})`}}></div>
          </div>
          <div className={styles.text_wrapper}>
            <p className={styles.user_name}>user name</p>
            <h5 className={styles.summary}>Absolutely fantastic! Recommended for everyone.</h5>
            <Rate className={styles.rate} disabled allowHalf defaultValue={3.5}/>
            <span className={styles.date}>2017.01.02</span>
            <p className={styles.content + (this.state.isWrap ? ' ' + styles.content_wrap : '')}>
              Amazing characters, scenery, city life and town life, story. Everything is just wow. So fine! The
              suspension, the empathy, the scenes. The thoughts. It's playful and pleasant, but serious and funny at the
              same time. It makes you want to feel it and live it, while you just keep cheering for everything.
              Amazing characters, scenery, city life and town life, story. Everything is just wow. So fine! The
              suspension, the empathy, the scenes. The thoughts. It's playful and pleasant, but serious and funny at the
              same time. It makes you want to feel it and live it, while you just keep cheering for everything.
              Amazing characters, scenery, city life and town life, story. Everything is just wow. So fine! The
              suspension, the empathy, the scenes. The thoughts. It's playful and pleasant, but serious and funny at the
              same time. It makes you want to feel it and live it, while you just keep cheering for everything.

            </p>
            <Tag className={styles.all} onClick={this.onClickAll}>{this.state.isWrap ? 'View All' : 'Wrap Up'}</Tag>
            <div className={styles.helpfulness}>
              <Button icon="like-o">94</Button>
              <Button icon="dislike-o">8</Button>
            </div>

          </div>
        </div>
      </Card>
    );
  }
}

export default ReviewCard;
