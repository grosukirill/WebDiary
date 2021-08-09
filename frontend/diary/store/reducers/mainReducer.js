const initialState = {
    loginAnimation: false
}

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case "main/set":
            return {
                ...state,
                [action.payload.key]: action.payload.value
            }
        default:
            return state;
    }
}

export default userReducer