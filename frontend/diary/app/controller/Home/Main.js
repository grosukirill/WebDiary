import React from 'react';
import { createElement } from 'react';
import { connect } from 'react-redux'

import MainView from '../../view/Home/Main';
import Questions from '../../../services/Questions';

class Main extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            questions: [],
            loading: true
        }
    }

    componentDidMount = () => {
        this.getAllQuestions();
    }

    getAllQuestions = () => {
        Questions.getFeedQuestions(this.props.auth.token, "Admin", 0).then(res => {
            if (res.status) {
                this.setState({
                    questions: res.data.content,
                    loading: false
                })
            }
        })
    }

    render() {
        return createElement(MainView, {
            ...this
        })
    }
}

const mapStateToProps = state => {
    return {
        data: state.mainReducer,
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(Main);