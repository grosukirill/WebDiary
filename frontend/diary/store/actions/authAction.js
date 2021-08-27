import Auth from '../../services/Auth'
import User from '../../services/User';
import Router from 'next/router';

export const registerStart = (data) => dispatch => {
    dispatch({ type: "LOADING_START" })
    const req = {
        body: JSON.stringify(data)
    }
    Auth.startRegistration(req).then(res => {
        if (res.status) {
            loggedSuccessful(dispatch, res.data.bearer);
            dispatch({ type: "AUTH_SUCCESS" })
            Router.push("/")
        }
        else {
            dispatch({
                type: "AUTH_ERROR",
                payload: {
                    errors: res.error
                }
            })
        }
    })
}

export const authenticationStart = (data) => dispatch => {
    dispatch({ type: "LOADING_START" })
    const req = {
        body: JSON.stringify(data)
    }
    Auth.startAuthentication(req).then(res => {
        if (res.status) {
            loggedSuccessful(dispatch, res.data.bearer);
            Router.push("/")
        }
        else {
            dispatch({
                type: "AUTH_ERROR",
                payload: {
                    errors: res.error
                }
            })
        }
    })
}

export const setUserLogged = (store, token) => {
    const checkToken = !store.getState().authReducer.token
    getUserInfo(token, store.dispatch);
    if (checkToken) {
        store.dispatch({
            type: "LOGGED",
            payload: {
                token: token
            }
        })
    }
}

export const getUserInfo = (token, dispatch) => {
    const userSession = sessionStorage.getItem("user") && JSON.parse(sessionStorage.getItem("user"))

    if (!userSession) {
        User.getUserInfo(token).then(res => {
            if (res.status) {
                setUserInfo(res.data, dispatch)
            }
        })
    }

    else {
        dispatch({
            type: "SET_AUTH_DATA",
            payload: {
                key: "user",
                data: userSession
            }
        })
    }
}

export const setUserInfo = (data, dispatch) => {
    const obj = {
        firstName: data.firstName,
        lastName: data.lastName,
        avatar: data.avatar,
        email: data.email
    }
    sessionStorage.setItem("user", JSON.stringify(obj))
    dispatch({
        type: "SET_AUTH_DATA",
        payload: {
            key: "user",
            data: obj
        }
    })
}

export const logout = () => {
    localStorage.removeItem("token")
    sessionStorage.removeItem("user")
    window.location.reload()
}

export const loggedSuccessful = (dispatch, token) => {
    dispatch({
        type: "LOGGED",
        payload: {
            token: token
        }
    })
    localStorage.setItem("token", token)
    getUserInfo(token, dispatch)
}

export const setAuthData = (key, data) => dispatch => {
    dispatch({
        type: "SET_AUTH_DATA",
        payload: {
            key: key,
            data: data
        }
    })
}