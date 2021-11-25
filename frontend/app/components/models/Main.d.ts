interface IDropDownMainData {
    icon?: ReactElement | undefined
    name?: string
    type?: string
    category?: string
    id?: number
}

interface IconC {
    fill?: string
    onClick?: () => void
    className?: string
}

interface IPostC {
    title: string 
    author: string
    date: string
    count: number
    key: string
    id: number
    votes: number
    voted: boolean
    auth: IauthReducer
}

interface ICreateViewC {
    avatar: string
    setQuestion: (data: ICreateQuestionS) => void
    auth: IauthReducer
}