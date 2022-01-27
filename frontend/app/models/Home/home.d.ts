interface MainStateC {

}

interface FeedStateC {
    questions: IQuestion[]
    loading: boolean
    nextPage: number
}

interface MainPropsC {
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}

interface FeedPropsC {
    setQuestion: (data: IQuestion) => void
    getRef?: LegacyRef<HTMLDivElement>
    state: FeedStateC
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}