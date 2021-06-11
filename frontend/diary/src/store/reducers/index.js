import combineReducers from "redux/src/combineReducers";
import authReducer from "./authReducer";
import questionReducer from "./questionReducer";

export const rootReducer = combineReducers({
    authReducer,
    questionReducer
})
