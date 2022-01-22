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
    count?: number
    key: number
    idEl: number
    views: number
    voted: boolean | null
    votes: number
    auth: IauthReducer
}

interface ICreateViewC {
    avatar: string
    setQuestion: (data: ICreateQuestionS) => void
    auth: IauthReducer
}