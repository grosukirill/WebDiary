import Auth from '../../services/Auth'

export const registerStart = (data) => dispatch => {
    dispatch({ type: "LOADING_START" })
    const req = {
        body: JSON.stringify(data)
    }
    Auth.startRegistration(req).then(res => {
        if (res.status) {
            dispatch({ type: "AUTH_SUCCESS" })
        }
        else {
            dispatch({
                type: "AUTH_ERROR",
                payload: {
                    errors: res.message
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
            loggedSuccessful(dispatch, res.access_token);
        }
        else {
            if (res.errors) {
                dispatch({
                    type: "AUTH_ERROR",
                    payload: {
                        errors: res.message
                    }
                })
            }
            else {
                dispatch({
                    type: "main/set",
                    payload: {
                        key: "errorNotification",
                        value: {
                            status: true,
                            message: res.message
                        }
                    }
                })
                dispatch({
                    type: "FINISH_LOADING"
                })
            }
        }
    })
}

export const authenticationGoogleStart = (data) => dispatch => {
    dispatch({ type: "LOADING_START" })
    const req = {
        body: JSON.stringify(data)
    }
    Auth.startGoogleAuthentication(req).then(res => {
        if (res.status) {
            loggedSuccessful(dispatch, res.access_token);
        }
        else {
            dispatch({
                type: "main/set",
                payload: {
                    key: "errorNotification",
                    value: {
                        status: true,
                        message: res.message
                    }
                }
            })
            dispatch({
                type: "FINISH_LOADING"
            })
        }
    })
}

export const confirmEmail = (token) => dispatch => {
    dispatch({ type: "LOADING_START" })
    Auth.confirmToken(token).then(res => {
        if (res.status) {
            loggedSuccessful(dispatch, res.access_token);
        }
        else {
            dispatch({
                type: "AUTH_ERROR",
                payload: {
                    errors: res.errors
                }
            })
        }
    })
}

export const sendResetEmail = (email) => dispatch => {
    dispatch({ type: "LOADING_START" })
    Auth.resetEmailPassword(email).then(res => {
        if (res.status) {
            dispatch({
                type: "RESET_START"
            })
        }
        else {
            dispatch({
                type: "AUTH_ERROR",
                payload: {
                    errors: res.errors
                }
            })
        }
    })
}

export const sendPassword = (data) => dispatch => {
    dispatch({ type: "LOADING_START" })
    const req = {
        body: JSON.stringify(data)
    }
    Auth.resetPassword(req).then(res => {
        if (res.status) {
            dispatch({
                type: "RESET_START"
            })
        }
        else {
            dispatch({
                type: "AUTH_ERROR",
                payload: {
                    errors: res.errors
                }
            })
        }
    })
}

export const getProfileData = (token) => dispatch => {
    setProfileData(dispatch, token)
}

export const loggedSuccessful = (dispatch, token) => {
    dispatch({
        type: "LOGGED",
        payload: {
            token: token
        }
    })
    localStorage.setItem("token", token)
    sessionStorage.setItem("user", JSON.stringify({ token: token }));
    setProfileData(dispatch, token)
}

export const setUserData = (dispatch, data) => {
    dispatch({
        type: "SET_AUTH_DATA",
        payload: {
            key: "user",
            data: data
        }
    })
    sessionStorage.setItem("userInfo", JSON.stringify(data))
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

export const logged = token => (dispatch) => {
    dispatch({
        type: "LOGGED",
        payload: {
            token: token
        }
    })
}