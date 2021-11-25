import { Action } from "redux";
import { ThunkAction } from "redux-thunk";

export const setData = (key: string, value: any): ThunkAction<void, any, any, Action> => dispatch => {
    dispatch({
        type: "main/set",
        payload: {
            key: key,
            value: value
        }
    })
}