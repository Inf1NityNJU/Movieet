import React, {Component} from 'react';
import {Checkbox, Icon} from 'antd';

import BlankPhoto from '../Util/BlankPhoto';

import styles from './ElementItem.css';


class ElementItem extends Component {

    state = {
        close: false,
    };

    handleMouseEnter = () => {
        this.setState({
            close: true,
        })
    };

    handleMouseLeave = () => {
        this.setState({
            close: false,
        })
    };


    render() {
        const {item, img, onCheckChange, onItemRemove} = this.props;

        return (
            <div className={styles.item}
                 onMouseEnter={this.handleMouseEnter}
                 onMouseLeave={this.handleMouseLeave}>


                <Checkbox
                    checked={item.checked}
                    onChange={() => onCheckChange(item.id, !item.checked)}
                >

                    {img ?
                        <div className={styles.avatar_wrapper}>
                            { item.photo ?
                                <div className={styles.avatar} style={{backgroundImage: `url(${item.photo})`}}/>
                                : <BlankPhoto
                                    className={styles.avatar}
                                    size="mini"
                                >
                                    No Photo
                                </BlankPhoto>
                            }
                        </div> : null
                    }
                    {item.value}
                </Checkbox>
                {
                    this.state.close ?
                        <Icon
                            type="close"
                            onClick={() => onItemRemove(item.id)}
                        /> : null
                }
            </div>
        );
    }
}


export default ElementItem;
