const initialState = {
    loading: false,
    token: null,
    userId: null,
    confirmation: false,
    resetPassword: false,
    errors: {},
    user: {}
}

const userReducer = (state = initialState, action) => {
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

export default userReducer