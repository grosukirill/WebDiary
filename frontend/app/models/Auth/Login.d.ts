interface LoginStateC {
    user: string
    password: string
    hidePassword: boolean,
    anim: boolean,
    checkbox: boolean,
    inputs: HTMLElement[]
}

interface ILoginUser {
    email: string,
    password: string
}

interface LoginPropsC {
    state: LoginStateC
    changeState: (key: string, value: any) => void
    getAuthErrors: () => IError
    setRef: (value: HTMLElement) => void
    signIn: () => void
    changeAuthPage: () => void
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}