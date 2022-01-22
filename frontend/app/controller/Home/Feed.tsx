import React from 'react';
import { createElement } from 'react';
import { connect } from 'react-redux'

import FeedView from '../../view/Home/Feed';
import Questions from '../../../services/Questions';

interface IProps {
    data: ImainReducer
    auth: IauthReducer
}

class Feed extends React.Component<IProps, FeedStateC> {
    constructor(props: IProps) {
        super(props);
        this.state = {
            questions: [],
            loading: false
        }
    }

    componentDidMount = () => {
        this.getAllQuestions();
    }

    setQuestion = (data: ICreateQuestionS) => {
        // let ques = [...this.state.questions]
        // ques.unshift(data);
        // this.setState({
        //     questions: ques
        // })
    }

    getAllQuestions = () => {
        Questions.getFeedQuestions(this.props.auth.token, "Home", 0).then(res => {
            if (res.status) {
                this.setState({
                    questions: res.data.content,
                    loading: false
                })
            }
        })
    }

    render() {
        const obj: FeedPropsC = {
            setQuestion: this.setQuestion,
            props: {
                data: this.props.data,
                auth: this.props.auth
            },
            state: {
                questions: this.state.questions,
                loading: this.state.loading
            }
        }

        return createElement(FeedView, obj)
    }
}

const mapStateToProps = (state: RootState) => {
    return {
        data: state.mainReducer,
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(Feed);