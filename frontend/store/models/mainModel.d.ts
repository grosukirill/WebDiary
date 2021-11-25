interface IauthReducer {
    loading: boolean,
    token: string | null,
    userId: number | null,
    confirmation: boolean,
    resetPassword: boolean,
    errors: IError,
    user: {
        avatar?: string
    }
}

interface ImainReducer {
    loginAnimation: boolean
}

interface RootState {
    authReducer: IauthReducer
    mainReducer: ImainReducer
}