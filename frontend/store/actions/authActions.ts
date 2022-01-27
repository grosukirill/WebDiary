import { Action, Dispatch } from "redux";
import { ThunkAction } from "redux-thunk";
import Auth from '../../services/Auth'
import User from '../../services/User';
import Router from 'next/router';
import { StoreState } from '../index'

export const registerStart = (data: IRegisterUser): ThunkAction<void, any, any, Action> => dispatch => {
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

export const loggedSuccessful = (dispatch: Dispatch, token: string) => {
    dispatch({
        type: "LOGGED",
        payload: {
            token: token
        }
    })
    localStorage.setItem("token", token)
    getUserInfo(token, dispatch)
}

export const getUserInfo = (token: string, dispatch: Dispatch) => {
    const users = sessionStorage.getItem("user");
    const userSession = users && JSON.parse(users)

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

export const logout = () => {
    localStorage.removeItem("token")
    sessionStorage.removeItem("user")
    window.location.reload()
}

export const setUserInfo = (data: any, dispatch: Dispatch) => {
    const obj: IUser = {
        id: data.id,
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

export const setUserLogged = (store: StoreState, token: string) => {
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

export const authenticationStart = (data: ILoginUser): ThunkAction<void, any, any, Action> => dispatch => {
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

export const setAuthData = (key: string, data: any): ThunkAction<void, any, any, Action> => dispatch => {
    dispatch({
        type: "SET_AUTH_DATA",
        payload: {
            key: key,
            data: data
        }
    })
}