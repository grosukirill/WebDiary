import { MAIN_SET } from '../creators/mainCreator'

const initialState: ImainReducer = {
    loginAnimation: false
}

type IAction = ReturnType<
    typeof MAIN_SET
>

const authReducer = (state = initialState, action: IAction) => {
    switch (action.type) {
        case "main/set":
            return {
                ...state,
                [action.payload.key]: action.payload.value
            }
        default:
            return state
    }
}

export default authReducer