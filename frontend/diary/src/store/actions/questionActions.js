import axios from "axios";
import {toast} from "material-react-toastify";

export const GET_ALL_QUESTIONS_START = "GET_ALL_QUESTIONS_START";
export const GET_ALL_QUESTIONS_END = "GET_ALL_QUESTIONS_END";
export const GET_ALL_QUESTIONS_FAIL = "GET_ALL_QUESTIONS_FAIL";

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

const handleError = (error) => {
    const errorMessage = error.response.data.error
    toast.warn(errorMessage)
}
