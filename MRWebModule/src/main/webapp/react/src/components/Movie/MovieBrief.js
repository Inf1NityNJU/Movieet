import React from 'react';

import {connect} from 'dva';
import {Link} from 'dva/router';

import {Row, Col, Icon, Button, Rate, Tag} from 'antd';

import MovieEvaluate from './MovieEvaluate';
import styles from './MovieBrief.css';

import example from '../../assets/img/example.png';
import {USER_MOVIE_STATUS} from '../../constants';

class MovieBrief extends React.Component {

  state = {
    loading: false,
    visible: false,
  };

  onClickCollect = () => {
    const {dispatch, movie, user} = this.props;
    dispatch({
      type: 'movie/collectMovie',
      payload: movie.id,
    });
  };

  onClickRemoveCollect = () => {
    const {dispatch, movie, user} = this.props;
    dispatch({
      type: 'movie/removeCollectMovie',
      payload: movie.id,
    });
  };

  onClickEvaluate = () => {
    this.setState({
      visible: true,
    });
  };
  onEvaluateOk = (evaluate) => {
    const {dispatch, movie, user} = this.props;
    this.setState({loading: true});
    dispatch({
      type: 'movie/evaluateMovie',
      payload: {
        id: movie.id,
        evaluate: {...evaluate, tags: []},
      },
      onComplete: () => {
        this.setState({loading: false, visible: false});
      }
    });
  };
  onEvaluateCancel = () => {
    this.setState({visible: false});
  };

  onClickRemoveEvaluate = () => {
    const {dispatch, movie, user} = this.props;
    dispatch({
      type: 'movie/removeEvaluateMovie',
      payload: movie.id,
    });
  };

  render() {
    const {movie, user} = this.props;

    return (
      <div className={styles.normal}>

        <div className={styles.top}>
          <div className="container">
            <Row>
              <Col span={4}>
         <span>
         <Icon type="clock-circle-o"/>{movie.runtime} min
         </span>
              </Col>
              <Col span={4}>
         <span>
         <Icon type="global"/>{movie.country}
         </span>
              </Col>
              <Col span={4}>
         <span>
         <Icon type="message"/>{movie.language}
         </span>
              </Col>
            </Row>
          </div>
        </div>

        <div className={styles.main}>
          <div className="container">
            <Row>
              <Col span={7}>
                <div className={styles.poster_wrapper}>
                  <div className={styles.poster} style={{backgroundImage: `url(${movie.poster})`}}></div>
                </div>
              </Col>
              <Col offset={1} span={8} className={styles.col_2}>
                <div className={styles.people_info}>
                  {movie.director ?
                    <div className={styles.info_item + ' ' + styles.horizontal}>
                      <span>Director</span>
                      <span>
                        {movie.director.map((director, i) =>
                          <Link
                            to={"/director/" + director.id}
                            className={styles.people_link}
                          >
                            {director.name}
                            {movie.director.length === i + 1 ? "" : ", "}
                          </Link>
                        )}
                      </span>
                    </div> : null}
                  {/*
                   <div className={styles.info_item + ' ' + styles.horizontal}>
                   <span>Writer</span>
                   <span>some writers</span>
                   </div>
                   */}
                  {movie.actor ?
                    <div className={styles.info_item + ' ' + styles.horizontal}>
                      <span>Actor</span>
                      <span>
                        {movie.actor.map((actor, i) =>
                          <Link
                            to={"/actor/" + actor.id}
                            className={styles.people_link}
                          >
                            {actor.name}
                            {movie.actor.length === i + 1 ? "" : ", "}
                          </Link>
                        )}
                      </span>
                    </div> : null}
                </div>
                <div className={styles.buttons}>

                  {
                    user.status === 'collect' ?
                      <Row gutter={10}>
                        <Col span={20}>
                          <div className={styles.status}>
                            <Icon type="heart"/>I want to watch this movie
                          </div>
                        </Col>
                        <Col span={4}>
                          <Tag
                            className={styles.remove}
                            onClick={this.onClickRemoveCollect}
                          >
                            Remove
                          </Tag>
                        </Col>
                      </Row>
                      : null
                  }
                  {
                    user.status === 'evaluate' ?
                      <Row gutter={10}>
                        <Col span={20}>
                          <div className={styles.status}>
                            <Icon type="star"/>I had watched this movie
                          </div>
                        </Col>
                        <Col span={4}>
                          <Tag
                            className={styles.remove}
                            onClick={this.onClickRemoveEvaluate}
                          >
                            Remove
                          </Tag>
                        </Col>
                      </Row>
                      : null
                  }
                  {
                    user.status ? null :
                      <Row gutter={10}>
                        <Col span={12}>
                          <Button type="primary"
                                  icon="heart-o"
                                  ghost
                                  className={styles.button_small}
                                  onClick={this.onClickCollect}
                          >
                            Want to watch
                          </Button>
                        </Col>
                        <Col span={12}>
                          <Button type="primary"
                                  icon="star-o"
                                  className={styles.button_small}
                                  onClick={this.onClickEvaluate}
                          >
                            Had watched
                          </Button>
                          <MovieEvaluate
                            visible={this.state.visible}
                            loading={this.state.loading}
                            handleOk={this.onEvaluateOk}
                            handleCancel={this.onEvaluateCancel}
                          />
                        </Col>
                      </Row>

                  }

                  {/*
                   <Row>
                   <Col span={24}>
                   <Button type="primary" icon="star-o" ghost className={styles.button_large}>Add to list</Button>
                   </Col>
                   </Row>
                   */}
                </div>
              </Col>
              <Col offset={1} span={7}>
                <div className={styles.info_item + ' ' + styles.vertical}>
                  <span>Score</span>
                  <div>
                    <Rate
                      className={styles.rate}
                      disabled
                      allowHalf
                      value={movie.score % 2 > 1 ?
                        Math.floor(movie.score / 2) + 0.5 :
                        Math.floor(movie.score / 2)}
                    />
                    <span className={styles.score}>{movie.score}</span>
                    <span className={styles.count}>From {movie.votes} people</span>
                  </div>
                </div>
                { movie.keyword ?
                  <div className={styles.info_item + ' ' + styles.vertical}>
                    <span>Tags</span>
                    <div className={styles.tags}>
                      {
                        movie.keyword.map((tag) =>
                          <Tag key={tag.id}>{tag.value}</Tag>
                        )
                      }
                    </div>
                  </div> : null }
              </Col>
            </Row>
          </div>
        </div>
      </div>
    );
  }
}
function mapStateToProps(state) {
  const {movie, user} = state.movie;
  return {
    movie,
    user
  };
}


export default connect(mapStateToProps)(MovieBrief);
