import React from 'react';
import { createElement } from 'react';
import { connect } from 'react-redux'

import FeedView from '../../view/Home/Feed';
import Questions from '../../../services/Questions';

class Feed extends React.Component {
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

    setQuestion = data => {
        let ques = [...this.state.questions]
        ques.unshift(data);
        this.setState({
            questions: ques
        })
    }

    getAllQuestions = () => {
        Questions.getFeedQuestions(this.props.auth.token, "Users", 0).then(res => {
            console.log(res)
            if (res.status) {
                this.setState({
                    questions: res.data.content,
                    loading: false
                })
            }
        })
    }

    render() {
        return createElement(FeedView, {
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

export default connect(mapStateToProps)(Feed);