interface ICreateQuestionS {
    status: boolean
    data: {
        id: number
        question: string
        creationDate: string
        categories: ICategories[]
    }
    type: string
}

interface IFeedGetQuestion {
    status: boolean
    data:{
        content: IQuestion[]
        hasNextPage: boolean
        nextPage: number | null
    }
}

interface IQuestion {
    id: number
    question: string
    answers: number | null
    creationDate: string
    type: string
    views: number
    voted: boolean | null
    votes: number
    creator: ICreator
}

interface ICreator {
    avatar: string
    email: string
    firstName: string
    followers: number
    following: number
    id: number
    isFollowed: boolean | null
    lastName: string
}

interface ICategories {
    id: number
    category: string
}

interface IReq {
    body: string
}