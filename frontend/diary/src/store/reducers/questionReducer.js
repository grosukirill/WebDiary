import {GET_ALL_QUESTIONS_END, GET_ALL_QUESTIONS_FAIL, GET_ALL_QUESTIONS_START} from "../actions/questionActions";

const initialState = {
    data: null,
    isLoading: false,
    error: null
}

const questionReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_ALL_QUESTIONS_START:
            return {
                ...state,
                isLoading: true
            };
        case GET_ALL_QUESTIONS_END:
            return {
                ...state,
                data: action.payload.data,
                isLoading: false
            };
        case GET_ALL_QUESTIONS_FAIL:
            return {
                ...initialState,
                error: action.payload
            }
        default:
            return state;
    }
}

export default questionReducer;
