interface RegisterStateC {
    name: string
    surname: string,
    email: string,
    password: string,
    hidePassword: boolean,
    anim: boolean,
    checkbox: boolean,
    inputs: HTMLElement[],
    error: IError
}

interface RegisterPropsC {
    state: RegisterStateC
    changeState: (key: string, value: any) => void
    getAuthErrors: () => IError
    setRef: (value: HTMLElement) => void
    register: () => void
    changeAuthPage: () => void
    props: {
        data: ImainReducer
        auth: IauthReducer
    }
}

interface IRegisterUser {
    firstName: string
    lastName: string
    email: string
    password: string
}