import {
    GET_ALL_QUESTIONS_END,
    GET_ALL_QUESTIONS_FAIL,
    GET_ALL_QUESTIONS_START,
    GET_QUESTION_END,
    GET_QUESTION_FAIL,
    GET_QUESTION_START
} from "../actions/questionActions";

const initialState = {
    questions: null,
    isLoading: false,
    error: null,
    question: null
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
                questions: action.payload.data,
                isLoading: false
            };
        case GET_ALL_QUESTIONS_FAIL:
            return {
                ...initialState,
                error: action.payload
            }
        case GET_QUESTION_START:
            return {
                ...state,
                isLoading: true
            };
        case GET_QUESTION_END:
            return {
                ...state,
                question: action.payload.question,
                isLoading: false
            };
        case GET_QUESTION_FAIL:
            return {
                ...initialState,
                error: action.payload
            }
        default:
            return state;
    }
}

export default questionReducer;
