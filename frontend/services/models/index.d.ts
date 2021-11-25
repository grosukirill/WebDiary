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

interface ICategories {
    id: number
    category: string
}

interface IReq {
    body: string
}