import axios from "axios";
import {toast} from "material-react-toastify";

export const GET_ALL_QUESTIONS_START = "GET_ALL_QUESTIONS_START";
export const GET_ALL_QUESTIONS_END = "GET_ALL_QUESTIONS_END";
export const GET_ALL_QUESTIONS_FAIL = "GET_ALL_QUESTIONS_FAIL";
export const GET_QUESTION_START = "GET_QUESTION_START";
export const GET_QUESTION_END = "GET_QUESTION_END";
export const GET_QUESTION_FAIL = "GET_QUESTION_FAIL";


export const getAllQuestions = () => {
    return dispatch => {
        dispatch({type:GET_ALL_QUESTIONS_START})
        let config = {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        }
        axios.get("http://localhost:8080/questions", config)
            .then(resp => {
                const data = resp.data;
                dispatch(getAllQuestionsSuccess(data));
            })
            .catch(err => {
                dispatch({
                    type: GET_ALL_QUESTIONS_FAIL,
                    payload: err
                })
                handleError(err);
            })
    }
}

export const getAllQuestionsSuccess = (data) => {
    return {
        type: GET_ALL_QUESTIONS_END,
        payload: {
            data: data
        }
    }
}

export const getQuestion = (questionId) => {
    return dispatch => {
        dispatch({type: GET_QUESTION_START})
        let config = {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        }
        axios.get("http://localhost:8080/questions/" + questionId, config)
            .then(resp => {
                const data = resp.data;
                dispatch(getQuestionSuccess(data))
            })
            .catch(err => {
                dispatch({
                    type: GET_QUESTION_FAIL,
                    payload: err
                })
                handleError(err);
            })
    }
}

export const getQuestionSuccess = (data) => {
    return {
        type: GET_QUESTION_END,
        payload: {
            question: data
        }
    }
}

const handleError = (error) => {
    const errorMessage = error.response;
    toast.warn(errorMessage)
}
