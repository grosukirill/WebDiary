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
    private ref: React.RefObject<HTMLDivElement>;
    constructor(props: IProps) {
        super(props);
        this.state = {
            questions: [],
            loading: false,
            nextPage: 0
        }
        this.ref = React.createRef<HTMLDivElement>();
    }

    componentDidMount = () => {
        this.getAllQuestions();
        document.addEventListener('scroll', this.onScroll);
    }

    setQuestion = (data: IQuestion) => {
        let ques = [...this.state.questions]
        ques.unshift(data);
        this.setState({
            questions: ques
        })
    }

    getAllQuestions = () => {
        const nextPage = this.state.nextPage
        if (nextPage !== null) {
            this.setState({
                loading: true
            })
            Questions.getFeedQuestions(this.props.auth.token, "Home", nextPage).then(res => {
                if (res.status) {
                    let content = res.data?.content
                    if (nextPage !== 0) {
                        content = this.state.questions.push(content)
                    }
                    this.setState({
                        questions: content,
                        loading: false,
                        nextPage: res.data?.nextPage
                    })
                }
            })
        }
    }

    isBottom(el: HTMLDivElement | null) {
        if (el) {
            return el.getBoundingClientRect().bottom <= window.innerHeight;
        }
        return null
    }

    onScroll = () => {
        if (!this.state.loading) {
            const element = this.ref.current
            if (this.isBottom(element)) {
                console.log(this.state.questions)
                document.removeEventListener('scroll', this.onScroll);
            }
        }
    };

    getRef = (ref: React.RefObject<HTMLDivElement>) => {
        if (ref) {
            this.ref = ref
        }
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.onScroll);
    }

    render() {
        const obj: FeedPropsC = {
            setQuestion: this.setQuestion,
            getRef: this.getRef,
            props: {
                data: this.props.data,
                auth: this.props.auth
            },
            state: {
                questions: this.state.questions,
                loading: this.state.loading,
                nextPage: this.state.nextPage
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