import React, {Component} from 'react';

import {connect} from 'dva';
import {Link} from 'dva/router';

import {Row, Col, Icon, Button, Rate, Tag} from 'antd';

import AuthModal from '../Auth/AuthModal'
import MovieEvaluate from './MovieEvaluate';

import styles from './MovieBrief.css';

class MovieBrief extends Component {

    state = {
        loading: false,
        visible: false,
    };

    onClickCollect = () => {
        const {dispatch, movie} = this.props;
        dispatch({
            type: 'movie/collectMovie',
            payload: movie.id,
        });
    };

    onClickRemoveCollect = () => {
        const {dispatch, movie} = this.props;
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
        const {dispatch, movie} = this.props;
        this.setState({loading: true});
        dispatch({
            type: 'movie/evaluateMovie',
            payload: {
                id: movie.id,
                evaluate: {...evaluate, tags: []},
            },
            onComplete: () => {
                this.setState({loading: false});
            }
        });
    };
    onEvaluateCancel = () => {
        this.setState({visible: false});
    };

    onClickRemoveEvaluate = () => {
        const {dispatch, movie} = this.props;
        dispatch({
            type: 'movie/removeEvaluateMovie',
            payload: movie.id,
        });
    };

    handleAuthShow = () => {
        const {dispatch} = this.props;
        dispatch({
            type: 'user/saveAuthStatus',
            payload: true,
        });
    };


    render() {
        const {movie, evaluateMovies, status, user} = this.props;

        return (
            <div className={styles.normal}>

                <div className={styles.top}>
                    <div className="container">
                        <Row>
                            <Col span={5}>
                                <span>
                                  <Icon type="clock-circle-o"/>{movie.runtime !== 0 ? movie.runtime + ' min' : 'No Data'}
                                </span>
                            </Col>
                            <Col span={5}>
                                <span>
                                  <Icon type="global"/>{movie.country}
                                  </span>
                            </Col>
                            <Col span={5}>
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
                            {/* poster */}
                            <Col span={7}>
                                <div className={styles.poster_wrapper}>
                                    <div className={styles.poster} style={{backgroundImage: `url(${movie.poster})`}}/>
                                </div>
                            </Col>
                            {/* director and actor */}
                            <Col offset={1} span={8} className={styles.col_2}>
                                <div className={styles.people_info}>
                                    {movie.director && movie.director.length > 0 ?
                                        <div className={styles.info_item + ' ' + styles.horizontal}>
                                            <span>Director</span>
                                            <span>
                                                {movie.director.map((director, i) =>
                                                    <Link
                                                        key={director.id}
                                                        to={"/director/" + director.id}
                                                        className={styles.people_link}
                                                    >
                                                        {director.name}
                                                        {movie.director.length === i + 1 ? "" : ", "}
                                                    </Link>
                                                )}
                                            </span>
                                        </div> : null}
                                    {movie.actor && movie.actor.length > 0 ?
                                        <div className={styles.info_item + ' ' + styles.horizontal}>
                                            <span>Actor</span>
                                            <span>
                                                {movie.actor.map((actor, i) =>
                                                    <Link
                                                        key={actor.id}
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
                                {/* button */}
                                <div className={styles.buttons}>

                                    {
                                        user && status === 'collect' ?
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
                                        user && status === 'evaluate' ?
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
                                        status ? null :
                                            <Row gutter={10}>
                                                <Col span={12}>
                                                    <Button type="primary"
                                                            icon="heart-o"
                                                            ghost
                                                            className={styles.button_small}
                                                            onClick={user ? this.onClickCollect : this.handleAuthShow}
                                                    >
                                                        Want to watch
                                                    </Button>
                                                </Col>
                                                <Col span={12}>
                                                    <Button type="primary"
                                                            icon="star-o"
                                                            className={styles.button_small}
                                                            onClick={user ? this.onClickEvaluate : this.handleAuthShow}
                                                    >
                                                        Had watched
                                                    </Button>
                                                </Col>
                                            </Row>
                                    }

                                    <MovieEvaluate
                                        status={status}
                                        movie={movie}
                                        movies={evaluateMovies}
                                        visible={this.state.visible}
                                        loading={this.state.loading}
                                        handleOk={this.onEvaluateOk}
                                        handleCancel={this.onEvaluateCancel}
                                    />

                                    {/*
                                     <Row>
                                     <Col span={24}>
                                     <Button type="primary" icon="star-o" ghost className={styles.button_large}>Add to list</Button>
                                     </Col>
                                     </Row>
                                     */}
                                </div>
                            </Col>
                            {/* rate and keyword*/}
                            <Col offset={1} span={7}>
                                <div className={styles.info_item + ' ' + styles.vertical}>
                                    <span>Score</span>
                                    {movie.scoreFR === 0 ? <span className={styles.score}>No Score</span> :
                                        <div>
                                            <Rate
                                                className={styles.rate}
                                                disabled
                                                allowHalf
                                                value={movie.scoreFR % 2 > 1 ?
                                                    Math.floor(movie.scoreFR / 2) + 0.5 :
                                                    Math.floor(movie.scoreFR / 2)}
                                            />
                                            <span className={styles.score}>{movie.scoreFR}</span>
                                            <span className={styles.count}>From {movie.votesFR} people</span>
                                        </div>
                                    }
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

                <AuthModal/>
            </div>
        );
    }
}
function mapStateToProps(state) {
    const {movie, evaluateMovies, user} = state.movie;
    return {
        movie,
        status: user ? user.status : null,
        user: state.user.currentUser,
        evaluateMovies,
    };
}


export default connect(mapStateToProps)(MovieBrief);
