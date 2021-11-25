interface MainStateC {

}

interface FeedStateC {

}

interface MainPropsC {
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}

interface FeedPropsC {
    setQuestion: (data: ICreateQuestionS) => void
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}