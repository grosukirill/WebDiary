import { combineReducers } from "redux";
import authReducer from "./authReducer";
import mainReducer from "./mainReducer";

export const rootReducer = combineReducers({
    authReducer,
    mainReducer
});