import React, {Component} from 'react';
import {connect} from 'dva';
import {Modal, Form, Button, Rate, Tag} from 'antd';

import MovieListSmall from '../MovieList/MovieListSmall';

import styles from './MovieEvaluate.css';

const CheckableTag = Tag.CheckableTag;
const FormItem = Form.Item;

class MovieEvaluate extends Component {

    state = {
        currentScore: 10,
        score: 10,
        genre: [],
        director: [],
        actor: [],
        keyword: [],
    };

    handleSubmit = () => {
        const {form} = this.props;
        const {score, genre, director, actor, keyword} = this.state;

        form.validateFields((err, values) => {
            if (!err) {
                const newValues = {...values, score, genre, director, actor, keyword};
                console.log('Received values of form: ', newValues);
                this.props.handleOk(newValues);
            } else {
                console.log('Error', err);
            }
        });
    };

    // function handleChange(props, values) {
    //console.log(props);
    //console.log(values);
    //form.validateFields((err, values) => {
    //  if (!err) {
    //    console.log('Sign in: Received values of form: ', values);
    //  }
    //  console.log('Sign in: Received values of form: ', values);
    //});
    // }

    handleRateChange = (value) => {
        this.setState({
            currentScore: value * 2,
            score: value * 2,
        })
    };

    handleRateHoverChange = (value) => {
        this.setState({

            currentScore: value * 2,
        })
    };

    handleGenreChange = (tag, checked) => {
        const {genre} = this.state;
        const nextGenre = checked ?
            [...genre, tag] :
            genre.filter(t => t !== tag);
        this.setState({genre: nextGenre});
    };

    handleDirectorChange = (tag, checked) => {
        const {director} = this.state;
        const nextDirector = checked ?
            [...director, tag] :
            director.filter(t => t !== tag);
        this.setState({director: nextDirector});
    };

    handleActorChange = (tag, checked) => {
        const {actor} = this.state;
        const nextActor = checked ?
            [...actor, tag] :
            actor.filter(t => t !== tag);
        this.setState({actor: nextActor});
    };

    handleKeywordChange = (tag, checked) => {
        const {keyword} = this.state;
        const nextKeyword = checked ?
            [...keyword, tag] :
            keyword.filter(t => t !== tag);
        this.setState({keyword: nextKeyword});
    };


    render() {
        const {status, movie, movies, form, visible, loading, handleOk, handleCancel} = this.props;
        const {currentScore, score, genre, director, actor, keyword} = this.state;
        const {getFieldDecorator} = form;

        return (
            <Modal
                className={styles.evaluate}
                visible={visible}
                title={status === 'evaluate' && movies !== null ? 'Guess you will like' : 'Evaluate' }
                width={status === 'evaluate' && movies !== null && movies.length > 0 ? 225*movies.length + 20*(movies.length-1) + 80 : 800}
                // onOk={handleOk}
                onCancel={handleCancel}
                footer={status === 'evaluate' && movies !== null ?
                    [ <Button key="submit" type="primary" size="large" loading={loading} onClick={handleCancel}>
                        Ok
                    </Button>]
                    :[
                    <Button key="back" type="primary" size="large" ghost onClick={handleCancel}>Cancel</Button>,
                    <Button key="submit" type="primary" size="large" loading={loading} onClick={this.handleSubmit}>
                        Submit
                    </Button>,
                ]}
            >

                {status === 'evaluate' && movies !== null ?
                    <div>
                        {movies.length > 0 ?
                            <MovieListSmall
                                num={4}
                                list={movies}
                                colCount={movies.length}
                            /> :
                            <p className={styles.no_movies}>There is no movies to recommend!</p>
                        }
                    </div> :
                    <Form className={styles.form}>
                        <FormItem
                            label="Rate this movie"
                        >
                            {getFieldDecorator('score', {
                                rules: [{required: true, message: 'Please rate the movie!'}],
                                initialValue: this.state.score / 2,
                            })(
                                <div className={styles.rate_wrapper}>
                                    <Rate
                                        allowHalf
                                        className={styles.rate}
                                        value={currentScore ? currentScore / 2 : score / 2}
                                        onHoverChange={this.handleRateHoverChange}
                                        onChange={this.handleRateChange}
                                    />
                                    <span className={styles.score}>{currentScore ? currentScore : score}</span>
                                </div>
                            )}
                        </FormItem>
                        <FormItem
                            label="Genres you like"
                        >
                            <div className={styles.tag_wrapper}>
                                {movie.genre ?
                                    movie.genre.map((g) =>
                                        <CheckableTag
                                            key={g.id}
                                            checked={genre.indexOf(g.id) > -1}
                                            onChange={checked => this.handleGenreChange(g.id, checked)}
                                        >
                                            {g.value}
                                        </CheckableTag>
                                    ) : null
                                }
                            </div>
                        </FormItem>
                        <FormItem
                            label="Directors you like"
                        >
                            <div className={styles.tag_wrapper}>
                                {movie.director ?
                                    movie.director.map((d) =>
                                        <CheckableTag
                                            key={d.id}
                                            checked={director.indexOf(d.id) > -1}
                                            onChange={checked => this.handleDirectorChange(d.id, checked)}
                                        >
                                            {d.name}
                                        </CheckableTag>
                                    ) : null
                                }
                            </div>
                        </FormItem>
                        <FormItem
                            label="Actors you like"
                        >
                            <div className={styles.tag_wrapper}>
                                {movie.actor ?
                                    movie.actor.map((a) =>
                                        <CheckableTag
                                            key={a.id}
                                            checked={actor.indexOf(a.id) > -1}
                                            onChange={checked => this.handleActorChange(a.id, checked)}
                                        >
                                            {a.name}
                                        </CheckableTag>
                                    ) : null
                                }
                            </div>
                        </FormItem>
                        <FormItem
                            label="Keywords you like"
                        >
                            <div className={styles.tag_wrapper}>
                                {movie.keyword ?
                                    movie.keyword.map((k) =>
                                        <CheckableTag
                                            key={k.id}
                                            checked={keyword.indexOf(k.id) > -1}
                                            onChange={checked => this.handleKeywordChange(k.id, checked)}
                                        >
                                            {k.value}
                                        </CheckableTag>
                                    ) : null
                                }
                            </div>
                        </FormItem>

                    </Form>
                }


            </Modal>
        );
    }
}

export default connect()(Form.create()(MovieEvaluate));
