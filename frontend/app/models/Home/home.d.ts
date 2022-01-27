interface MainStateC {

}

interface FeedStateC {
    questions: IQuestion[]
    loading: boolean
}

interface MainPropsC {
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}

interface FeedPropsC {
    setQuestion: (data: ICreateQuestionS) => void
    state: FeedStateC
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}