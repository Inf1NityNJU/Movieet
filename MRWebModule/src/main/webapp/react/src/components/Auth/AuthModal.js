import React, {Component} from 'react';
import {connect} from 'dva';
import {Modal} from 'antd';

import Auth from './Auth'

import styles from './AuthModal.css';

class AuthModal extends Component {

    state = {
        loading: false,
    };


    handleCancel = (e) => {
        const {dispatch} = this.props;
        dispatch({
            type: 'user/saveAuthStatus',
            payload: false,
        })
    };

    render() {

        const {loading, status} = this.props;

        return (
            <Modal
                className={styles.auth_modal}
                width={400}
                visible={status}
                title={null}
                onCancel={this.handleCancel}
                footer={null}
            >
                <Auth
                    loading={loading}
                    dark={true}
                />

            </Modal>
        );
    }
}
function mapStateToProps(state) {
    return {
        loading: state.loading.models.user,
        status: state.user.authStatus,
    };
}

export default connect(mapStateToProps)(AuthModal);
