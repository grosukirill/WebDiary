export function LOADING_START() {
    return <const>{
        type: 'LOADING_START'
    }
}

export function FINISH_LOADING() {
    return <const>{
        type: 'FINISH_LOADING'
    }
}

export function AUTH_SUCCESS() {
    return <const>{
        type: 'AUTH_SUCCESS'
    }
}

export function LOGGED(payload: {
    token: string
}) {
    return <const>{
        type: 'LOGGED',
        payload
    }
}

export function AUTH_ERROR(payload: {
    errors: IError
}) {
    return <const>{
        type: 'AUTH_ERROR',
        payload
    }
}

export function RESET_START() {
    return <const>{
        type: 'RESET_START'
    }
}

export function SET_AUTH_DATA(payload: {
    key: string
    data: any
}) {
    return <const>{
        type: 'SET_AUTH_DATA',
        payload
    }
}


export function REMOVE_USER() {
    return <const>{
        type: 'REMOVE_USER'
    }
}