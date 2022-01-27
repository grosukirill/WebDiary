interface IUser {
    avatar?: string
    id?: number,
    firstName?: string,
    lastName?: string,
    email?: string
}
interface IauthReducer {
    loading: boolean,
    token: string | null,
    userId: number | null,
    confirmation: boolean,
    resetPassword: boolean,
    errors: IError,
    user: IUser
}

interface ImainReducer {
    loginAnimation: boolean
}

interface RootState {
    authReducer: IauthReducer
    mainReducer: ImainReducer
}