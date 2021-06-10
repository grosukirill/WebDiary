import axios from "axios";
import {toast} from "material-react-toastify";
import Alert from '@material-ui/lab/Alert';

export const LOGIN_START = "LOGIN_START";
export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGIN_FAIL = "LOGIN_FAIL";
export const LOGOUT = "LOGOUT";


export const loginStart = (email, password) => {
    return dispatch => {
        dispatch({type: LOGIN_START});
        axios.post("http://localhost:8080/auth", {email: email, password: password})
            .then(resp => {
                const data = resp.data;
                storeAuthData(data);
                dispatch(loginSuccess(data.bearer, data.id));
            })
            .catch(err => {
                dispatch({
                    type: LOGIN_FAIL,
                    payload: err
                });
                handleError(err)
            })
    };
};

export const loginSuccess = (token,userId) => {
    return {
        type: LOGIN_SUCCESS,
        payload: {
            token: token,
            userId: userId
        }
    };
}

export const registerStart = (email, firstName, lastName, password) => {
    return dispatch => {
        dispatch({type: LOGIN_START});
        axios.post("http://localhost:8080/users", {email: email, password: password, firstName: firstName, lastName: lastName})
            .then(resp => {
                const data = resp.data;
                storeAuthData(data);
                dispatch(loginSuccess(null, data.id));
            })
            .catch(err => {
                dispatch({
                    type: LOGIN_FAIL,
                    payload: {
                        error: err.response.data
                    }
                })
                handleError(err)
            })
    };
};


export const logout = () => {
    removeAuthData()
    return dispatch => {
        dispatch({type: LOGOUT})
    }
}

const handleError = (error) => {
    const errorMessage = (error.response.data.error === 'Unauthorized') ? "Wrong credentials" : error.response.data.error
    toast.warn(errorMessage)
}

const storeAuthData = (authData) => {
    localStorage.setItem('userId', authData.id);
    localStorage.setItem('token', authData.bearer);
}

const removeAuthData = () => {
    localStorage.removeItem('userId');
    localStorage.removeItem('token');
}
export const authCheckState = () => {
    return dispatch => {
        const userId = localStorage.getItem('userId');
        if (userId){

        }
    }
}
