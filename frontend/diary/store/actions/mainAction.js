export const setData = (key, value) => dispatch => {
    dispatch({
        type: "main/set",
        payload: {
            key: key,
            value: value
        }
    })
}