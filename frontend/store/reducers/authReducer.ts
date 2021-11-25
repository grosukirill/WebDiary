import {
    LOGGED, AUTH_ERROR, LOADING_START, FINISH_LOADING, AUTH_SUCCESS,
    RESET_START, SET_AUTH_DATA, REMOVE_USER
} from '../creators/authCreator'

const initialState: IauthReducer = {
    loading: false,
    token: null,
    userId: null,
    confirmation: false,
    resetPassword: false,
    errors: {},
    user: {}
}

type IAction = ReturnType<
    typeof LOGGED | typeof AUTH_ERROR | typeof LOADING_START | typeof FINISH_LOADING
    | typeof AUTH_SUCCESS | typeof RESET_START | typeof SET_AUTH_DATA | typeof REMOVE_USER
>

const authReducer = (state = initialState, action: IAction) => {
    switch (action.type) {
        case "LOADING_START": {
            return {
                ...state,
                loading: true
            }
        }
        case "FINISH_LOADING": {
            return {
                ...state,
                loading: false
            }
        }
        case "AUTH_SUCCESS": {
            return {
                ...state,
                loading: false,
                confirmation: true,
                errors: {}
            }
        }
        case "LOGGED":
            return {
                ...state,
                token: action.payload.token,
                confirmation: false,
                loading: false,
                errors: {}
            }
        case "AUTH_ERROR":
            return {
                ...state,
                loading: false,
                errors: action.payload.errors
            }
        case "RESET_START": {
            return {
                ...state,
                loading: false,
                resetPassword: true,
                errors: {}
            }
        }
        case "SET_AUTH_DATA": {
            return {
                ...state,
                [action.payload.key]: action.payload.data
            }
        }
        case "REMOVE_USER":
            return {
                ...state,
                token: null,
                loading: false
            }
        default:
            return state;
    }
}

export default authReducer